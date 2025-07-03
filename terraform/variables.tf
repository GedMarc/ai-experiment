# variables.tf - Input declarations for AI Experiment

variable "project_id" {
  description = "The GCP project ID"
  type        = string
  default     = "za-ai-experiment"
}

variable "region" {
  description = "The GCP region for resources"
  type        = string
  default     = "europe-west1"
}

variable "root_domain" {
  description = "The root domain for the application"
  type        = string
  default     = "https://gedmarc.co.za/ai-experiment"
}

variable "auth_domain" {
  description = "The domain for the authentication service"
  type        = string
  default     = "https://auth.gedmarc.co.za"
}

variable "artifact_registry_repo" {
  description = "The name of the Artifact Registry repository"
  type        = string
  default     = "shared"
}

variable "keycloak_admin_user" {
  description = "The admin username for Keycloak"
  type        = string
  default     = "admin"
  sensitive   = true
}

variable "keycloak_admin_password" {
  description = "The admin password for Keycloak"
  type        = string
  sensitive   = true
}

variable "keycloak_db_password" {
  description = "The database password for Keycloak"
  type        = string
  sensitive   = true
  default     = ""  # Default to empty string, will generate a random password if not provided
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

variable "environment" {
  description = "The deployment environment (dev, qe, prod)"
  type        = string
  default     = "dev"
}

variable "java_version" {
  description = "The Java version to use"
  type        = string
  default     = "24"
}

variable "vpc_connector_range" {
  description = "The IP CIDR range for the VPC connector"
  type        = string
  default     = "10.8.0.0/28"
}

variable "service_account_roles" {
  description = "Default IAM roles for service accounts"
  type        = list(string)
  default = [
    "roles/logging.logWriter",
    "roles/monitoring.metricWriter",
    "roles/artifactregistry.reader"
  ]
}

variable "enable_vpc_access" {
  description = "Whether to enable VPC access for Cloud Run services"
  type        = bool
  default     = true
}

variable "enable_domain_mapping" {
  description = "Whether to enable domain mapping for Cloud Run services"
  type        = bool
  default     = false
}
