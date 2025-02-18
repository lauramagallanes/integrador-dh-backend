variable "environment" {
  description = "Environment name"
  type        = string
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "subnet_id" {
  description = "Subnet ID for EC2 instance"
  type        = string
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t2.micro"
}

variable "key_name" {
  description = "Name of the SSH key pair"
  type        = string
}

variable "allowed_cidr" {
  description = "CIDR block allowed to connect to EC2"
  type        = string
  default     = "0.0.0.0/0"
}

variable "s3_bucket_arn" {
  description = "ARN of the S3 bucket that the EC2 instance needs access to"
  type        = string
}