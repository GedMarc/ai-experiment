#!/bin/bash
# Script to build and push the Keycloak image to Artifact Registry and deploy to Cloud Run

# Exit on error
set -e

echo "Building and pushing Keycloak image to Artifact Registry..."

# Ensure we're in the correct directory
cd "$(dirname "$0")"

# Authenticate with Google Cloud (if not already authenticated)
if ! gcloud auth print-access-token &>/dev/null; then
  echo "Please authenticate with Google Cloud:"
  gcloud auth login
fi

# Set the project
echo "Setting project to za-ai-experiment..."
gcloud config set project za-ai-experiment

# Configure Docker for Artifact Registry
echo "Configuring Docker for Artifact Registry..."
gcloud auth configure-docker europe-west1-docker.pkg.dev

# Build the image
echo "Building Keycloak image..."
docker build -t europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest .

# Push the image to Artifact Registry
echo "Pushing image to Artifact Registry..."
docker push europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest

# Apply Terraform changes
echo "Applying Terraform changes to update Cloud Run service..."
cd ../../../
terraform init
terraform apply -var-file=environments/dev.tfvars

echo "Deployment complete! Keycloak should now be running on Cloud Run."