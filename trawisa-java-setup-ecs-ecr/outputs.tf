output "cloudfront_distribution_url" {
  description = "The URL of the CloudFront distribution"
  value       = aws_cloudfront_distribution.java_distribution.domain_name
}

output "services_user_access_key" {
  description = "Access Key ID for the services user"
  value       = aws_iam_access_key.services_user_key.id
}

output "services_user_secret_key" {
  description = "Secret Access Key for the services user"
  value       = aws_iam_access_key.services_user_key.secret
  sensitive   = true
}

output "AWS_ECR_ID" {
  description = "ID of the ECR Repository"
  value       = aws_ecr_repository.java_app.repository_url
}

output "REPO_NAME" {
  description = "Name of the ECR Repository"
  value       = aws_ecr_repository.java_app.name
}

output "ECS_CLUSTER_NAME" {
  description = "Name of the ECS Cluster"
  value       = aws_ecs_cluster.java_cluster.name
}

output "ECS_SERVICE_NAME" {
  description = "Name of the ECS Service"
  value       = aws_ecs_service.java_service.name
}

output "developer_logs_console_url" {
  value = "https://${var.aws_region}.console.aws.amazon.com/iam/home?region=${var.aws_region}#/users/${aws_iam_user.developer_logs.name}"
  description = "Console URL for developer-logs IAM user"
}

output "password" {
  value = aws_iam_user_login_profile.developer_logs.encrypted_password
}