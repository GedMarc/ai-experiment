# main.tf - Provider and project setup for AI Experiment

terraform {
  required_version = ">= 1.7.0"
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
    google-beta = {
      source  = "hashicorp/google-beta"
      version = "~> 5.0"
    }
  }

  # Uncomment this block to use a GCS backend for state storage
  # backend "gcs" {
  #   bucket = "za-ai-experiment-terraform-state"
  #   prefix = "terraform/state"
  # }
}

provider "google" {
  project = var.project_id
  region  = var.region
}

provider "google-beta" {
  project = var.project_id
  region  = var.region
}

# Enable required APIs for the project
resource "google_project_service" "required_apis" {
  for_each = toset([
    "cloudresourcemanager.googleapis.com",
    "serviceusage.googleapis.com",
    "iam.googleapis.com",
    "artifactregistry.googleapis.com",
    "run.googleapis.com",
    "vpcaccess.googleapis.com",
    "compute.googleapis.com",
    "secretmanager.googleapis.com",
    "logging.googleapis.com",
    "monitoring.googleapis.com",
    "cloudbuild.googleapis.com",
    "domains.googleapis.com",
    "sqladmin.googleapis.com",
    "servicenetworking.googleapis.com"
  ])

  project = var.project_id
  service = each.key

  disable_dependent_services = false
  disable_on_destroy         = false
}

# VPC Network for services
resource "google_compute_network" "vpc_network" {
  name                    = "${var.project_id}-vpc"
  auto_create_subnetworks = true
  depends_on              = [google_project_service.required_apis]
}

# VPC Connector for Cloud Run services
resource "google_vpc_access_connector" "connector" {
  name          = "vpc-connector"
  region        = var.region
  network       = google_compute_network.vpc_network.name
  ip_cidr_range = "10.8.0.0/28"
  depends_on    = [google_project_service.required_apis]
}

# Artifact Registry module
module "artifact_registry" {
  source     = "./modules/artifact-registry"
  project_id = var.project_id
  region     = var.region
  depends_on = [
    google_project_service.required_apis["artifactregistry.googleapis.com"]
  ]
}

# Grant necessary roles to the Artifact Registry service account for CI/CD
resource "google_project_iam_member" "ar_sa_roles" {
  for_each = toset([
    "roles/cloudbuild.builds.builder",
    "roles/run.admin",
    "roles/iam.serviceAccountUser",
    "roles/storage.objectViewer",
    "roles/logging.logWriter",
    "roles/artifactregistry.writer"
  ])

  project    = var.project_id
  role       = each.key
  member     = "serviceAccount:${module.artifact_registry.service_account_email}"
  depends_on = [module.artifact_registry]
}

# Keycloak identity provider
module "keycloak" {
  source              = "./modules/keycloak"
  project_id          = var.project_id
  region              = var.region
  vpc_connector       = google_vpc_access_connector.connector.id
  keycloak_admin_user = var.keycloak_admin_user
  keycloak_admin_pass = var.keycloak_admin_password
  keycloak_db_pass    = var.keycloak_db_password
  domain              = var.auth_domain

  # PostgreSQL configuration
  is_cloud_sql                     = true
  cloud_sql_instance_connection_name = module.postgres.connection_name
  keycloak_db_url                  = "jdbc:postgresql://${module.postgres.private_ip_address}:5432/${module.postgres.database_name}"
  keycloak_db_username             = module.postgres.user_name

  depends_on          = [google_project_service.required_apis, module.artifact_registry, module.postgres]
}

# Default Cloud Run service account with minimal permissions
resource "google_service_account" "default_run_sa" {
  account_id   = "${var.project_id}-default-sa"
  display_name = "Default Cloud Run Service Account"
  depends_on   = [google_project_service.required_apis]
}

# Grant minimal permissions to the default service account
resource "google_project_iam_member" "default_sa_roles" {
  for_each = toset([
    "roles/logging.logWriter",
    "roles/monitoring.metricWriter",
    "roles/artifactregistry.reader"
  ])

  project = var.project_id
  role    = each.key
  member  = "serviceAccount:${google_service_account.default_run_sa.email}"
}
