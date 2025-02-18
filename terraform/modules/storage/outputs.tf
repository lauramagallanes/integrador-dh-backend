# modules/storage/outputs.tf
output "bucket_name" {
  description = "Name of the created S3 bucket"
  value       = aws_s3_bucket.storage.id
}

output "bucket_arn" {
  description = "ARN of the created S3 bucket"
  value       = aws_s3_bucket.storage.arn
}

output "bucket_domain_name" {
  description = "Domain name of the bucket"
  value       = aws_s3_bucket.storage.bucket_domain_name
}

output "bucket_regional_domain_name" {
  description = "Regional domain name of the bucket"
  value       = aws_s3_bucket.storage.bucket_regional_domain_name
}

output "versioning_enabled" {
  description = "Whether versioning is enabled"
  value       = var.enable_versioning
}