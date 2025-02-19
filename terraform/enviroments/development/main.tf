provider "aws" {
  region = "us-east-1"
}

module "network" {
  source = "../../modules/network"
  
  environment         = var.environment
  vpc_cidr           = var.vpc_cidr
  public_subnet_cidrs = var.public_subnet_cidrs
}

module "storage" {
  source = "../../modules/storage"
  
  environment         = var.environment
  bucket_prefix      = "spring-api"
  force_destroy      = false
  lifecycle_rule_days = 90
  enable_versioning  = true
  allowed_origins    = var.allowed_origins
}

module "compute" {
  source = "../../modules/compute"
  
  environment    = var.environment
  vpc_id         = module.network.vpc_id
  subnet_id      = module.network.public_subnet_ids[0]
  key_name       = var.key_name
  instance_type  = "t2.micro"
  allowed_cidr   = var.allowed_cidr
  s3_bucket_arn  = module.storage.bucket_arn
}

module "rds" {
  source = "../../modules/rds"
  
  environment          = var.environment
  vpc_id              = module.network.vpc_id
  subnet_ids          = module.network.public_subnet_ids
  db_username         = var.db_username
  db_password         = var.db_password
  app_security_group_id = module.compute.security_group_id
  
  depends_on = [module.compute]
}