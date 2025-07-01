# 📒 Implementation: The AI Experiment Application

## Purpose

This implementation document defines the structure, operational behavior, and architectural strategy of the core "ExperimentApp" platform — serving as a foundational base for modular, event-driven, cloud-native microservices and micro frontends.

---

# 🧩 Application Type

* **Domain-Driven** architecture
* **Event-Driven** communication (via Vert.x EventBus + optional TCP bridge)
* **Cloud-Native** deployment (GCP, Cloud Run, Cloud Build, Terraform-managed)
* **Micro Frontends & Microservices** composition with plugin-based modularity
* **Service-loading and JPMS** boundaries

---

# 📦 Runtime & Tooling

* **Java 24+, JPMS** with strict module boundaries
* **Vert.x 5.x**, Hibernate Reactive 3, PostgreSQL 17
* **Angular 20** with W3C Web Components
* **Terraform** for GCP-native resource provisioning
* **Docker Compose** for local orchestration
* **Testcontainers** for database integration tests
* **Maven 4**, GitHub Actions, SonarQube, BrowserStack

---

# 🗂️ Module Structure

### Core Modules:

* `platform-core`: foundational configuration, service loader registration
* `platform-config`: loads and injects runtime configuration
* `platform-health`: reports system and service component health via Vert.x Health
* `platform-tracing`: OpenTelemetry integration for GCP + local logging
* `platform-auth`: security boundaries using Keycloak (shaded, modularized)
* `platform-events`: shared event definitions + codecs
* `platform-db`: persistence configuration and base repository layer (shaded PG driver)

### Application Shell (Frontend):

* `shell-frontend`: navigation, login, layout for loaded microfrontends

### Environment Branches:

* `dev`, `qe`, `main`, aligned with GitHub Actions → Cloud Build

---

# 🧪 Testing Strategy

* Unit + integration tests (JUnit + Testcontainers)
* Module-level SonarQube coverage reports
* Microfrontend visual and behavior tests via BrowserStack
* E2E integration tests across all components
* Test-first principle for every logic unit

---

# 🔄 Communication

* **Async-first** event communication using Vert.x EventBus
* Optional TCP EventBus bridge for cross-platform clients
* Events use custom codecs, optionally Avro or JSON
* Microservice boundaries follow bounded context + versioned interfaces

---

# 🩺 Observability

* **Vert.x MicroProfile Health**: service, DB, message bus, config status
* **OpenTelemetry traces**: fed into GCP tracing tools
* **Structured logging**: JSON for cloud, human-readable locally
* **Service topology mapping**: spans + tags resolve communication flows

---

# 📚 Documentation & Release

* Markdown docs per module (`rules.md`, `guides.md`, `implementation.md`)
* GitHub Pages auto-generated on merge to `main`
* Mermaid for sequence, architecture, and ERD diagrams
* Release notes tagged and published with Maven Release Plugin

---

# 🧠 Modularity Rules

* No duplicate package names across modules (JPMS-safe)
* All service implementations are `provides` via service loader
* Consumers `use` interfaces via module-info only — no reflection
* Shaded libs for PG driver, Hibernate Validator, Keycloak OIDC Client

---

# 🚀 DevOps

* Terraform generates services, DBs, IAM accounts, firewalls, secrets
* Cloud Run executes JLink-slimmed images
* Cloud Build assigns 0-2 instances per backend, 1-1 for frontends
* CI runs on PR to update coverage and Sonar reports
* Merge triggers release: version bump, build, tag, publish

---

# 🧱 Frontend Behavior

* Micro frontends are Angular 20 apps exported as Web Components
* Shell app only renders microfrontends that are included in build
* Uses `loadCondition()` to dynamically resolve available MFEs
* Tested across browsers via BrowserStack, CI-connected

---
