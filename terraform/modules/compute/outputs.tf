output "instance_id" {
  value = aws_instance.api_server.id
}

output "instance_public_ip" {
  value = aws_instance.api_server.public_ip
}

output "ecr_repository_url" {
  value = aws_ecr_repository.api.repository_url
}

output "security_group_id" {
  description = "The ID of the EC2 security group"
  value       = aws_security_group.ec2.id
}