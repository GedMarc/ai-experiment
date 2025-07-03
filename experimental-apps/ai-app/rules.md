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

## Micro Frontend Integration Rules

### Role

The ExperimentalAIApp acts as the **host shell** for dynamically mounted micro frontends (MFEs). It provides routing, slot-based layout composition, and runtime configuration propagation.

### Mounting Strategy

* MFEs must expose:

  * `rules.md`
  * `openapi.md`
  * `frontend-implementation.md`
  * `env-variables.md`
* Shell queries build manifest and dynamically registers apps based on declared route prefix
* Loaded apps are expected to use path-based routing (e.g., `/calc`, `/wallet`)
* MFEs must not override or conflict with root-level paths

### Layout Composition

* Shell defines the following standard slot regions:

  * `app-header`
  * `sidebar`
  * `main-content`
  * `app-footer`
* MFEs must declare required slot usage in their `rules.md`

### Environment and Configuration Injection

* Shell reads global `orchestration/env-variables.md` and propagates merged values to each MFE
* Injected configuration includes:

  * `BASE_API_URL`
  * `AUTH_TOKEN`
  * `SHARED_CONFIG` (flat map)
* MFEs must read from provided config source or service

### Lifecycle Events and Logging

* MFEs must emit lifecycle and diagnostic events:

  * Mount success/failure
  * Slot injection duration
  * Route handover
* Logging must conform to the shared Vert.x event bus format

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

## Micro Frontend Compliance Checklist

* [ ] Has `rules.md` describing layout and routing slot expectations
* [ ] Has `openapi.md` with UI and API contract mapping
* [ ] Declares required `env-variables.md`
* [ ] Emits lifecycle and health events to shell logger
* [ ] Complies with layout and routing injection boundaries

---

## Known Gaps and Evolution Areas

* Future micro frontends may require capability discovery via schema rather than manifest
* Slot regions may need support for nested layouts or tab-based navigation containers
* Cross-app session sync and auth propagation will be formalized in `security-authentication.md`
* Component-level shadow DOM styling coordination may require additional ruleset

## Appendix

### Example MFE Declaration

```ts
{
  name: "basic-calculator",
  routePrefix: "/calc",
  slotsUsed: ["main-content"],
  requiredEnv: ["BASE_API_URL", "SHARED_CONFIG"],
  openapi: "apps/basic-calculator/openapi.md"
}
```

### Example Shell Injection Flow

1. Parse `orchestration/env-variables.md`
2. Resolve `build.manifest`
3. Register MFE routes
4. Inject config and mount
5. Await lifecycle handshake

---
