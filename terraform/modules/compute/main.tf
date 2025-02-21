resource "aws_ecr_repository" "api" {
  name                 = "spring-api-${var.environment}"
  image_tag_mutability = "MUTABLE"
  
  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Environment = var.environment
  }
}

# Security Groups
resource "aws_security_group" "ec2" {
  name        = "ec2-sg-${var.environment}"
  description = "Security group for EC2 instance"
  vpc_id      = var.vpc_id

  # SSH access
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.allowed_cidr]
  }

  # Application port
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "ec2-sg-${var.environment}"
    Environment = var.environment
  }
}

# IAM Role and Instance Profile
resource "aws_iam_role" "ec2_role" {
  name = "ec2-role-${var.environment}"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy" "ecr_policy" {
  name = "ecr-policy-${var.environment}"
  role = aws_iam_role.ec2_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ecr:GetAuthorizationToken",
          "ecr:BatchGetImage",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchDeleteImage",  
          "ecr:ListImages"       
        ]
        Resource = "*"
      },
      {
        Effect = "Allow"
        Action = [
          "s3:PutObject",
          "s3:GetObject",
          "s3:ListBucket"
        ]
        Resource = [
          "${var.s3_bucket_arn}",
          "${var.s3_bucket_arn}/*"
        ]
      }
    ]
  })
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2-profile-${var.environment}"
  role = aws_iam_role.ec2_role.name
}

# EC2 Instance
resource "aws_instance" "api_server" {
  ami                    = data.aws_ami.amazon_linux_2.id
  instance_type          = var.instance_type
  subnet_id              = var.subnet_id
  vpc_security_group_ids = [aws_security_group.ec2.id]
  key_name              = var.key_name
  iam_instance_profile   = aws_iam_instance_profile.ec2_profile.name

  user_data = <<-EOF
              #!/bin/bash
              yum update -y
              yum install -y docker amazon-cloudwatch-agent
              systemctl start docker
              systemctl enable docker
              usermod -a -G docker ec2-user

              # cleanup script
              cat <<'SCRIPT' > /usr/local/bin/cleanup-docker.sh
              #!/bin/bash
              docker container prune -f
              docker image prune -a -f --filter "until=24h" --filter "label!=latest"
              SCRIPT
              chmod +x /usr/local/bin/cleanup-docker.sh
              # cleanup cron job
              echo "0 0 * * * root /usr/local/bin/cleanup-docker.sh" > /etc/cron.d/docker-cleanup

              # Configure ECR login
              aws ecr get-login-password --region ${data.aws_region.current.name} | docker login --username AWS --password-stdin ${aws_ecr_repository.api.repository_url}
              EOF

  tags = {
    Name        = "api-server-${var.environment}"
    Environment = var.environment
  }
}
resource "aws_eip" "ec2_eip" {
  instance = aws_instance.api_server.id
  domain   = "vpc"

  tags = {
    Name        = "eip-${var.environment}"
    Environment = var.environment
  }
}
# Data sources
data "aws_ami" "amazon_linux_2" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-gp2"]
  }
}
resource "aws_ecr_lifecycle_policy" "api" {
  repository = aws_ecr_repository.api.name

  policy = jsonencode({
    rules = [
      {
        rulePriority = 1
        description  = "Keep only last 2 images"
        selection = {
          tagStatus   = "any"
          countType   = "imageCountMoreThan"
          countNumber = 2
        }
        action = {
          type = "expire"
        }
      }
    ]
  })
}


data "aws_region" "current" {}