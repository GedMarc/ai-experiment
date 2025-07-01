# 📗 Rules: ExperimentalAIApp

## Purpose

This file defines the platform-level rules for the `ExperimentalAIApp`, which functions as the orchestrator of microservices and micro frontends under a unified event-driven architecture.

---

## App Context

* Located under `apps/ExperimentalAIApp`
* Acts as the root entry point for system deployment and interface exposure
* Hosts global configurations, shared services, and composite frontend shell

## Scope

* Aggregates runtime configuration, security context, tracing, health, and UI shell
* Only loads micro frontends and microservices included in current build scope
* Responsible for routing, health summaries, environment exposure

## Naming & Modules

* Each microservice follows naming: `apps/<app-name>/<service-name>`
* Each frontend follows: `apps/<app-name>/<frontend-name>`
* Module and Angular selectors must be prefixed by the app context, e.g., `expai-`

---

## Technology

* Java 24+, JPMS
* Vert.x 5.x
* Angular 20
* Service-loading for all plugin modules
* Terraform-based GCP provisioning

---

## Standards

* No framework-styling libraries allowed
* All services must expose health, metrics, trace
* All frontends must be Web Components with shadow DOM and scoped styles
* All modules must include: `rules.md`, `guides.md`, `implementation.md`

---

## Build & Deployment

* Only modules explicitly included in environment builds are loaded
* App shell must query build manifest to determine available features
* GitHub Actions + Maven handle coordinated versioning

---

## Testing

* Backend via JUnit + Testcontainers
* Frontend via BrowserStack, Cypress/Playwright
* Shared coverage gates

---

## Documentation

* GitHub Pages reflect this app’s structure and feature state
* Mermaid usage required for flows and diagrams

---

