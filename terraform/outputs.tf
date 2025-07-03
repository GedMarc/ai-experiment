# outputs.tf - URLs, domains, tokens for AI Experiment

output "project_id" {
  description = "The GCP project ID"
  value       = var.project_id
}

output "region" {
  description = "The GCP region for resources"
  value       = var.region
}

output "root_domain" {
  description = "The root domain for the application"
  value       = var.root_domain
}

output "auth_domain" {
  description = "The domain for the authentication service"
  value       = var.auth_domain
}

output "artifact_registry_url" {
  description = "The URL for the Artifact Registry"
  value       = "${var.region}-docker.pkg.dev/${var.project_id}/${var.artifact_registry_repo}"
}

output "vpc_connector" {
  description = "The VPC connector ID"
  value       = google_vpc_access_connector.connector.id
}

output "vpc_network" {
  description = "The VPC network name"
  value       = google_compute_network.vpc_network.name
}

output "keycloak_url" {
  description = "The URL for the Keycloak service"
  value       = module.keycloak.url
}

output "keycloak_admin_user" {
  description = "The admin username for Keycloak"
  value       = var.keycloak_admin_user
  sensitive   = true
}

output "keycloak_realm" {
  description = "The Keycloak realm name"
  value       = "ai-experiment"
}

output "keycloak_logs_url" {
  description = "The URL for the Keycloak logs"
  value       = module.keycloak.logs_url
}

output "default_service_account" {
  description = "The default service account email"
  value       = google_service_account.default_run_sa.email
}

output "artifact_registry_service_account" {
  description = "The Artifact Registry service account email"
  value       = module.artifact_registry.service_account_email
}

output "enabled_apis" {
  description = "The list of enabled APIs"
  value       = [for api in google_project_service.required_apis : api.service]
}

output "environment" {
  description = "The current deployment environment"
  value       = var.environment
}

output "java_version" {
  description = "The Java version used"
  value       = var.java_version
}

# Cloud Run service URLs will be added by the cloud-run module
# These are placeholders that will be populated when services are deployed
output "service_urls" {
  description = "URLs for deployed Cloud Run services"
  value       = {}
}

# PostgreSQL outputs
output "postgres_instance_name" {
  description = "The name of the PostgreSQL instance"
  value       = module.postgres.instance_name
}

output "postgres_connection_name" {
  description = "The connection name of the PostgreSQL instance"
  value       = module.postgres.connection_name
}

output "postgres_public_ip" {
  description = "The public IP address of the PostgreSQL instance"
  value       = module.postgres.public_ip_address
}

output "postgres_private_ip" {
  description = "The private IP address of the PostgreSQL instance"
  value       = module.postgres.private_ip_address
}

output "keycloak_db_url" {
  description = "The JDBC URL for Keycloak database"
  value       = "jdbc:postgresql://${module.postgres.private_ip_address}:5432/${module.postgres.database_name}"
  sensitive   = true
}

# Secret manager secrets
output "available_secrets" {
  description = "List of available secrets in Secret Manager"
  value       = [
    "keycloak-admin-password",
    "keycloak-db-password",
    "keycloak-db-url",
    "keycloak-db-username"
  ]
  sensitive   = true
}
