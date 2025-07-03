# postgres.tf - PostgreSQL Cloud SQL instance for AI Experiment
# Note: Keycloak is now using PostgreSQL 17 for its database

module "postgres" {
  source = "./modules/postgres"

  project_id  = var.project_id
  region      = var.region
  vpc_network = google_compute_network.vpc_network.id
  user_password = var.keycloak_db_password != "" ? var.keycloak_db_password : random_password.keycloak_db_password[0].result

  depends_on = [
    google_project_service.required_apis["sqladmin.googleapis.com"],
    google_compute_network.vpc_network
  ]
}

# Generate a random password for the Keycloak database if not provided
resource "random_password" "keycloak_db_password" {
  count   = var.keycloak_db_password == "" ? 1 : 0
  length  = 16
  special = true
}
