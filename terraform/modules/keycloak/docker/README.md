# Keycloak Docker Image

This directory contains the Dockerfile and related files for building the Keycloak Docker image used in the AI Experiment project.

## Image Details

- Base Image: `quay.io/keycloak/keycloak:latest`
- Built with optimizations for Cloud Run
- Configured for PostgreSQL database
- Pre-built with required extensions and configurations

## Building and Pushing the Image

### Automatic Build via GitHub Actions

A GitHub Actions workflow has been set up to automatically build and push the Keycloak image to the Artifact Registry whenever changes are made to files in this directory.

To manually trigger the workflow:

1. Go to the GitHub repository
2. Navigate to Actions → Build and Push Keycloak Image
3. Click "Run workflow" and select the branch (usually `main`)
4. Click "Run workflow" to start the build

The workflow will:
1. Build the Keycloak image using the Dockerfile in this directory
2. Push the image to Artifact Registry at `europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest`
3. Trigger the Terraform workflow to apply changes and update the Cloud Run service

### Manual Build

If you need to build and push the image manually:

```bash
# Navigate to this directory
cd terraform/modules/keycloak/docker

# Build the image
docker build -t europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest .

# Authenticate with Google Cloud
gcloud auth configure-docker europe-west1-docker.pkg.dev

# Push the image
docker push europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest
```

After pushing the image manually, you'll need to run `terraform apply` to update the Cloud Run service.

## Troubleshooting

If you encounter the error "Image 'europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest' not found" when deploying with Terraform, it means the image hasn't been built and pushed to the Artifact Registry yet. Follow the steps above to build and push the image.

## Quick Start Guide

### Option 1: Using the Automated Script

The easiest way to build and push the Keycloak image to the Artifact Registry and deploy to Cloud Run is to use the provided script.

#### For Linux/Mac:

```bash
# Navigate to the script directory
cd terraform/modules/keycloak/docker

# Make the script executable
chmod +x build-push-deploy.sh

# Run the script
./build-push-deploy.sh
```

#### For Windows:

```cmd
# Navigate to the script directory
cd terraform\modules\keycloak\docker

# Run the batch script
build-push-deploy.bat
```

The script will handle authentication, building, pushing, and deploying automatically.

### Option 2: Manual Steps

If you prefer to run the commands manually:

1. Ensure you have Docker installed and running
2. Authenticate with Google Cloud:
   ```bash
   gcloud auth login
   gcloud config set project za-ai-experiment
   ```
3. Configure Docker for Artifact Registry:
   ```bash
   gcloud auth configure-docker europe-west1-docker.pkg.dev
   ```
4. Build the image:
   ```bash
   cd terraform/modules/keycloak/docker
   docker build -t europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest .
   ```
5. Push the image to Artifact Registry:
   ```bash
   docker push europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest
   ```
6. Apply Terraform changes to update the Cloud Run service:
   ```bash
   cd ../../../
   terraform apply -var-file=environments/dev.tfvars
   ```

Both options will create the Keycloak image in the Artifact Registry and deploy it to Cloud Run using the configuration from Terraform.
