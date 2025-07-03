# dev.tfvars - Default values for dev environment

project_id             = "za-ai-experiment"
region                 = "europe-west1"
root_domain            = "https://gedmarc.co.za/ai-experiment"
auth_domain            = "https://auth.gedmarc.co.za"
artifact_registry_repo = "shared"
keycloak_admin_user    = "admin"
# keycloak_admin_password is not set here for security reasons
# It should be provided via GitHub secrets or other secure means

environment            = "dev"
java_version           = "24"
vpc_connector_range    = "10.8.0.0/28"
enable_vpc_access      = true
enable_domain_mapping  = false

# Keycloak configuration
keycloak_db_url        = "jdbc:postgresql://localhost:5432/keycloak"
keycloak_db_username   = "keycloak"
keycloak_hostname      = ""
is_cloud_sql           = true
# cloud_sql_instance_connection_name is set in main.tf to use the postgres module's connection_name output
# A dedicated 'keycloak' database is created in the PostgreSQL instance with default schema

# Service account roles for dev environment
service_account_roles = [
  "roles/logging.logWriter",
  "roles/monitoring.metricWriter",
  "roles/artifactregistry.reader"
]
