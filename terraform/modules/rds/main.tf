resource "aws_db_subnet_group" "main" {
  name        = "db-subnet-group-${var.environment}"
  subnet_ids  = var.subnet_ids
  description = "DB subnet group for ${var.environment}"

  tags = {
    Name        = "db-subnet-group-${var.environment}"
    Environment = var.environment
  }
}

resource "aws_security_group" "db" {
  name        = "db-sg-${var.environment}"
  description = "Security group for database in ${var.environment}"
  vpc_id      = var.vpc_id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [var.app_security_group_id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "db-sg-${var.environment}"
    Environment = var.environment
  }
}

resource "aws_db_instance" "main" {   
  identifier           = "spring-api-${var.environment}"
  engine              = "mysql"
  engine_version      = "8.0.40"
  instance_class      = var.instance_class
  allocated_storage   = var.allocated_storage
  storage_type        = "gp2"
  
  db_name             = "springapi${var.environment}"
  username            = var.db_username
  password            = var.db_password
  
  vpc_security_group_ids = [aws_security_group.db.id]
  db_subnet_group_name   = aws_db_subnet_group.main.name
  
  skip_final_snapshot    = true
  
  backup_retention_period = var.environment == "production" ? 7 : 1
  backup_window          = "03:00-04:00"
  maintenance_window     = "Mon:04:00-Mon:05:00"

  multi_az               = false
  publicly_accessible    = false
  performance_insights_enabled = false
  
  tags = {
    Name        = "spring-api-db-${var.environment}"
    Environment = var.environment
  }
}