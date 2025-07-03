@echo off
REM Script to build and push the Keycloak image to Artifact Registry and deploy to Cloud Run

echo Building and pushing Keycloak image to Artifact Registry...

REM Ensure we're in the correct directory
cd /d "%~dp0"

REM Authenticate with Google Cloud (if not already authenticated)
gcloud auth print-access-token >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
  echo Please authenticate with Google Cloud:
  gcloud auth login
)

REM Set the project
echo Setting project to za-ai-experiment...
gcloud config set project za-ai-experiment

REM Configure Docker for Artifact Registry
echo Configuring Docker for Artifact Registry...
gcloud auth configure-docker europe-west1-docker.pkg.dev

REM Build the image
echo Building Keycloak image...
docker build -t europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest .

REM Push the image to Artifact Registry
echo Pushing image to Artifact Registry...
docker push europe-west1-docker.pkg.dev/za-ai-experiment/shared/keycloak:latest

REM Apply Terraform changes
echo Applying Terraform changes to update Cloud Run service...
cd ..\..\..\
terraform init
terraform apply -var-file=environments\dev.tfvars

echo Deployment complete! Keycloak should now be running on Cloud Run.
pause