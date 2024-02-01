provider "aws" {
  region     = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

data "aws_caller_identity" "current" {}

resource "aws_ecr_repository" "java_app" {
  name                 = var.project_name
  image_tag_mutability = "MUTABLE"
}

resource "aws_ecs_cluster" "java_cluster" {
  name = var.project_name
}

resource "aws_ecs_task_definition" "java_task" {
  family                   = var.project_name
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = aws_iam_role.ecs_execution_role.arn

  container_definitions = jsonencode([
    {
      name  = var.project_name
      image = "${aws_ecr_repository.java_app.repository_url}:latest"
      portMappings = [
        {
          containerPort = 8080
        }
      ]
      logConfiguration = {
        logDriver = "awslogs",
        options = {
          "awslogs-group" = aws_cloudwatch_log_group.ecs_logs.name,
          "awslogs-region" = var.aws_region,
          "awslogs-stream-prefix" = "ecs"
        }
      }
    }
  ])
}

resource "aws_iam_role" "ecs_execution_role" {
  name               = "ecs_execution_role"
  assume_role_policy = data.aws_iam_policy_document.ecs_assume_role_policy.json
}

resource "aws_iam_policy" "ecs_execution_policy" {
  name        = "ecs_execution_policy"
  description = "Allows ECS tasks to pull images from ECR"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "ecr:GetAuthorizationToken",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage",
          "ecr:BatchCheckLayerAvailability"
        ],
        Resource = aws_ecr_repository.java_app.arn
      },
      {
        Effect = "Allow",
        Action = [
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Resource = "arn:aws:logs:*:*:*"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_execution_policy_attach" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryFullAccess"
  role       = aws_iam_role.ecs_execution_role.name
}

resource "aws_iam_policy" "ecs_logging" {
  name        = "ECSLogging"
  description = "Allows ECS tasks to push logs to CloudWatch."

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Resource = "arn:aws:logs:*:*:*"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_logging_attach" {
  policy_arn = aws_iam_policy.ecs_logging.arn
  role       = aws_iam_role.ecs_execution_role.name
}

resource "aws_ecr_repository_policy" "java_app" {
  repository = aws_ecr_repository.java_app.name
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect    = "Allow",
        Action    = [
          "ecr:BatchCheckLayerAvailability",
          "ecr:BatchGetImage",
          "ecr:CompleteLayerUpload",
          "ecr:GetDownloadUrlForLayer",
          "ecr:GetLifecyclePolicy",
          "ecr:InitiateLayerUpload",
          "ecr:PutImage",
          "ecr:UploadLayerPart"
        ],
        Principal = {
          AWS = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        }
      }
    ]
  })
}

data "aws_iam_policy_document" "ecs_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_ecs_service" "java_service" {
  name            = var.project_name
  cluster         = aws_ecs_cluster.java_cluster.id
  task_definition = aws_ecs_task_definition.java_task.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100

  network_configuration {
    subnets          = var.subnets
    security_groups  = [aws_security_group.java_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.java_tg.arn
    container_name   = var.project_name
    container_port   = 8080
  }

  deployment_circuit_breaker {
    enable = true
    rollback = true
  }

  depends_on = [
    aws_lb_listener.java_listener,
    aws_security_group.java_sg,
    aws_ecs_task_definition.java_task
  ]
}

resource "aws_security_group" "java_sg" {
  name        = "java-sg"
  description = "Allow inbound traffic from ALB"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Better to restrict it to the ALB's security group
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1" # all traffic outbound rules
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb" "java_lb" {
  name               = "java-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.java_alb_sg.id]
  subnets            = var.subnets
}

resource "aws_security_group" "java_alb_sg" {
  name        = "java-alb-sg"
  description = "Allow inbound traffic for ALB"

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"  # Allow all outbound traffic
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb_target_group" "java_tg" {
  name     = "java-tg"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = var.vpc_id
  target_type = "ip"
  deregistration_delay = 360
}

resource "aws_lb_listener" "java_listener" {
  load_balancer_arn = aws_lb.java_lb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.java_tg.arn
  }
}

