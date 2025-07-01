# GCP Infrastructure Structure

This document outlines the Google Cloud Platform (GCP) infrastructure structure for The AI Experiment.

## Resource Organization

* Project-based isolation for each environment (`dev`, `qe`, `prod`)
* VPC network with private subnets
* Cloud Run services for containerized applications
* Cloud SQL for PostgreSQL instances
* Artifact Registry for container images
* Cloud Storage for static assets and Terraform state
* Secret Manager for sensitive configuration

## Terraform Modules

* `core/` - Base infrastructure (VPC, subnets, IAM)
* `services/` - Application services (Cloud Run, Cloud SQL)
* `storage/` - Data storage resources (Cloud Storage, Artifact Registry)
* `security/` - Security resources (Secret Manager, IAM roles)

## Deployment Strategy

* Infrastructure as Code (IaC) using Terraform
* State stored in Cloud Storage with versioning
* CI/CD pipeline for infrastructure changes
* Separate state files for each environment

## Best Practices

* Use of Terraform workspaces
* Least privilege principle for IAM roles
* Encryption for data at rest and in transit
* Regular security scanning and compliance checks