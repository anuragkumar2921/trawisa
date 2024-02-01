variable "aws_access_key" {
  description = "AWS access key"
  sensitive   = true
}

variable "aws_secret_key" {
  description = "AWS secret key"
  sensitive   = true
}

variable "aws_region" {
  description = "AWS Region"
  default     = "eu-central-1"
}

variable "project_name" {
  description = "Name of the project"
  default     = "java-app"
}

variable "subnets" {
  description = "List of subnets for ECS tasks and ALB"
  type        = list(string)
  default     = ["subnet-099a259a546daf8f4", "subnet-0253bc9a73e316d1d", "subnet-03a4e4c2a451aaa87"]
}

variable "vpc_id" {
  description = "VPC ID where the resources will be created"
  type        = string
  default     = "vpc-01f9530caf622ef8c"
}

variable "developer_logs_password" {
  description = "Password for the developer-logs user"
  type        = string
}
