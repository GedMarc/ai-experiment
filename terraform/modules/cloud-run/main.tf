# cloud-run.tf - Reusable Cloud Run service module

variable "service_name" {
  description = "The name of the Cloud Run service"
  type        = string
}

variable "image" {
  description = "The container image to deploy"
  type        = string
}

variable "project_id" {
  description = "The GCP project ID"
  type        = string
}

variable "region" {
  description = "The GCP region for resources"
  type        = string
}

variable "vpc_connector" {
  description = "The VPC connector ID"
  type        = string
  default     = ""
}

variable "service_account_email" {
  description = "The service account email to use for the service"
  type        = string
  default     = ""
}

variable "environment_variables" {
  description = "Environment variables to set on the service"
  type        = map(string)
  default     = {}
}

variable "secrets" {
  description = "Secrets to mount on the service"
  type        = list(object({
    name    = string
    version = string
    path    = string
  }))
  default     = []
}

variable "cpu" {
  description = "CPU allocation for the service"
  type        = string
  default     = "1"
}

variable "memory" {
  description = "Memory allocation for the service"
  type        = string
  default     = "512Mi"
}

variable "min_instances" {
  description = "Minimum number of instances"
  type        = number
  default     = 0
}

variable "max_instances" {
  description = "Maximum number of instances"
  type        = number
  default     = 10
}

variable "timeout_seconds" {
  description = "Request timeout in seconds"
  type        = number
  default     = 300
}

variable "ingress" {
  description = "Ingress traffic setting"
  type        = string
  default     = "all"
}

variable "enable_vpc_access" {
  description = "Whether to enable VPC access"
  type        = bool
  default     = true
}

variable "domain" {
  description = "Custom domain to map to the service"
  type        = string
  default     = ""
}

variable "enable_domain_mapping" {
  description = "Whether to enable domain mapping"
  type        = bool
  default     = false
}

# Create a dedicated service account for the Cloud Run service
resource "google_service_account" "service_account" {
  account_id   = "${var.service_name}-sa"
  display_name = "Service Account for ${var.service_name}"
  project      = var.project_id
}

# Grant minimal permissions to the service account
resource "google_project_iam_member" "service_account_roles" {
  for_each = toset([
    "roles/logging.logWriter",
    "roles/monitoring.metricWriter",
    "roles/artifactregistry.reader"
  ])

  project = var.project_id
  role    = each.key
  member  = "serviceAccount:${google_service_account.service_account.email}"
}

# Deploy the Cloud Run service
resource "google_cloud_run_service" "service" {
  name     = var.service_name
  location = var.region
  project  = var.project_id

  template {
    spec {
      containers {
        image = var.image
        
        resources {
          limits = {
            cpu    = var.cpu
            memory = var.memory
          }
        }

        # Set environment variables if provided
        dynamic "env" {
          for_each = var.environment_variables
          content {
            name  = env.key
            value = env.value
          }
        }

        # Mount secrets if provided
        dynamic "volume_mounts" {
          for_each = var.secrets
          content {
            name       = volume_mounts.value.name
            mount_path = volume_mounts.value.path
          }
        }
      }

      # Set the service account
      service_account_name = var.service_account_email != "" ? var.service_account_email : google_service_account.service_account.email
      
      # Set container concurrency
      container_concurrency = 80
      
      # Set timeout
      timeout_seconds = var.timeout_seconds
    }

    metadata {
      annotations = {
        # Enable VPC connector if specified
        "run.googleapis.com/vpc-access-connector" = var.enable_vpc_access ? var.vpc_connector : null
        "run.googleapis.com/vpc-access-egress"    = var.enable_vpc_access ? "all-traffic" : null
        
        # Set CPU allocation
        "autoscaling.knative.dev/minScale" = var.min_instances
        "autoscaling.knative.dev/maxScale" = var.max_instances
      }
    }
  }

  # Set traffic to 100% to latest revision
  traffic {
    percent         = 100
    latest_revision = true
  }

  # Set ingress settings
  metadata {
    annotations = {
      "run.googleapis.com/ingress" = var.ingress
    }
  }

  # Depend on the service account
  depends_on = [
    google_service_account.service_account,
    google_project_iam_member.service_account_roles
  ]
}

# Allow unauthenticated access to the service
resource "google_cloud_run_service_iam_member" "public_access" {
  location = google_cloud_run_service.service.location
  project  = google_cloud_run_service.service.project
  service  = google_cloud_run_service.service.name
  role     = "roles/run.invoker"
  member   = "allUsers"
}

# Domain mapping (if enabled)
resource "google_cloud_run_domain_mapping" "domain_mapping" {
  count    = var.enable_domain_mapping && var.domain != "" ? 1 : 0
  location = var.region
  project  = var.project_id
  name     = var.domain

  metadata {
    namespace = var.project_id
  }

  spec {
    route_name = google_cloud_run_service.service.name
  }
}

# Outputs
output "url" {
  description = "The URL of the deployed service"
  value       = google_cloud_run_service.service.status[0].url
}

output "service_account_email" {
  description = "The email of the service account"
  value       = google_service_account.service_account.email
}

output "name" {
  description = "The name of the service"
  value       = google_cloud_run_service.service.name
}

output "domain" {
  description = "The custom domain mapped to the service"
  value       = var.enable_domain_mapping && var.domain != "" ? var.domain : ""
}