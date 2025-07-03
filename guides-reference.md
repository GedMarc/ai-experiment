# 📚 AI Experiment - Guides Reference

This document serves as a comprehensive reference for guides and walkthroughs in the AI Experiment project. It focuses on contextual walkthroughs and module blueprints as defined in the `guides/` directory.

## Table of Contents

1. [Integration Guides](#integration-guides)
   - [General Integration Guide](#general-integration-guide)
   - [TCP EventBus Bridge Integration](#tcp-eventbus-bridge-integration)
2. [Upgrade Guides](#upgrade-guides)
   - [Hibernate Upgrade Guide](#hibernate-upgrade-guide)
   - [Vert.x Upgrade Guide](#vertx-upgrade-guide)
3. [Authentication Guides](#authentication-guides)
   - [OAuth2 Flow Guide](#oauth2-flow-guide)
4. [Database Guides](#database-guides)
   - [Postgres Client Guide](#postgres-client-guide)
5. [Application Guides](#application-guides)
   - [Basic Calculator Guide](#basic-calculator-guide)
   - [Advanced Calculator Guide](#advanced-calculator-guide)
   - [AI App Guide](#ai-app-guide)

## Integration Guides

### General Integration Guide

This guide provides a comprehensive overview of integrating services within the AI Experiment platform:

- **Service-to-Service Communication**:
  - HTTP-based integration using RESTful APIs
  - Event-based integration using CloudEvents
  - Synchronous vs. asynchronous communication patterns

- **Authentication for Integration**:
  - Service account authentication
  - JWT token propagation
  - Securing service-to-service communication

- **Error Handling**:
  - Standard error response format
  - Retry strategies
  - Circuit breaking patterns

- **Observability**:
  - Distributed tracing across service boundaries
  - Correlation ID propagation
  - Logging standards for integration points

### TCP EventBus Bridge Integration

This guide explains how to use the Vert.x TCP EventBus Bridge for integration:

- **EventBus Bridge Setup**:
  - Configuration of TCP EventBus Bridge
  - Security considerations
  - Client connection management

- **Message Formats**:
  - CloudEvents format for messages
  - Message addressing
  - Message headers and metadata

- **Client Integration**:
  - Java client integration
  - JavaScript client integration
  - Other language clients

- **Scaling and Reliability**:
  - Clustering considerations
  - High availability setup
  - Handling network partitions

## Upgrade Guides

### Hibernate Upgrade Guide

This guide provides instructions for upgrading Hibernate in AI Experiment applications:

- **Upgrade Path**:
  - Current version to target version
  - Intermediate steps if necessary
  - Compatibility considerations

- **API Changes**:
  - Deprecated APIs and their replacements
  - New features to leverage
  - Breaking changes to address

- **Configuration Updates**:
  - Property changes
  - XML configuration updates
  - Java configuration updates

- **Testing Strategy**:
  - Unit testing changes
  - Integration testing approach
  - Performance testing considerations

### Vert.x Upgrade Guide

This guide provides instructions for upgrading Vert.x in AI Experiment applications:

- **Upgrade Path**:
  - Vert.x 4.x to Vert.x 5.x
  - Module-by-module upgrade approach
  - Compatibility considerations

- **API Changes**:
  - Deprecated APIs and their replacements
  - New reactive patterns
  - Breaking changes to address

- **Configuration Updates**:
  - Deployment options changes
  - Cluster manager configuration
  - Metrics and tracing configuration

- **Testing Strategy**:
  - Unit testing with Vert.x Test
  - Integration testing approach
  - Performance testing considerations

## Authentication Guides

### OAuth2 Flow Guide

This guide explains the OAuth2 authentication flow used in the AI Experiment platform:

- **Authentication Flow**:
  - Authorization Code flow
  - Client Credentials flow
  - Refresh Token flow

- **Keycloak Integration**:
  - Realm configuration
  - Client registration
  - Role mapping

- **Implementation Details**:
  - Frontend authentication
  - Backend token validation
  - Token refresh strategy

- **Security Considerations**:
  - Token storage
  - CSRF protection
  - Scope limitations

## Database Guides

### Postgres Client Guide

This guide explains how to use the Postgres client in AI Experiment applications:

- **Connection Setup**:
  - Connection pool configuration
  - Environment variable configuration
  - Health check integration

- **Reactive Queries**:
  - Executing SQL queries
  - Handling results
  - Transaction management

- **Data Mapping**:
  - Entity mapping
  - DTO conversion
  - JSON handling

- **Migration Management**:
  - Flyway integration
  - Migration script organization
  - Versioning strategy

## Application Guides

### Basic Calculator Guide

This guide provides a walkthrough of the Basic Calculator application:

- **Application Overview**:
  - Purpose and functionality
  - Architecture and components
  - API endpoints

- **Implementation Details**:
  - Backend implementation
  - Frontend implementation
  - Integration points

- **Deployment**:
  - Local development setup
  - Cloud deployment
  - Environment configuration

- **Testing**:
  - Unit testing approach
  - Integration testing
  - End-to-end testing

### Advanced Calculator Guide

This guide provides a walkthrough of the Advanced Calculator application:

- **Application Overview**:
  - Extended functionality over Basic Calculator
  - Architecture and components
  - API endpoints

- **Implementation Details**:
  - Backend implementation
  - Frontend implementation
  - Integration with external services

- **Currency Enhancement**:
  - Currency conversion functionality
  - Exchange rate provider integration
  - Caching strategy

- **Deployment**:
  - Local development setup
  - Cloud deployment
  - Environment configuration

### AI App Guide

This guide provides a walkthrough of the AI App application:

- **Application Overview**:
  - Purpose and functionality
  - Architecture and components
  - API endpoints

- **Implementation Details**:
  - Backend implementation
  - Frontend shell implementation
  - Authentication integration

- **AI Integration**:
  - AI service integration
  - Model selection and configuration
  - Response handling

- **Deployment**:
  - Local development setup
  - Cloud deployment
  - Environment configuration