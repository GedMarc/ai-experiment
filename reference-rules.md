# 📘 AI Experiment - Reference Rules Documentation

This document serves as a comprehensive reference for all rules, guidelines, and implementation details in the AI Experiment project. It is organized according to the Pact Chain defined in the PACT.md file: Rules, Guides, and Implementation.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Rules](#rules)
   - [Build and Publish](#build-and-publish)
   - [Configuration](#configuration)
   - [Backend](#backend)
   - [Frontend](#frontend)
   - [Security](#security)
   - [Infrastructure](#infrastructure)
   - [Documentation](#documentation)
   - [Development Process](#development-process)
   - [Orchestration](#orchestration)
   - [Integration](#integration)
   - [Specification](#specification)
3. [Guides](#guides)
   - [Integration Guides](#integration-guides)
   - [Upgrade Guides](#upgrade-guides)
   - [Authentication Guides](#authentication-guides)
   - [Database Guides](#database-guides)
4. [Implementation](#implementation)
   - [CI/CD](#ci-cd)
   - [Database](#database)
   - [Security](#security-implementation)
   - [Observability](#observability)
   - [Terraform](#terraform)
   - [Service Discovery](#service-discovery)

## Project Overview

The AI Experiment is a modular, cloud-native, event-driven system driven by multi-agent AI collaboration. It combines cloud-native microservices and micro frontends, domain-driven design (DDD), event-driven architecture (EDA), full automation of infrastructure, code generation, and CI/CD pipelines, with a documentation-first, rule-driven AI-assisted implementation approach.

### Project Structure

The project is organized into the following directories:

- `apps/`: Contains application modules that are built as JLink images
- `experimental-apps/`: Contains experimental application modules that are built as JLink images
- `libs/`: Contains library modules that are built as JARs and published to GitHub Packages
- `rules/`: Contains rules and guidelines for the project
- `guides/`: Contains contextual walkthroughs and module blueprints
- `implementation/`: Contains language- and runtime-specific implementation details

### Project Coordinates

The project uses the following coordinates:

- **GitHub**: Organization/User: `GedMarc`, Repository: `ai-experiment` (monorepo)
- **GCP**: Project ID: `za-ai-experiment`, Region: `europe-west1`
- **Maven**: Group ID: `za.co.ai.experiment`, Artifact Format: `<app>-<component>`

## Rules

### Build and Publish

#### Module Types

**`libs/` Directory (Shared Libraries)**

* Must follow JPMS (`module-info.java`)
* Must publish to GitHub Packages on commit/tag
* Must be independently buildable and installable
* Must not contain placeholder `.java` files
* Should include:
  * `lombok` (>= `1.18.38`)
  * `mapstruct` (`1.6.3`)
  * `jackson` (`2.18.3`) for JSON binding
  * `log4j2` for logging (optional SLF4J bridge)
  * If both Lombok and MapStruct are used together:
    ```xml
    <path>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok-mapstruct-binding</artifactId>
      <version>0.2.0</version>
    </path>
    ```

**`apps/` Directory (Microservices / Micro Frontends)**

* Must depend **only** on published artifacts (not local paths)
* Must build and test independently on GitHub Actions and GCP Cloud Build
* Must use shared runtime env vars (see `env-variables.md`)
* Must contain a corresponding JLink packaging module for runtime image generation
* Dockerfiles must build from JLink runtime images and can use the **smallest base container images** (no full JDK required)

#### Module Metadata & Structure

**Group ID**: `za.co.ai.experiment`

**Artifact ID Format**:
* Libraries: `lib-<function>` (e.g., `lib-core-utils`, `lib-core-observability`)
* Applications: `app-<name>` (e.g., `app-basic-calculator`)
* Runtime Images: `<app>-runtime` (e.g., `basic-calculator-runtime`)

**Package Naming**:
* Source code: `za.co.ai.experiment.<module>`
* Test code: `za.co.ai.experiment.<module>.tests`

**JPMS Naming**:
* Source: `za.co.ai.experiment.<module>`
* Test: `za.co.ai.experiment.<module>.tests`
  * Test modules must export and open their packages to JUnit platform modules:
  ```
  exports za.co.ai.experiment.<module>.tests to org.junit.platform.commons;
  opens za.co.ai.experiment.<module>.tests to org.junit.platform.commons;
  ```

#### GitHub Packages Publishing

Each library module must:
* Include a GitHub Actions workflow to publish to: `https://maven.pkg.github.com/GedMarc/ai-experiment`
* Use `mvn deploy` with:
  * GitHub token authentication
  * Project version via release plugin (SemVer)
* Include:
  ```xml
  <distributionManagement>
    <repository>
      <id>github</id>
      <url>https://maven.pkg.github.com/GedMarc/ai-experiment</url>
    </repository>
  </distributionManagement>
  ```

#### CI/CD Integration

**GitHub Actions**:
* Each module must include a `.github/workflows/publish-<module>.yml`
* Should run unit tests, `jlink` validation (where applicable), and deploy on `main`
* JLink modules must build runnable JDK 24 images from app dependencies

**Cloud Build**:
* Apps will be built/deployed using GCP-native Cloud Build triggers
* All app dependencies must resolve from GitHub Packages

#### Maven Enforcer Rules

We enforce:
* JDK compatibility (`maven.compiler.release`) — JDK 24
* Unique coordinates per module
* No duplicate dependencies
* No SNAPSHOT references for production builds

> ℹ️ Note: Maven Enforcer is currently not JDK 24 compatible — disable or override as needed.

#### Dependency Management

Root `pom.xml` should include:
* Centralized versions of:
  * `log4j2`
  * `lombok` (1.18.38+)
  * `jackson-databind` (2.19.0)
  * `mapstruct` (1.6.3)
  * `lombok-mapstruct-binding` (0.0.1, if applicable)
* Managed via `<dependencyManagement>`

#### Tips for AI Scaffold Generators

* Assume apps run on Cloud Build and deploy to Cloud Run
* Libraries must be published to GitHub Packages for consumption
* Do not generate empty `.java` files — focus on structure only
* All modules must include `module-info.java` with required `requires` clauses
* Test modules must mirror JPMS structure
* Default version is `1.0.0-SNAPSHOT`
* JLink builds must be in separate `<packaging>jlink</packaging>` modules
* Docker images must be based on runtime JLink builds using the smallest available base images (no JDKs)
* JLink plugin config **must** use updated syntax:
  ```xml
  <launcher>myapp=za.co.ai.experiment.app.module/com.example.Main</launcher>
  ```

### Configuration

* Follow the [12-Factor App](https://12factor.net/) methodology.
* Configuration via environment variables only.
* No hardcoded ports, secrets, or URLs.
* Secrets managed via Secret Manager.
* Support default `.env`, `.env.dev`, `.env.qe`, `.env.prod` where applicable.
* Terraform variable files (`*.tfvars`) should not store secrets; reference them securely.

### Backend

(Content from backend.md would be included here)

### Frontend

(Content from frontend.md would be included here)

### Security

(Content from security.md would be included here)

### Infrastructure

(Content from infrastructure.md would be included here)

### Documentation

(Content from documentation.md would be included here)

### Development Process

(Content from dev-process.md would be included here)

### Orchestration

(Content from orchestration.md would be included here)

### Integration

(Content from integration-vertx-web.md would be included here)

### Specification

#### OpenAPI Guidelines

(Content from specification/openapi-guidelines.md would be included here)

#### OpenAPI Implementation

(Content from specification/openapi-implementation.md would be included here)

#### OpenAPI

(Content from specification/openapi.md would be included here)

## Guides

### Integration Guides

(Content from guides/integration-guide.md and guides/integration-tcp-evenetbus-bridge.md would be included here)

### Upgrade Guides

(Content from guides/hibernate-upgrade.md and guides/vertx-upgrade.md would be included here)

### Authentication Guides

(Content from guides/oauth2-flow-guide.md would be included here)

### Database Guides

(Content from guides/postgres-client.md would be included here)

## Implementation

### CI/CD

(Content from implementation/ci-cd.md would be included here)

### Database

(Content from implementation/database.md would be included here)

### Security Implementation

(Content from implementation/security.md would be included here)

### Observability

#### Health

(Content from implementation/health.md would be included here)

#### Metrics

(Content from implementation/metrics.md would be included here)

#### Tracing

(Content from implementation/tracing.md would be included here)

### Terraform

#### GCP Structure

(Content from implementation/terraform/gcp-structure.md would be included here)

#### Terraform Deployment Rules

(Content from implementation/terraform/terraform-deployment-rules.md would be included here)

### Service Discovery

(Content from implementation/vertx-service-discovery.md would be included here)

## Event & Messaging Structure

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
