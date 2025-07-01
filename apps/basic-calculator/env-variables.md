# 🔐 Environment Variables — Basic Calculator Module

> This file defines environment-specific secrets and runtime configuration for the `basic-calculator` application module.
> It overrides or extends values defined in the global `env-variables.md` document for this specific service.

---

## 🧭 Purpose

* Provide isolated environment control for the `basic-calculator` microservice
* Enable local Docker Compose, GitHub Actions CI, and Cloud Run deployments

---

## ✅ Required Runtime Variables

| Variable      | Description                                       | Required | Default / Notes                  |
| ------------- | ------------------------------------------------- | -------- | -------------------------------- |
| `APP_NAME`    | Unique identifier for logs, traces, etc.          | ✅        | `basic-calculator`               |
| `APP_ENV`     | Application environment (dev, qe, prod)           | ✅        | Must match Terraform workspace   |
| `APP_PORT`    | Port for local or containerized execution         | ✅        | `8080`                           |
| `APP_VERSION` | Semantic version string used in tracing/reporting | ✅        | auto-injected via build metadata |
| `LOG_LEVEL`   | Logging verbosity level (`DEBUG`, `INFO`, etc.)   | ✅        | `INFO`                           |

---

## 🔐 Secret Keys and Credentials

| Variable            | Description                            | Required | Notes                                  |
| ------------------- | -------------------------------------- | -------- | -------------------------------------- |
| `POSTGRES_HOST`     | Hostname or IP of Postgres DB instance | ✅        | Pulled from Terraform output           |
| `POSTGRES_PORT`     | Port of Postgres service               | ✅        | Typically `5432`                       |
| `POSTGRES_DB`       | Database schema name                   | ✅        | e.g. `calc_basic`                      |
| `POSTGRES_USER`     | Username for DB connection             | ✅        | Injected via Secret Manager            |
| `POSTGRES_PASSWORD` | Password for DB user                   | ✅        | Injected via Secret Manager            |
| `JWT_SECRET`        | Secret used for signing/verifying JWTs | ✅        | App-specific or shared rotation policy |

---

## 🔄 Optional Runtime Tuning

| Variable           | Description                             | Required | Notes                      |
| ------------------ | --------------------------------------- | -------- | -------------------------- |
| `HTTP_TIMEOUT_MS`  | HTTP request timeout in milliseconds    | ❌        | e.g. `5000`                |
| `POOL_MAX_SIZE`    | Max DB connection pool size             | ❌        | e.g. `10`                  |
| `ENABLE_TRACING`   | Whether OpenTelemetry tracing is active | ❌        | `true`/`false`             |
| `TRACING_ENDPOINT` | OTLP or Zipkin endpoint URI             | ❌        | Pulled from central config |

---

## 🧪 Test Overrides (Optional)

* `.env.test` can override:

  * Use of Testcontainers hostname: `localhost`
  * Temporary `APP_ENV=test`
  * Fixed port for contract tests: `8088`

---

## 📘 Notes

* Secrets must be provided via GCP Secret Manager for prod environments.
* Variables are injected via Cloud Build substitutions or Docker Compose `.env`.
* Validate values on CI via `ci/validate-env.js` or equivalent.
