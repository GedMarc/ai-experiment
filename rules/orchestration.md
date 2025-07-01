# 📦 Orchestration Rules for The AI Experiment

## 🎯 Purpose

This document defines orchestration rules for infrastructure, CI/CD, environment separation, and deployment strategy across all components of The AI Experiment — including:

* `apps/experimental-ai-app`
* `apps/basic-calculator`
* `apps/advanced-calculator`

It ensures alignment between backend services, frontend micro apps, infrastructure provisioning, and release lifecycles.

---

## 🛠️ Infrastructure as Code (Terraform)

### Modules:

* GCP IAM + Service Accounts
* Cloud Run per module (backend + frontend)
* PostgreSQL 17 instances per environment
* Cloud VPC Connector & Subnets
* Secret Manager integration
* Cloud Monitoring + Prometheus
* Native Keycloak (optionally provisioned via container or realm import)

### State & Safety:

* Modular state files (`terraform/envs/dev.tfstate`, etc.)
* Always-pass plan safety: fail on drift, block misconfigured destructive updates
* Structured destroy path for complete re-creation

> Note: Redis is excluded; Hazelcast to be reviewed later.

---

## 🔁 GitHub Actions CI/CD

### Branching Strategy

* `dev` → Development
* `qe` → QA/Staging
* `main` → Production/Release

### Trigger Points

* PRs: Run test + coverage checks, update SonarQube
* Merges:

  * Run full test matrix
  * Version tagging (Maven Release Plugin)
  * Module-specific release
  * Avoid infinite loops via commit marker tagging

---

## 🧪 Testing Strategy

### Backend

* Unit + integration tests with Testcontainers (PostgreSQL + Keycloak)
* JaCoCo/Kover coverage
* Contract-driven simulation via Pact or EventBus tests

### Frontend

* Angular component tests
* Flow coverage using BrowserStack
* `ng serve` for local shell with `proxy.conf.json` for micro-frontend injection

---

## 📄 Documentation Rules

* `README.md` per module with usage, API, diagrams
* Mermaid support for guides, flows, architecture
* GitHub Pages builds from `main` with version tags
* Release notes automatically generated on tags
* `docs/` folder reflects all exported platform docs

---

## 📦 Dependency Management

* BOM defined at `/bom/pom.xml` for shared versioning
* Backends reference via `dependencyManagement`
* Shared shaded dependencies (e.g., Hibernate Validator, PostgreSQL JDBC)

---

## 🚀 Runtime Bundles

* JLink modules for backend runners (Java 24+, JPMS)
* Excluded from test cycles
* Produced via `jlink-maven-plugin` per executable launcher module

---

## 🔍 Observability

* Tracing via GCP native tools + Cloud Trace
* Logging format adjusted (human-readable local, structured prod)
* Prometheus metrics exposed at `/prometheus`
* Health endpoints (via Vert.x Health) exposed at `/health`, `/info`, `/metrics`
* All observability endpoints are public by default

---

## 🔒 Security Conventions

* JWT-based Keycloak validation
* Role-based access in EventBus and HTTP endpoints
* Admin/test users predefined in Keycloak realm (non-prod)

---

## 🧩 Frontend Shell & Micro Frontends

* Frontend shell determines menu injection by build presence
* Auth routing enforced via role claims in JWT
* Web components and micro frontends use Angular 20 + W3C Custom Elements
* BrowserStack component validation required per PR

---

## ✅ Summary

* Terraform ensures recreate-on-demand environments
* GitHub Actions automate test + release flow
* Module ownership scoped per `apps/*` folder
* Observability, traceability, and docs baked into delivery
* The AI Experiment is testable, observable, deployable — from commit to Cloud Run ☁️
