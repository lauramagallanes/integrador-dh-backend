output "endpoint" {
  description = "The connection endpoint"
  value       = aws_db_instance.main.endpoint
}

output "security_group_id" {
  description = "The security group ID"
  value       = aws_security_group.db.id
}

output "db_name" {
  description = "The database name"
  value       = aws_db_instance.main.db_name
}