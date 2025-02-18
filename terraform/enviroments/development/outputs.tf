output "vpc_id" {
  value = module.network.vpc_id
}

output "public_subnet_ids" {
  value = module.network.public_subnet_ids
}

output "ec2_instance_ip" {
  value = module.compute.instance_public_ip
}

output "ecr_repository_url" {
  value = module.compute.ecr_repository_url
}

output "rds_endpoint" {
  value = module.rds.endpoint
}

output "s3_bucket_name" {
  value = module.storage.bucket_name
}