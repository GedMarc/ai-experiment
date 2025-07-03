# postgres/main.tf - PostgreSQL Cloud SQL instance for AI Experiment

variable "project_id" {
  description = "The GCP project ID"
  type        = string
}

variable "region" {
  description = "The GCP region for resources"
  type        = string
}

variable "instance_name" {
  description = "The name of the PostgreSQL instance"
  type        = string
  default     = "ai-experiment-postgres"
}

variable "database_version" {
  description = "The PostgreSQL version"
  type        = string
  default     = "POSTGRES_17"
}

variable "tier" {
  description = "The machine type for the instance"
  type        = string
  default     = "db-g1-small" # Alternatively: db-f1-micro
}

variable "disk_size" {
  description = "The disk size in GB"
  type        = number
  default     = 10
}

variable "database_name" {
  description = "The name of the database"
  type        = string
  default     = "main"
}

variable "user_name" {
  description = "The database username"
  type        = string
  default     = "keycloak"
}

variable "user_password" {
  description = "The database password"
  type        = string
  sensitive   = true
}

variable "vpc_network" {
  description = "The VPC network name"
  type        = string
}

# Create a private IP address for the PostgreSQL instance
resource "google_compute_global_address" "private_ip_address" {
  name          = "${var.instance_name}-private-ip"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 16
  network       = var.vpc_network
  project       = var.project_id
}

# Create a VPC peering connection for private IP
resource "google_service_networking_connection" "private_vpc_connection" {
  network                 = var.vpc_network
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.private_ip_address.name]
}

# Create the PostgreSQL instance
resource "google_sql_database_instance" "postgres" {
  name             = var.instance_name
  database_version = var.database_version
  region           = var.region
  project          = var.project_id

  settings {
    tier              = var.tier
    availability_type = "ZONAL"
    disk_size         = var.disk_size
    disk_type         = "PD_SSD"

    backup_configuration {
      enabled            = true
      start_time         = "02:00"
      binary_log_enabled = false
    }

    ip_configuration {
      ipv4_enabled    = true
      private_network = var.vpc_network
      require_ssl     = false
    }

    database_flags {
      name  = "max_connections"
      value = "100"
    }
  }

  deletion_protection = true

  depends_on = [
    google_service_networking_connection.private_vpc_connection
  ]
}

# Create the main database
resource "google_sql_database" "database" {
  name     = var.database_name
  instance = google_sql_database_instance.postgres.name
  project  = var.project_id
}

# Create the keycloak database
resource "google_sql_database" "keycloak_database" {
  name     = "keycloak"
  instance = google_sql_database_instance.postgres.name
  project  = var.project_id
}

# Create the keycloak user
resource "google_sql_user" "user" {
  name     = var.user_name
  instance = google_sql_database_instance.postgres.name
  password = var.user_password
  project  = var.project_id
}

# Create secrets for database connection
resource "google_secret_manager_secret" "db_url" {
  secret_id = "keycloak-db-url"
  project   = var.project_id

  replication {
    auto {}
  }
}

resource "google_secret_manager_secret_version" "db_url" {
  secret      = google_secret_manager_secret.db_url.id
  secret_data = "jdbc:postgresql://${google_sql_database_instance.postgres.private_ip_address}:5432/keycloak"
}

resource "google_secret_manager_secret" "db_username" {
  secret_id = "keycloak-db-username"
  project   = var.project_id

  replication {
    auto {}
  }
}

resource "google_secret_manager_secret_version" "db_username" {
  secret      = google_secret_manager_secret.db_username.id
  secret_data = var.user_name
}

# Create a secret for the database password
resource "google_secret_manager_secret" "db_password" {
  secret_id = "keycloak-db-password"
  project   = var.project_id

  replication {
    auto {}
  }
}

# Store the database password in Secret Manager
resource "google_secret_manager_secret_version" "db_password" {
  secret      = google_secret_manager_secret.db_password.id
  secret_data = var.user_password
}

# Outputs
output "instance_name" {
  description = "The name of the PostgreSQL instance"
  value       = google_sql_database_instance.postgres.name
}

output "connection_name" {
  description = "The connection name of the PostgreSQL instance"
  value       = google_sql_database_instance.postgres.connection_name
}

output "public_ip_address" {
  description = "The public IP address of the PostgreSQL instance"
  value       = google_sql_database_instance.postgres.public_ip_address
}

output "private_ip_address" {
  description = "The private IP address of the PostgreSQL instance"
  value       = google_sql_database_instance.postgres.private_ip_address
}

output "database_name" {
  description = "The name of the database"
  value       = google_sql_database.database.name
}

output "user_name" {
  description = "The database username"
  value       = google_sql_user.user.name
}

output "db_url" {
  description = "The JDBC URL for the database"
  value       = "jdbc:postgresql://${google_sql_database_instance.postgres.private_ip_address}:5432/keycloak"
  sensitive   = true
}

output "db_url_secret_id" {
  description = "The secret ID for the database URL"
  value       = google_secret_manager_secret.db_url.secret_id
}

output "db_username_secret_id" {
  description = "The secret ID for the database username"
  value       = google_secret_manager_secret.db_username.secret_id
}

output "db_password_secret_id" {
  description = "The secret ID for the database password"
  value       = google_secret_manager_secret.db_password.secret_id
}
