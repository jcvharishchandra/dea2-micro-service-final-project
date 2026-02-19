# Dockerizing & Deploying to AWS EC2

This document explains how to build a Docker image for this Spring Boot app, push it to Amazon ECR, and run it on an EC2 instance (production recommendations included).

Overview (recommended production architecture)
- Build and push image to Amazon ECR.
- Use Amazon RDS (PostgreSQL) for the database (do not run DB on the same EC2 host in production).
- Run the container on EC2 (Docker) or use ECS/EKS for easier scaling.
- Store DB credentials in AWS Secrets Manager or SSM Parameter Store and attach IAM role to the instance.

Local dev (quick):
- Create a `.env` (example provided). Then run:

```bash
docker compose up --build
```

This starts Postgres and the app. The app reads DB credentials from environment variables configured in `docker-compose.yml` and `.env`.

Build & push to ECR (commands):

1. Create an ECR repository (console or AWS CLI):

```bash
aws ecr create-repository --repository-name inventory-management-service
```

2. Build locally and tag for ECR (replace <account> and <region>):

```bash
docker build -t inventory-management-service:latest .
docker tag inventory-management-service:latest <account>.dkr.ecr.<region>.amazonaws.com/inventory-management-service:latest
```

3. Authenticate and push:

```bash
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <account>.dkr.ecr.<region>.amazonaws.com
docker push <account>.dkr.ecr.<region>.amazonaws.com/inventory-management-service:latest
```

Run on EC2 (Amazon Linux 2) — minimal steps:

1. Launch an EC2 instance with an IAM role that allows ECR pull and (optionally) Secrets Manager read.
2. Open security group for your app port (e.g. 8010) from the intended source (0.0.0.0/0 for public, better restrict to CIDR).
3. SSH into the instance and install Docker:

```bash
sudo yum update -y
sudo amazon-linux-extras install docker -y
sudo service docker start
sudo usermod -a -G docker ec2-user
```

4. Login to ECR and pull image:

```bash
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <account>.dkr.ecr.<region>.amazonaws.com
docker pull <account>.dkr.ecr.<region>.amazonaws.com/inventory-management-service:latest
```

5. Run container with production environment variables (example using RDS and Secrets Manager):

```bash
docker run -d \
  --name inventory-service \
  -e DB_URL=jdbc:postgresql://<rds-endpoint>:5432/inventory_management_db \
  -e DB_USERNAME=<db_user> \
  -e DB_PASSWORD=<db_password> \
  -e SERVER_PORT=8010 \
  -p 8010:8010 \
  <account>.dkr.ecr.<region>.amazonaws.com/inventory-management-service:latest
```

Run container as a systemd service (example `inventory.service`):

```ini
[Unit]
Description=Inventory Management Service
After=docker.service
Requires=docker.service

[Service]
Restart=always
ExecStart=/usr/bin/docker run --rm --name inventory-service -e DB_URL=jdbc:postgresql://<rds-endpoint>:5432/inventory_management_db -e DB_USERNAME=<db_user> -e DB_PASSWORD=<db_password> -p 8010:8010 <account>.dkr.ecr.<region>.amazonaws.com/inventory-management-service:latest
ExecStop=/usr/bin/docker stop inventory-service

[Install]
WantedBy=multi-user.target
```

Security & production recommendations
- Use Amazon RDS for PostgreSQL; enable backups and Multi-AZ if needed.
- Store DB credentials in Secrets Manager and fetch them at container startup (or inject via instance role + env helper).
- Use an Application Load Balancer in front of your EC2 instances and Auto Scaling Groups for resilience.
- Send logs to CloudWatch using the awslogs driver or a sidecar.
- Configure healthchecks (Spring Actuator `/actuator/health`) and a readiness probe in your load balancer.

Monitoring & CI/CD
- Add CI pipeline to automatically build, test, and push images to ECR. Example: GitHub Actions or AWS CodeBuild.
- Use CloudWatch metrics and alerts for JVM metrics and request errors.

Notes
- The application already reads DB config from environment variables provided in `src/main/resources/application.yaml`.
- For local development use `docker-compose.yml` which wires a local Postgres to the app.
