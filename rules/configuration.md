# Configuration Rules

* Follow the [12-Factor App](https://12factor.net/) methodology.
* Configuration via environment variables only.
* No hardcoded ports, secrets, or URLs.
* Secrets managed via Secret Manager.
* Support default `.env`, `.env.dev`, `.env.qe`, `.env.prod` where applicable.
* Terraform variable files (`*.tfvars`) should not store secrets; reference them securely.