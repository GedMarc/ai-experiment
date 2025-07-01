# 📘 The AI Experiment – Pact

This document defines the architectural pact for "The AI Experiment" — a modular, cloud-native, event-driven system driven by multi-agent AI collaboration.

## Vision

A reference-grade, language-agnostic architecture combining:

* Cloud-native microservices and micro frontends
* Domain-driven design (DDD)
* Event-driven architecture (EDA)
* Full automation of infrastructure, code generation, and CI/CD pipelines
* Documentation-first, rule-driven AI-assisted implementation

## Pact Chain

* **Rules**: Define platform-level boundaries, contracts, versions, and principles.
* **Guides**: Contextual walkthroughs and module blueprints including use cases, diagrams, and data flow.
* **Implementation**: Language- and runtime-specific details with full implementation strategy.

## Execution Agents

* **ChatGPT**: Definitions, documentation, structural reasoning.
* **JetBrains Junie**: IDE-native project and file creation.
* **Claude v4**: Code generation and refinement.

## Stack Summary

* GCP-native (Cloud Run, Cloud Build, Artifact Registry, private VPC)
* Java 24+ with JPMS, Vert.x 5, Hibernate Reactive, PostgreSQL 17
* Angular 20 with W3C Web Components
* Terraform infrastructure, GitHub Actions CI/CD, SonarQube, and test-first development