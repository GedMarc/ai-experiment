# keycloak.tf - Identity provider for AI Experiment

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
}

variable "keycloak_admin_user" {
  description = "The admin username for Keycloak"
  type        = string
  default     = "admin"
}

variable "keycloak_admin_pass" {
  description = "The admin password for Keycloak"
  type        = string
  sensitive   = true
}

variable "keycloak_db_pass" {
  description = "The database password for Keycloak"
  type        = string
  sensitive   = true
  default     = ""  # Default to empty string, will generate a random password if not provided
}

variable "domain" {
  description = "The domain for Keycloak"
  type        = string
  default     = ""
}

variable "keycloak_image" {
  description = "The Keycloak container image"
  type        = string
  default     = "europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest"  # Using the Artifact Registry image
}

variable "artifact_registry_repo" {
  description = "The Artifact Registry repository name"
  type        = string
  default     = "shared"
}

variable "keycloak_cpu" {
  description = "CPU allocation for Keycloak"
  type        = string
  default     = "1"
}

variable "keycloak_memory" {
  description = "Memory allocation for Keycloak"
  type        = string
  default     = "2Gi"  # Updated to 2GB as per requirements
}

variable "keycloak_min_instances" {
  description = "Minimum number of Keycloak instances"
  type        = number
  default     = 1
}

variable "keycloak_max_instances" {
  description = "Maximum number of Keycloak instances"
  type        = number
  default     = 3
}

variable "enable_domain_mapping" {
  description = "Whether to enable domain mapping for Keycloak"
  type        = bool
  default     = false
}

variable "keycloak_db_url" {
  description = "The database URL for Keycloak"
  type        = string
  default     = "jdbc:postgresql://localhost:5432/keycloak"
}

variable "keycloak_db_username" {
  description = "The database username for Keycloak"
  type        = string
  default     = "keycloak"
}

variable "keycloak_hostname" {
  description = "The hostname for Keycloak"
  type        = string
  default     = ""
}

variable "is_cloud_sql" {
  description = "Whether the database is a Cloud SQL instance"
  type        = bool
  default     = false
}

variable "cloud_sql_instance_connection_name" {
  description = "The connection name of the Cloud SQL instance"
  type        = string
  default     = ""
}

# Create a dedicated service account for Keycloak
resource "google_service_account" "keycloak_sa" {
  account_id   = "${var.project_id}-keycloak-sa"
  display_name = "Service Account for Keycloak"
  project      = var.project_id
}

# Grant necessary permissions to the service account
resource "google_project_iam_member" "keycloak_sa_roles" {
  for_each = toset(concat([
    "roles/logging.logWriter",
    "roles/monitoring.metricWriter",
    "roles/artifactregistry.reader",
    "roles/secretmanager.secretAccessor"
  ], var.is_cloud_sql ? [
    "roles/cloudsql.client"
  ] : []))

  project = var.project_id
  role    = each.key
  member  = "serviceAccount:${google_service_account.keycloak_sa.email}"
}


# Create a secret for the Keycloak admin password
resource "google_secret_manager_secret" "keycloak_admin_password" {
  secret_id = "keycloak-admin-password"
  project   = var.project_id

  replication {
    auto {}
  }
}

# Store the Keycloak admin password in Secret Manager
resource "google_secret_manager_secret_version" "keycloak_admin_password" {
  secret      = google_secret_manager_secret.keycloak_admin_password.id
  secret_data = var.keycloak_admin_pass
}

# Use data source to reference existing secret created by postgres module
data "google_secret_manager_secret" "keycloak_db_password" {
  secret_id = "keycloak-db-password"
  project   = var.project_id
}

# Grant access to the admin password secret to the Keycloak service account
resource "google_secret_manager_secret_iam_member" "keycloak_secret_access" {
  project   = var.project_id
  secret_id = google_secret_manager_secret.keycloak_admin_password.secret_id
  role      = "roles/secretmanager.secretAccessor"
  member    = "serviceAccount:${google_service_account.keycloak_sa.email}"
}

# Grant access to the database password secret to the Keycloak service account
resource "google_secret_manager_secret_iam_member" "keycloak_db_secret_access" {
  project   = var.project_id
  secret_id = data.google_secret_manager_secret.keycloak_db_password.name
  role      = "roles/secretmanager.secretAccessor"
  member    = "serviceAccount:${google_service_account.keycloak_sa.email}"
}

