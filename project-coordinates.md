# 📌 Project Coordinates — AI Experiment

This document defines the foundational metadata, repository structure, GCP layout, and Maven conventions used throughout the AI Experiment platform. All infrastructure, CI/CD, and generation logic should refer to this as the canonical source.

---

## 🗂️ GitHub Configuration

| Field           | Value                       |
| --------------- | --------------------------- |
| GitHub Org/User | `GedMarc`                   |
| Repository      | `ai-experiment` (monorepo)  |
| Branch Strategy | `main` → `qe` → `prod`      |
| CI/CD Approach  | Trunk-based, full pipeline  |
| Submodules      | ✅ Enabled for app isolation |

### GitHub Actions Environment

* `dev`, `qe`, `prod`
* Requires matrix support for deployments

### Secrets Checklist (per environment)

| Secret Name                   | Description                           | Example Default       |
| ----------------------------- | ------------------------------------- | --------------------- |
| `GCP_PROJECT_ID`              | GCP project ID                        | `za-ai-experiment`    |
| `GCP_SA_KEY`                  | Terraform + Deploy SA JSON            | —                     |
| `REGION`                      | Cloud Run region                      | `europe-west1`        |
| `ARTIFACT_REGISTRY_REPO`      | Container registry repo               | `shared`              |
| `KEYCLOAK_ADMIN_USER`         | Keycloak bootstrap admin user         | `admin`               |
| `KEYCLOAK_ADMIN_PASSWORD`     | Keycloak admin pass                   | —                     |
| `JWT_TEST_TOKEN`              | Sample dev token                      | for test environments |
| `FIREBASE_API_KEY` (optional) | If Firebase used in place of Keycloak | —                     |

> All secrets must be declared in GitHub environment configuration.

---

## ☁️ Google Cloud Platform

| Field             | Value                                 |
| ----------------- | ------------------------------------- |
| Project ID        | `za-ai-experiment`                    |
| Billing Enabled   | ✅ Yes                                 |
| Quota Strategy    | Soft ceiling at \$50                  |
| Region            | `europe-west1`                        |
| Artifact Registry | `shared`                              |
| Cloud Run Naming  | Descriptive per module                |
| VPC               | Single regional default               |
| Public Hostname   | `https://gedmarc.co.za/ai-experiment` |

### Identity

* 🔐 **Keycloak is the identity provider.**
* Hosted via Terraform on GCP (Cloud Run or Compute Engine depending on cost)
* Realm: `ai-experiment`
* URL: `https://auth.gedmarc.co.za/ai-experiment` (custom domain)
* Uses Cloud Run **domain mapping (preview)** for public apps
* Internal routing via private VPC connectors

---

## 📆 Event & Messaging Structure

* All internal service-to-service events will use **CloudEvents v1.0** compliant structure
* Format: `application/cloudevents+json`
* Propagated over:

  * Vert.x EventBus (internal pub/sub)
  * HTTPS + Cloud Run with CloudEvents headers

### CloudEvent Fields

| Field    | Description                   |
| -------- | ----------------------------- |
| `id`     | Unique UUID per event         |
| `source` | Service URI (e.g. `/calc`)    |
| `type`   | Event name (e.g. `calc.done`) |
| `time`   | ISO timestamp                 |
| `data`   | Application payload           |

### Extensions & Observability Addons

The following CloudEvents extensions are supported and required for internal traceability:

| Extension Key    | Description                                 |
| ---------------- | ------------------------------------------- |
| `traceparent`    | W3C trace context (for distributed tracing) |
| `spancontext`    | Custom span ID + parent span (optional)     |
| `correlation_id` | Business correlation ID for audit trail     |
| `request_id`     | Downstream request tracking                 |

> These values are injected by the shell and propagated by Vert.x middleware.

---

## 📆 Maven Build Conventions

| Field              | Value                              |
| ------------------ | ---------------------------------- |
| Group ID           | `za.co.ai.experiment`              |
| Artifact Format    | `<app>-<component>`                |
| Base Package       | `za.co.ai.experiment.<app>.<comp>` |
| JPMS Module Format | `za.co.ai.experiment.<app>.<comp>` |
| Test Module Suffix | `.tests` (JPMS + Package)          |
| Java Version       | JDK 24                             |
| Versioning         | SemVer + Maven Release Plugin      |

### Example:

* Artifact: `basic-calculator-api`
* Module: `za.co.ai.experiment.calc.basic`
* Test: `za.co.ai.experiment.calc.basic.tests`

---

## 📌 Notes

* Environment variables must have default values for local execution
* Test modules must be declared as separate JPMS modules
* Artifact Registry, Cloud Run, Keycloak provisioning should all be driven from Terraform
* Secret names must align across Terraform + GitHub + CI
* Domain mapping will be applied *after* Cloud Run deployment completes successfully
* Internal app communication must be secure, use VPC or Auth headers, and rely on CloudEvent format
* CloudEvents must include traceability extensions for platform observability

---
