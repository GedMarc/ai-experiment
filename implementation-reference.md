# 🔧 AI Experiment - Implementation Reference

This document serves as a comprehensive reference for implementation details in the AI Experiment project. It focuses on the practical implementation aspects as defined in the `implementation/` directory.

## Table of Contents

1. [CI/CD Implementation](#ci-cd-implementation)
2. [Database Implementation](#database-implementation)
3. [Health Monitoring](#health-monitoring)
4. [Metrics Collection](#metrics-collection)
5. [Tracing Implementation](#tracing-implementation)
6. [Security Implementation](#security-implementation)
7. [Orchestration](#orchestration)
8. [Service Discovery](#service-discovery)
9. [Terraform Implementation](#terraform-implementation)
   - [GCP Structure](#gcp-structure)
   - [Deployment Rules](#deployment-rules)

## CI/CD Implementation

The AI Experiment project implements CI/CD using a combination of GitHub Actions and GCP Cloud Build:

- **GitHub Actions** are used for:
  - Building and testing library modules
  - Publishing library modules to GitHub Packages
  - Building and testing application core modules
  - Running unit tests and integration tests

- **Cloud Build** is used for:
  - Building container images from JLink runtime modules
  - Deploying applications to Cloud Run
  - Running end-to-end tests in the cloud environment

The CI/CD pipeline follows these principles:
- Trunk-based development with feature branches
- Automated testing at multiple levels (unit, integration, e2e)
- Automated deployment to dev, qe, and prod environments
- Artifact promotion through environments (not rebuilding)

## Database Implementation

The AI Experiment project uses PostgreSQL 17 as its primary database, with the following implementation details:

- **Connection Management**:
  - Uses Hibernate Reactive for non-blocking database access
  - Connection pooling with HikariCP
  - Configurable connection parameters via environment variables

- **Schema Management**:
  - Flyway for database migrations
  - Versioned migration scripts
  - Separate schemas for different applications

- **Transaction Management**:
  - Reactive transaction management
  - Proper error handling and rollback
  - Idempotent operations where possible

## Health Monitoring

Health monitoring in the AI Experiment project is implemented as follows:

- **Health Endpoints**:
  - `/health` endpoint for overall application health
  - `/health/live` endpoint for liveness probes
  - `/health/ready` endpoint for readiness probes

- **Health Checks**:
  - Database connectivity checks
  - External service dependency checks
  - Internal component checks

- **Health Response Format**:
  ```json
  {
    "status": "UP",
    "checks": [
      {
        "name": "database",
        "status": "UP"
      },
      {
        "name": "auth-service",
        "status": "UP"
      }
    ]
  }
  ```

## Metrics Collection

Metrics collection in the AI Experiment project is implemented as follows:

- **Metrics Endpoints**:
  - `/metrics` endpoint exposing Prometheus-compatible metrics

- **Collected Metrics**:
  - JVM metrics (memory, GC, threads)
  - Application-specific metrics (request counts, response times)
  - Business metrics (operation counts, error rates)

- **Metric Naming Convention**:
  - `ai_experiment_<component>_<metric_name>`
  - Example: `ai_experiment_calculator_operation_count`

## Tracing Implementation

Distributed tracing in the AI Experiment project is implemented as follows:

- **Tracing Framework**:
  - OpenTelemetry for instrumentation
  - W3C Trace Context for propagation

- **Trace Context Propagation**:
  - HTTP headers for service-to-service communication
  - CloudEvents extensions for event-based communication

- **Span Naming Convention**:
  - `<service>.<operation>`
  - Example: `calculator.add`

## Security Implementation

Security in the AI Experiment project is implemented as follows:

- **Authentication**:
  - OAuth2/OIDC with Keycloak as the identity provider
  - JWT token validation
  - Role-based access control

- **Authorization**:
  - Fine-grained permissions based on roles
  - Resource-level access control
  - Method-level security annotations

- **Secure Communication**:
  - TLS for all external communication
  - Service-to-service authentication
  - Secure token handling

## Orchestration

Service orchestration in the AI Experiment project is implemented as follows:

- **Service Coordination**:
  - Event-driven architecture using CloudEvents
  - Vert.x EventBus for internal communication
  - HTTP for external communication

- **Workflow Management**:
  - Saga pattern for distributed transactions
  - Compensation actions for rollback
  - Idempotent operations

## Service Discovery

Service discovery in the AI Experiment project is implemented using Vert.x Service Discovery:

- **Service Registration**:
  - Automatic registration of services on startup
  - Health-based service availability

- **Service Lookup**:
  - Name-based service lookup
  - Type-based service lookup
  - Metadata-based service filtering

- **Service Types**:
  - HTTP endpoint
  - EventBus service
  - JDBC data source
  - Redis data source

## Terraform Implementation

### GCP Structure

The GCP infrastructure for the AI Experiment project is structured as follows:

- **Project Organization**:
  - Single GCP project: `za-ai-experiment`
  - Regional deployment: `europe-west1`
  - VPC network for service isolation

- **Service Structure**:
  - Cloud Run for application hosting
  - Cloud SQL for PostgreSQL databases
  - Artifact Registry for container images
  - Secret Manager for secrets
  - Cloud Storage for static assets

### Deployment Rules

Terraform deployment in the AI Experiment project follows these rules:

- **Module Structure**:
  - Reusable modules for common infrastructure components
  - Environment-specific configurations

- **State Management**:
  - Remote state stored in Cloud Storage
  - State locking with Cloud Storage

- **Deployment Process**:
  - Terraform plan reviewed before apply
  - Automated apply in CI/CD pipeline
  - Separate workspaces for dev, qe, and prod

- **Security Considerations**:
  - Least privilege service accounts
  - No secrets in Terraform code
  - Secret references from Secret Manager