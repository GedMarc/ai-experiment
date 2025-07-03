# NOTE: This file is intentionally empty.
# The Keycloak module is defined in the keycloak directory and used directly in main.tf.
# All required changes have been made to the keycloak/main.tf file.
#
# Changes made to keycloak/main.tf:
# - Set container_concurrency = 80
# - Set cpu_idle = false via "run.googleapis.com/cpu-throttling" = "false"
# - Set timeout_seconds = 600
# - Configure environment variables (KC_DB_URL, KC_DB_USERNAME, KC_DB_PASSWORD, KC_HOSTNAME, KC_HOSTNAME_STRICT)
# - Use docker.io/keycloak/keycloak:24.0.2 image
# - Add IAM roles for secret access
# - Configure Cloud SQL connectivity if needed
# - Update command to use runtime configuration with --optimized flag