# Deploy Keycloak to Cloud Run
resource "google_cloud_run_service" "keycloak" {
  name     = "keycloak"
  location = var.region
  project  = var.project_id

  template {
    spec {
      containers {
        image = var.keycloak_image

        resources {
          limits = {
            cpu    = var.keycloak_cpu
            memory = var.keycloak_memory
          }
        }

        # Set environment variables for Keycloak
        env {
          name  = "KEYCLOAK_ADMIN"
          value = var.keycloak_admin_user
        }

        env {
          name = "KEYCLOAK_ADMIN_PASSWORD"
          value_from {
            secret_key_ref {
              name = google_secret_manager_secret.keycloak_admin_password.secret_id
              key  = "latest"
            }
          }
        }

        env {
          name  = "KC_PROXY"
          value = "edge"
        }

        # Note: KC_HTTP_RELATIVE_PATH removed as it's a build-time option

        # Database configuration
        # KC_DB is a build-time parameter, already set in the Dockerfile

        env {
          name = "KC_DB_URL"
          value_from {
            secret_key_ref {
              name = "keycloak-db-url"
              key  = "latest"
            }
          }
        }

        env {
          name = "KC_DB_USERNAME"
          value_from {
            secret_key_ref {
              name = "keycloak-db-username"
              key  = "latest"
            }
          }
        }

        env {
          name = "KC_DB_PASSWORD"
          value_from {
            secret_key_ref {
              name = "keycloak-db-password"
              key  = "latest"
            }
          }
        }

        env {
          name  = "QUARKUS_DATASOURCE_JDBC_DRIVER"
          value = "org.postgresql.Driver"
        }

        env {
          name  = "QUARKUS_DATASOURCE_DB_KIND"
          value = "postgresql"
        }

        env {
          name  = "QUARKUS_DATASOURCE_JDBC_URL"
          value_from {
            secret_key_ref {
              name = "keycloak-db-url"
              key  = "latest"
            }
          }
        }

        env {
          name  = "QUARKUS_TRANSACTION_MANAGER_ENABLE_RECOVERY"
          value = "true"
        }

        env {
          name  = "KC_TRANSACTION_XA_ENABLED"
          value = "true"
        }

        # KC_HTTP_RELATIVE_PATH is a build-time parameter, already set in the Dockerfile

        env {
          name  = "KC_HOSTNAME"
          value = "auth.gedmarc.co.za"
        }

        env {
          name  = "KC_HOSTNAME_STRICT"
          value = "false"
        }

        # Command to start Keycloak in production mode (already optimized during build)
        command = ["/opt/keycloak/bin/kc.sh", "start"]
      }

      # Set the service account
      service_account_name = google_service_account.keycloak_sa.email

      # Set container concurrency
      container_concurrency = 80

      # Set timeout
      timeout_seconds = 600
    }

    metadata {
      annotations = {
        # Enable VPC connector
        "run.googleapis.com/vpc-access-connector" = var.vpc_connector
        "run.googleapis.com/vpc-access-egress"    = "all-traffic"

        # Set CPU allocation
        "autoscaling.knative.dev/minScale" = var.keycloak_min_instances
        "autoscaling.knative.dev/maxScale" = var.keycloak_max_instances

        # Keep CPU allocated during startup
        "run.googleapis.com/cpu-throttling" = "false"

        # Add Cloud SQL connector if using Cloud SQL
        "run.googleapis.com/cloudsql-instances" = var.is_cloud_sql ? var.cloud_sql_instance_connection_name : null
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
      "run.googleapis.com/ingress" = "all"
    }
  }

  # Depend on the service account and secrets
  depends_on = [
    google_service_account.keycloak_sa,
    google_project_iam_member.keycloak_sa_roles,
    google_secret_manager_secret_iam_member.keycloak_secret_access,
    google_secret_manager_secret_iam_member.keycloak_db_secret_access
  ]
}

# Allow unauthenticated access to Keycloak
resource "google_cloud_run_service_iam_member" "keycloak_public_access" {
  location = google_cloud_run_service.keycloak.location
  project  = google_cloud_run_service.keycloak.project
  service  = google_cloud_run_service.keycloak.name
  role     = "roles/run.invoker"
  member   = "allUsers"
}

# Domain mapping for Keycloak (if enabled)
resource "google_cloud_run_domain_mapping" "keycloak_domain_mapping" {
  count    = var.enable_domain_mapping && var.domain != "" ? 1 : 0
  location = var.region
  project  = var.project_id
  name     = var.domain

  metadata {
    namespace = var.project_id
  }

  spec {
    route_name = google_cloud_run_service.keycloak.name
  }
}

# Outputs
output "url" {
  description = "The URL of the Keycloak service"
  value       = google_cloud_run_service.keycloak.status[0].url
}

output "service_account_email" {
  description = "The email of the service account for Keycloak"
  value       = google_service_account.keycloak_sa.email
}

output "admin_user" {
  description = "The admin username for Keycloak"
  value       = var.keycloak_admin_user
}

output "domain" {
  description = "The custom domain mapped to Keycloak"
  value       = var.enable_domain_mapping && var.domain != "" ? var.domain : ""
}

output "realm" {
  description = "The Keycloak realm name"
  value       = "ai-experiment"
}

output "logs_url" {
  description = "The URL for the Keycloak logs"
  value       = "https://console.cloud.google.com/run/detail/${var.region}/${google_cloud_run_service.keycloak.name}/logs?project=${var.project_id}"
}

output "environment" {
  description = "Sanitized environment variables for Keycloak"
  value = {
    # Runtime environment variables
    KC_PROXY             = "edge"
    KC_DB_URL            = "jdbc:postgresql://<host>:5432/keycloak" # Sanitized
    KC_DB_USERNAME       = "keycloak" # Sanitized
    KC_DB_PASSWORD       = "<from Secret>" # Sanitized
    KC_HOSTNAME          = "auth.gedmarc.co.za"
    KC_HOSTNAME_STRICT   = "false"
    QUARKUS_DATASOURCE_JDBC_URL = "jdbc:postgresql://<host>:5432/keycloak" # Sanitized
    QUARKUS_DATASOURCE_JDBC_DRIVER = "org.postgresql.Driver"
    QUARKUS_DATASOURCE_DB_KIND = "postgresql"
    QUARKUS_TRANSACTION_MANAGER_ENABLE_RECOVERY = "true"
    KC_TRANSACTION_XA_ENABLED = "true"

    # Build-time parameters (set in Dockerfile)
    # KC_DB                = "postgres"
    # KC_HTTP_RELATIVE_PATH = "/auth"
  }
}
