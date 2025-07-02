# 📓 OpenAPI Authoring Guidelines for ExperimentalAIApp

## Purpose

To standardize and enforce consistent contract-first API development across all backend modules and microservices within the ExperimentalAIApp platform, using OpenAPI 3.x and Vert.x 5+.

---

## Supported Specification

* OpenAPI Version: `3.0.3` or `3.1.0`
* All contracts must be YAML format and validated via tooling

---

## File Structure

* OpenAPI spec: `src/main/resources/openapi/<module>.yaml`
* Shared schemas: `src/main/resources/openapi/components.yaml`
* Swagger UI: mounted at `/docs`

---

## Security Requirements

* All endpoints must define at least one `security` rule (globally or per-operation)
* Supported schemes:

  * `http_bearer` → uses `JWTAuthHandler`
  * `openIdConnect` → uses `OAuth2AuthHandler` with Keycloak
* Example:

  ```yaml
  securitySchemes:
    openIdConnect:
      type: openIdConnect
      openIdConnectUrl: https://auth.example.com/realms/experimental-ai/.well-known/openid-configuration
  ```

---

## Validation Rules

### Request Validation

* Query, header, path, and body must align with schema definitions
* Must be testable using Vert.x `RequestValidator`

### Response Validation

* All `200`, `201`, `204`, `4xx`, and `5xx` responses must:

  * Declare schema under `responses`
  * Use `$ref` to shared `DefaultError` or specific schema

### Shared Schema

* All error responses must use `DefaultError` component

  ```yaml
  components:
    schemas:
      DefaultError:
        type: object
        properties:
          code: { type: string }
          message: { type: string }
          traceId: { type: string }
  ```

---

## Vendor Extensions

Use `x-` fields for platform metadata:

| Extension   | Purpose                         |
| ----------- | ------------------------------- |
| `x-slot`    | MFE layout region               |
| `x-module`  | Logical service owner           |
| `x-feature` | Optional UI toggle for endpoint |

---

## Route Metadata Standards

Each route must define:

* `operationId`
* `tags` (mapped to domain/module)
* `summary` (human readable)
* `responses` (with schema references)

---

## Testing Requirements

* Unit tests must verify:

  * Contract can be parsed
  * Invalid input yields structured error
* Integration tests must mock contract via Vert.x router
* Tools allowed:

  * Swagger Validator
  * Postman / Insomnia
  * REST Assured

---

## Deployment Output

* `/openapi.yaml` → static contract
* `/openapi.json` (optional)
* `/docs` → Swagger UI mount

---

## Compliance Checklist

* [ ] OpenAPI spec is valid YAML under supported version
* [ ] Security requirements declared
* [ ] All responses mapped to known schemas
* [ ] Request/response validation enforced
* [ ] `/openapi.yaml` is served statically

---

🧪 **Contract-first. Validator-enforced. Developer-friendly.**