resource "aws_appautoscaling_target" "ecs_target" {
  max_capacity       = 3
  min_capacity       = 1
  resource_id        = "service/${aws_ecs_cluster.java_cluster.name}/${aws_ecs_service.java_service.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

resource "aws_appautoscaling_policy" "ecs_cpu_scale_out" {
  name               = "cpu-scale-out"
  policy_type        = "StepScaling"
  resource_id        = aws_appautoscaling_target.ecs_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_target.service_namespace

  step_scaling_policy_configuration {
    adjustment_type         = "ChangeInCapacity"
    cooldown                = 300
    metric_aggregation_type = "Average"

    step_adjustment {
      metric_interval_lower_bound = 0
      scaling_adjustment          = 1
    }
  }
}

resource "aws_appautoscaling_policy" "ecs_cpu_scale_in" {
  name               = "cpu-scale-in"
  policy_type        = "StepScaling"
  resource_id        = aws_appautoscaling_target.ecs_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_target.service_namespace

  step_scaling_policy_configuration {
    adjustment_type         = "ChangeInCapacity"
    cooldown                = 300
    metric_aggregation_type = "Average"

    step_adjustment {
      metric_interval_upper_bound = 0
      scaling_adjustment          = -1
    }
  }
}

resource "aws_cloudwatch_metric_alarm" "ecs_cpu_high" {
  alarm_name          = "ecs-cpu-high"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = "2"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/ECS"
  period              = "300"
  statistic           = "Average"
  threshold           = "80"
  alarm_description   = "This metric checks CPU utilization of ECS service"
  alarm_actions       = [aws_appautoscaling_policy.ecs_cpu_scale_out.arn]

  dimensions = {
    ClusterName = aws_ecs_cluster.java_cluster.name
    ServiceName = aws_ecs_service.java_service.name
  }
}

resource "aws_cloudwatch_metric_alarm" "ecs_cpu_low" {
  alarm_name          = "ecs-cpu-low"
  comparison_operator = "LessThanOrEqualToThreshold"
  evaluation_periods  = "2"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/ECS"
  period              = "300"
  statistic           = "Average"
  threshold           = "30"
  alarm_description   = "This metric checks CPU utilization of ECS service"
  alarm_actions       = [aws_appautoscaling_policy.ecs_cpu_scale_in.arn]

  dimensions = {
    ClusterName = aws_ecs_cluster.java_cluster.name
    ServiceName = aws_ecs_service.java_service.name
  }
}

resource "aws_cloudwatch_log_group" "ecs_logs" {
  name = "${var.project_name}-ecs-logs"
  retention_in_days = 14
}

resource "aws_cloudfront_distribution" "java_distribution" {
  origin {
    domain_name = aws_lb.java_lb.dns_name
    origin_id   = var.project_name

    custom_origin_config {
      http_port              = 80
      https_port             = 80
      origin_protocol_policy = "http-only"
      origin_ssl_protocols   = ["TLSv1", "TLSv1.1", "TLSv1.2"]
    }
  }

  enabled             = true
  is_ipv6_enabled     = true

  default_cache_behavior {
    allowed_methods  = ["DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"]
    cached_methods   = ["GET", "HEAD"]
    target_origin_id = var.project_name

    forwarded_values {
      query_string = true

      headers = ["*"]

      cookies {
        forward = "none"
      }
    }

    viewer_protocol_policy = "allow-all"
    min_ttl                = 0
    default_ttl            = 3600
    max_ttl                = 86400
  }

  restrictions {
    geo_restriction {
      restriction_type = "none"
    }
  }

  viewer_certificate {
    cloudfront_default_certificate = true
  }
}

resource "aws_iam_user" "developer_logs" {
  name = "developer-logs"
  force_destroy = true
}

resource "aws_iam_user_login_profile" "developer_logs" {
  user    = aws_iam_user.developer_logs.name
}

resource "aws_iam_user_policy" "developer_logs_policy" {
  name = "DeveloperLogsFullAccess"
  user = aws_iam_user.developer_logs.name

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect   = "Allow",
        Action   = "logs:*",
        Resource = "*"
      }
    ]
  })
}

resource "aws_s3_bucket" "project_bucket" {
  bucket = "${var.project_name}-bucket"  
  tags = {
    Name        = "TIA Dev Mode"
    Environment = "Dev"
  }
}

resource "aws_s3_bucket_versioning" "project_bucket_versioning" {
  bucket = aws_s3_bucket.project_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_iam_user" "services_user" {
  name          = "${var.project_name}-services-user"
  force_destroy = true
}

resource "aws_iam_user_policy" "services_user_policy" {
  name = "${var.project_name}-ServicesAccess"
  user = aws_iam_user.services_user.name

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect   = "Allow",
        Action   = [
          "ses:*",
          "sns:*",
          "s3:ListBucket",
          "s3:GetObject",
          "s3:PutObject"
        ],
        Resource = [
          aws_s3_bucket.project_bucket.arn,
          "${aws_s3_bucket.project_bucket.arn}/*"
        ]
      }
    ]
  })
}

resource "aws_iam_access_key" "services_user_key" {
  user = aws_iam_user.services_user.name
}