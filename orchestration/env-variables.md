# 🔐 Environment & Secret Variables Standard

> 📁 Location: `orchestration/rules/env-variables.md`

## 🌍 Scope Levels

| Scope        | Description                            | Example Key                     |
| ------------ | -------------------------------------- | ------------------------------- |
| Organization | Available globally across all projects | `ORG_NAME`, `ORG_SUPPORT_EMAIL` |
| Project      | Specific to a GCP project deployment   | `GCP_PROJECT_ID`, `GCP_REGION`  |
| Application  | App/module-specific values             | `DB_URL`, `AUTH_TOKEN_SECRET`   |

---

## 📦 Structure

Each application **must** include:

* `.env.example` – Documented environment variables (no values)
* `.env` – Local values (gitignored)
* `rules/env-variables.md` – Description and categorization
* Reference in CI/CD (`.github/workflows/`, `cloudbuild.yaml`)
* Terraform `variables.tf` mapping sensitive values via `secrets`

---

## 🔧 Core Variables

### 🧱 Global

| Variable                  | Purpose                                        | Scope        |
| ------------------------- | ---------------------------------------------- | ------------ |
| `ORG_NAME`                | Display name for org-related interfaces        | Organization |
| `GCP_REGION`              | Primary GCP region                             | Project      |
| `GCP_PROJECT_ID`          | Unique project identifier                      | Project      |
| `FIRESTORE_EMULATOR_HOST` | Used for local dev with Firebase if applicable | Local only   |
| `BROWSERSTACK_KEY`        | Component testing in frontend                  | Project      |
| `SONAR_TOKEN`             | SonarQube analysis token                       | Project      |

---

### 🔐 Security

| Variable                 | Purpose                                | Scope       |
| ------------------------ | -------------------------------------- | ----------- |
| `JWT_SECRET`             | Used for signing authentication tokens | Application |
| `AUTH_ADMIN_USER`        | Default admin username                 | Application |
| `AUTH_ADMIN_PASS`        | Default admin password                 | Application |
| `COOKIE_SECRET`          | Frontend session security              | Application |
| `KEYCLOAK_CLIENT_ID`     | OAuth client ID                        | Application |
| `KEYCLOAK_CLIENT_SECRET` | OAuth secret                           | Secret      |

---

### 📂 Database

| Variable             | Purpose                       | Scope       |
| -------------------- | ----------------------------- | ----------- |
| `DB_URL`             | JDBC or reactive URL          | Application |
| `DB_USER`            | Database username             | Secret      |
| `DB_PASS`            | Database password             | Secret      |
| `DB_POOL_SIZE`       | Initial DB pool configuration | Application |
| `PG_PASSWORD_SECRET` | Terraform secret key name     | Project     |

---

### 🧪 Testing

| Variable                  | Purpose                                     | Scope       |
| ------------------------- | ------------------------------------------- | ----------- |
| `TEST_DB_CONTAINER_IMAGE` | Postgres image for Testcontainers           | Application |
| `ENABLE_DEBUG_LOGS`       | Enables verbose logging                     | Application |
| `SKIP_INTEGRATION_TESTS`  | Used to conditionally run full system tests | Project     |

---

### 🔭 Observability

| Variable                  | Purpose                                        | Scope       |
| ------------------------- | ---------------------------------------------- | ----------- |
| `PROMETHEUS_METRICS_PATH` | Custom path for Prometheus scraping            | Application |
| `LOG_LEVEL`               | Logging verbosity: DEBUG / INFO / WARN / ERROR | Application |
| `EXPORT_LOGS_TO`          | Stackdriver / GCS / stdout                     | Application |

---

## 🧰 Tooling-Specific Notes

* `.env.example` must **always** match `.env` keys
* Terraform modules should validate required secrets via input variables
* GitHub Actions must consume secrets from repository/environment scope
* Cloud Build substitutions (`_VAR_NAME`) must be defined per trigger
* Docker Compose should pull from `.env` or inline `env_file:` reference
