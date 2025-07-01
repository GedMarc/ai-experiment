# 🌐 Environment Variables – Experimental AI App

This document outlines the complete list of required and optional environment variables that govern the behavior of the **Experimental AI App**, including its dynamic orchestration of plugins, authentication, frontend shells, and observability endpoints.

It builds upon base infrastructure rules found in `orchestration/rules/env-variables.md`.

---

## 🧪 Purpose

The Experimental AI App acts as the hosting container and gateway for multiple backend and frontend plugins (e.g., `basic-calculator`, `advanced-calculator`). It orchestrates shared responsibilities such as authentication, routing, menu rendering, logging, tracing, and health/metrics exposure.

---

## 🌍 Scope: Application-Level (Platform Container)

This configuration is required at:

* Cloud Run service deployment
* Central orchestration Docker Compose service
* CI/CD pipelines (e.g., GitHub Actions, Cloud Build)

---

## 🔧 Required Variables

| Key                      | Description                                              | Example Value                           |
| ------------------------ | -------------------------------------------------------- | --------------------------------------- |
| `APP_NAME`               | Identifies the orchestration layer for logs, metrics     | `experimental-ai-app`                   |
| `PORT`                   | Internal HTTP port used by Vert.x Web                    | `8080`                                  |
| `FRONTEND_MODULE_PATH`   | File path or mount location for frontend modules         | `/app/modules/frontends/`               |
| `BACKEND_MODULE_PATH`    | File path or mount location for backend modules          | `/app/modules/backends/`                |
| `ENABLED_MICROFRONTENDS` | Comma-separated list of enabled frontend modules by name | `basic-calculator,advanced-calculator`  |
| `JWT_PUBLIC_KEY_PATH`    | Path to JWT public key used for verifying tokens         | `/secrets/jwt.pub`                      |
| `KEYCLOAK_REALM_URL`     | Keycloak realm endpoint                                  | `https://auth.example.com/realms/expai` |
| `DEFAULT_ADMIN_USER`     | Bootstrap admin username (for experimentation only)      | `admineestrate`                         |
| `DEFAULT_ADMIN_PASS`     | Bootstrap admin password (for experimentation only)      | `unlockthedoorhandles123`               |

---

## 🔐 Optional / Infrastructure

| Key                      | Purpose                                         | Default        |
| ------------------------ | ----------------------------------------------- | -------------- |
| `TRACING_ENABLED`        | Enables Cloud Trace span propagation            | `true`         |
| `CLOUD_TRACE_PROJECT_ID` | Project ID for Trace and Monitoring reporting   | (env-specific) |
| `ENABLE_PROMETHEUS`      | Whether to expose Prometheus-compatible metrics | `true`         |
| `MENU_ROLE_FILTERING`    | Enable menu item filtering by user role         | `true`         |
| `CACHE_STRATEGY`         | Global caching strategy (none, memory, etc)     | `memory`       |

---

## 🔒 Secrets Handling

* Place sensitive keys (e.g., `DEFAULT_ADMIN_PASS`, `JWT_PUBLIC_KEY_PATH`) in **Secret Manager**.
* In CI/CD, use environment variables or substitutions with `cloudbuild.yaml` and GitHub Actions.

---

## 🔁 Local Development

When running locally via Docker Compose:

* Provide a `.env` file copied from `.env.example`
* Ensure file paths are relative to container mount structure

```dotenv
APP_NAME=experimental-ai-app
PORT=8080
FRONTEND_MODULE_PATH=./modules/frontends/
BACKEND_MODULE_PATH=./modules/backends/
ENABLED_MICROFRONTENDS=basic-calculator,advanced-calculator
JWT_PUBLIC_KEY_PATH=./secrets/jwt.pub
KEYCLOAK_REALM_URL=https://auth.example.com/realms/expai
DEFAULT_ADMIN_USER=admineestrate
DEFAULT_ADMIN_PASS=unlockthedoorhandles123
```

---

## 📦 Related

* `apps/experimental-ai-app/Dockerfile`
* `apps/experimental-ai-app/cloudbuild.yaml`
* `.github/workflows/ai-app-ci.yml`
* `orchestration/rules/env-variables.md`
* `apps/basic-calculator/env-variables.md`
* `apps/advanced-calculator/env-variables.md`
* `auth/keycloak/init.json` (Keycloak bootstrap realm and roles)
