resource "aws_s3_bucket" "storage" {
  bucket        = "${var.bucket_prefix}-${var.environment}-${random_id.suffix.hex}"
  force_destroy = var.force_destroy

  tags = merge(
    {
      Name        = "${var.bucket_prefix}-${var.environment}"
      Environment = var.environment
    },
    var.tags
  )
}

resource "aws_s3_bucket_versioning" "storage" {
  bucket = aws_s3_bucket.storage.id
  
  versioning_configuration {
    status = var.enable_versioning ? "Enabled" : "Suspended"
  }
}

resource "aws_s3_bucket_lifecycle_configuration" "storage" {
  bucket = aws_s3_bucket.storage.id

  rule {
    id     = "transition-to-ia"
    status = "Enabled"

    transition {
      days          = var.lifecycle_rule_days
      storage_class = "STANDARD_IA"
    }

    noncurrent_version_expiration {
      noncurrent_days = 7
    }
  }
}

resource "aws_s3_bucket_public_access_block" "storage" {
  bucket = aws_s3_bucket.storage.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_cors_configuration" "storage" {
  bucket = aws_s3_bucket.storage.id

  cors_rule {
    allowed_headers = ["*"]
    allowed_methods = ["GET", "POST", "PUT", "DELETE"]
    allowed_origins = var.allowed_origins
    expose_headers  = ["ETag"]
    max_age_seconds = 3000
  }
}
resource "aws_s3_bucket_policy" "public_read" {
  bucket = aws_s3_bucket.storage.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid       = "PublicReadGetObject"
        Effect    = "Allow"
        Principal = "*"
        Action    = "s3:GetObject"
        Resource  = "${aws_s3_bucket.storage.arn}/*"
      }
    ]
  })

  # This ensures the public access block settings are applied first
  depends_on = [aws_s3_bucket_public_access_block.storage]
}

resource "random_id" "suffix" {
  byte_length = 4
}