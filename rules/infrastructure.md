# Infrastructure Rules

* Use Terraform with a locked backend for all environments.
* Terraform state must not error on reapply.
* Cloud Build configurations scoped to service type.
* Database must auto-provision (PostgreSQL 17, test via Testcontainers).
* JLink image generation must occur in a separate module for each executable.
* Each service must include a `terraform/` folder with module-specific infra.
* Top-level `environments/` folder must support `dev`, `qe`, `prod` environments.
* All state managed via Google Cloud Storage bucket, IAM-protected.