# AWS ECS Configuration

This Terraform configuration sets up an AWS ECS, ECR, S3 bucket, a CloudFront distribution with optimized caching, developer access for only logs, and connects the CloudFront distribution to the ECS task. The objective is to serve api over CloudFront connected to ecs.

## Prerequisites

1. **Terraform**: Ensure you have Terraform installed.
2. **AWS CLI**: Optional but recommended. This is useful if you want to debug or manually inspect AWS resources.
3. **AWS Account**: Ensure you have an AWS account and the necessary permissions to create S3 buckets, CloudFront distributions, and related resources.

## Quick Start

1. **Clone the repository**:
   ```bash
   git clone [URL_TO_YOUR_REPO]
   cd [YOUR_REPO_DIRECTORY]
   ```
2. **Initialize Terraform**:
    ```bash
    terraform init
    ```
3. **Modify Variables**:<br />
    Update the **variables.tf** file or the respective variable file with the desired values for your configuration. Ensure the S3 bucket name is unique.

4. **Plan & Apply**:

    ```bash
    terraform plan
    ```
    Review the output, ensuring there are no errors. If everything looks good:

    ```bash
    terraform apply
    ```
    Confirm the changes when prompted.

5. **Access your Site**:<br />
    Once the **\`apply\`** is complete, you should be able to access your static website via the CloudFront URL provided in the outputs.

## Features

* **S3 Bucket**: Serves as storage for static assets.

* **CloudFront Distribution**: CDN service that caches content from the S3 bucket and serves it to end-users with low latency.

* **Cache Optimization**: Uses caching policies for efficient caching of assets.

* **Origin Access Control**: Restricts access to the S3 bucket, ensuring content can only be accessed via the CloudFront distribution.

* **Error Redirection**: In case of 404 or 403 errors, users are redirected to **\`index.html\`**.

* **Compression**: Assets are compressed when served to reduce bandwidth and improve load times.

## Cleanup

To destroy the resources created:

```bash
terraform destroy
```
Confirm the destruction when prompted. Ensure that all resources are deleted to avoid incurring unwanted charges.

## Caution

* Before making changes in a production environment, test your Terraform configurations in a staging or development environment first.

* Always review **\`terraform plan\`** output before applying to understand what changes will be made.

