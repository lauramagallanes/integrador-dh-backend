variable "environment" {
  description = "Environment name"
  type        = string
}

variable "force_destroy" {
  description = "Whether to force destroy the bucket even if it contains objects"
  type        = bool
  default     = false
}

variable "lifecycle_rule_days" {
  description = "Number of days before objects are transitioned to another storage class"
  type        = number
  default     = 30
}

variable "bucket_prefix" {
  description = "Prefix for the S3 bucket name"
  type        = string
  default     = "spring-api"
}

variable "enable_versioning" {
  description = "Enable versioning on the bucket"
  type        = bool
  default     = true
}

variable "allowed_origins" {
  description = "List of allowed origins for CORS"
  type        = list(string)
  default     = ["*"]
}

variable "tags" {
  description = "Additional tags for the bucket"
  type        = map(string)
  default     = {}
}