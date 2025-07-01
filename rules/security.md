# Security Rules

* OAuth2 and JWT via Vert.x modules.
* Keycloak used for authentication, modularized via shaded Moditect.
* Secure defaults must be enforced via GCP IAM and secrets.
* Minimal JLink distributions must exclude unsafe modules.
* Local environment must simulate security boundaries without backdoor bypasses.