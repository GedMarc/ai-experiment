# artifact-registry.tf - Shared container registry for AI Experiment

variable "project_id" {
  description = "The GCP project ID"
  type        = string
}

variable "region" {
  description = "The GCP region for resources"
  type        = string
}


variable "repository_id" {
  description = "The ID of the Artifact Registry repository"
  type        = string
  default     = "shared"
}

variable "repository_description" {
  description = "The description of the Artifact Registry repository"
  type        = string
  default     = "Shared Docker repository for AI Experiment"
}

variable "repository_format" {
  description = "The format of the repository"
  type        = string
  default     = "DOCKER"
}

# Create a dedicated service account for Artifact Registry
resource "google_service_account" "artifact_registry_sa" {
  account_id   = "${var.project_id}-ar-sa"
  display_name = "Service Account for Artifact Registry"
  project      = var.project_id
}

# Grant necessary permissions to the service account
resource "google_project_iam_member" "artifact_registry_sa_roles" {
  for_each = toset([
    "roles/artifactregistry.admin",
    "roles/storage.objectViewer"
  ])

  project = var.project_id
  role    = each.key
  member  = "serviceAccount:${google_service_account.artifact_registry_sa.email}"
}

# Create the Artifact Registry repository
resource "google_artifact_registry_repository" "repository" {
  provider      = google-beta
  location      = var.region
  repository_id = var.repository_id
  description   = var.repository_description
  format        = var.repository_format
  project       = var.project_id

  depends_on = [
    google_service_account.artifact_registry_sa,
    google_project_iam_member.artifact_registry_sa_roles
  ]
}

# Grant read access to all authenticated users
resource "google_artifact_registry_repository_iam_member" "reader" {
  provider   = google-beta
  project    = var.project_id
  location   = google_artifact_registry_repository.repository.location
  repository = google_artifact_registry_repository.repository.name
  role       = "roles/artifactregistry.reader"
  member     = "allAuthenticatedUsers"
}

# Grant write access to the Artifact Registry service account
resource "google_artifact_registry_repository_iam_member" "writer" {
  provider   = google-beta
  project    = var.project_id
  location   = google_artifact_registry_repository.repository.location
  repository = google_artifact_registry_repository.repository.name
  role       = "roles/artifactregistry.writer"
  member     = "serviceAccount:${google_service_account.artifact_registry_sa.email}"
  depends_on = [google_artifact_registry_repository.repository]
}

# Outputs
output "repository_id" {
  description = "The ID of the Artifact Registry repository"
  value       = google_artifact_registry_repository.repository.repository_id
}

output "repository_url" {
  description = "The URL of the Artifact Registry repository"
  value       = "${var.region}-docker.pkg.dev/${var.project_id}/${google_artifact_registry_repository.repository.repository_id}"
}

output "service_account_email" {
  description = "The email of the service account for Artifact Registry"
  value       = google_service_account.artifact_registry_sa.email
}
