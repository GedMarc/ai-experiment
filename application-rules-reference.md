# 📱 AI Experiment - Application Rules Reference

This document serves as a comprehensive reference for application-level rules and guidelines in the AI Experiment project. It focuses on rules specific to applications in the `apps/` and `experimental-apps/` directories.

## Table of Contents

1. [Application Structure](#application-structure)
2. [Environment Variables](#environment-variables)
3. [Health Checks](#health-checks)
4. [OpenAPI Specifications](#openapi-specifications)
5. [Security and Authentication](#security-and-authentication)
6. [Application-Specific Rules](#application-specific-rules)
   - [Basic Calculator](#basic-calculator)
   - [Advanced Calculator](#advanced-calculator)
   - [AI App](#ai-app)

## Application Structure

Each application in the AI Experiment project consists of two Maven modules:

1. **Core App Module (JAR)**
   - Contains the application code and JPMS module
   - Packaging: `jar`
   - Contains: `src/main/java`, `src/test/java`, `module-info.java`
   - Named: `basic-calculator`, `advanced-calculator`, etc.

2. **JLink Runtime Module**
   - Depends on the core app module
   - Packaging: `jlink`
   - No source code, only `pom.xml`
   - Named: `basic-calculator-runtime`, `advanced-calculator-runtime`
   - Responsible for generating the runtime image

Each application has:
- Core module with a `pom.xml` file with JPMS configuration
- Runtime module with a `pom.xml` file with JLink plugin setup
- Separate GitHub Actions workflows for building the core module and the runtime module
- A Dockerfile for containerization (in the runtime module)
- A cloudbuild.yaml file for Cloud Build integration (in the runtime module)

## Environment Variables

Applications must follow these environment variable rules:

- Must use shared runtime env vars as defined in `env-variables.md`
- Must support default `.env`, `.env.dev`, `.env.qe`, `.env.prod` where applicable
- No hardcoded ports, secrets, or URLs
- Follow the [12-Factor App](https://12factor.net/) methodology for configuration

Common environment variables across applications include:

- `PORT`: The port on which the application listens (default: 8080)
- `STARTUP_CPU_BOOST`: Whether to enable CPU boost during startup (default: true)
- `LOG_LEVEL`: The logging level (default: INFO)
- `AUTH_ENABLED`: Whether authentication is enabled (default: true)
- `AUTH_SERVER_URL`: The URL of the authentication server
- `AUTH_REALM`: The authentication realm (default: ai-experiment)

## Health Checks

Applications must implement health checks according to these rules:

- Must expose a `/health` endpoint that returns HTTP 200 when healthy
- Must include readiness and liveness probes
- Should include component health checks for dependencies
- Should follow the health check format defined in `health.md`

## OpenAPI Specifications

Applications must follow these OpenAPI specification rules:

- Must provide an OpenAPI specification in YAML format
- Must expose the specification at `/openapi.yaml`
- Must include proper authentication requirements
- Must follow the guidelines defined in `openapi-guidelines.md`
- Must implement the specification as defined in `openapi-implementation.md`

## Security and Authentication

Applications must follow these security and authentication rules:

- Must use OAuth2/OIDC for authentication
- Must validate JWT tokens
- Must implement proper role-based access control
- Must follow the security guidelines defined in `security.md`
- Must implement the security measures defined in `security-authentication.md`

## Application-Specific Rules

### Basic Calculator

The Basic Calculator application must follow these specific rules:

- Must implement basic arithmetic operations (addition, subtraction, multiplication, division)
- Must validate input parameters
- Must return appropriate error messages for invalid inputs
- Must log all operations
- Must implement the OpenAPI specification defined in `apps/basic-calculator/openapi.md`

### Advanced Calculator

The Advanced Calculator application must follow these specific rules:

- Must implement all Basic Calculator operations
- Must implement advanced operations (square root, power, etc.)
- Must support currency conversion as defined in `apps/advanced-calculator/currency-enhancement.md`
- Must implement the OpenAPI specification defined in `apps/advanced-calculator/openapi.md`

### AI App

The AI App application must follow these specific rules:

- Must implement authentication as defined in `experimental-apps/ai-app/experimental-app-backend-auth.md`
- Must implement the frontend shell as defined in `experimental-apps/ai-app/experimental-app-frontend-shell.md`
- Must implement the backend as defined in `experimental-apps/ai-app/experimental-app-implementation.md`
- Must implement the OpenAPI specification defined in `experimental-apps/ai-app/openapi.md`