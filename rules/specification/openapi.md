# 📘 OpenAPI Specification Rules for ExperimentalAIApp Platform

## 🎯 Purpose

Define consistent, production-grade usage of OpenAPI specifications across all backend modules in the ExperimentalAIApp platform using Vert.x 5+ and `vertx-web-openapi-router`. This ensures strong contract-first development, automatic request/response validation, traceable errors, and clean frontend-to-backend interaction.

---

## 📂 Location & Structure

* OpenAPI contracts (`.yaml`) must reside in `src/main/resources/openapi/` per module.
* External `$ref` schemas must be bundled locally (no external URLs).
* Supported versions: OpenAPI 3.0.3 or 3.1.0

---

## 🔧 Tooling & Integration

* Vert.x 5.x `RouterBuilder` must be used to generate all HTTP routes
* Route metadata (operationId, tags, etc.) must match backend handler registration
* Static spec paths must be used (no dynamic runtime loading)
* Vert.x's `OpenAPIContract` abstraction should be used when supported

---

## 🔒 Security Definition Mapping

| OpenAPI Scheme  | Vert.x Implementation        |
| --------------- | ---------------------------- |
| `http_bearer`   | JWTAuthHandler               |
| `openIdConnect` | OAuth2AuthHandler + Keycloak |

Every OpenAPI spec must:

* Define at least one security scheme
* Specify security requirements globally or per operation
* Avoid hardcoded auth tokens in examples

```yaml
securitySchemes:
  openIdConnect:
    type: openIdConnect
    openIdConnectUrl: https://auth.example.com/realms/experimental-ai/.well-known/openid-configuration
```

---

## 🔍 Validation & Defaults

### ✅ Request Validation

* Enabled by default for all operations
* Query/path/header/body schema must match the `operation.parameters` and `requestBody`
* Handler access via:

  ```java
  ValidatedRequest validated = routingContext.get(RouterBuilder.KEY_META_DATA_VALIDATED_REQUEST);
  ```

### ✅ Response Validation

* 200 and 2xx responses must be validated before sending
* Each `operation.responses` must:

  * Use `application/json` or `text/plain` media types
  * Include schema references with `$ref` to shared models

---

## 🧰 Shared Components (Schemas, Errors)

Define in `components:` section and reuse via `$ref`.

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

All errors (4xx/5xx) must reference `DefaultError`.

### Default Error Mapping

| Status Code | Description                         |
| ----------- | ----------------------------------- |
| 400         | `DefaultError` - validation failure |
| 401         | Unauthenticated                     |
| 403         | Unauthorized                        |
| 500         | Internal error                      |

---

## 📤 Response Defaults

| Return Type | Status | Schema          |
| ----------- | ------ | --------------- |
| `void`      | 204    | no content      |
| `object`    | 200    | JSON schema     |
| `list`      | 200    | array of schema |

Use consistent `application/json` content type.

---

## 📘 Example Operation Skeleton

```yaml
paths:
  /calculate:
    post:
      operationId: calculate
      tags: [calculator]
      summary: Perform a basic math operation
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/CalculationRequest'
      responses:
        '200':
          description: Successful calculation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/CalculationResponse'
        '400':
          $ref: '#/components/responses/BadRequest'
        '500':
          $ref: '#/components/responses/InternalError'
```

---

## 🧪 Testing Requirements

* Must include unit test coverage for spec loading and router validation
* Invalid input cases should be contract-tested
* Each handler must have a mock integration test with the OpenAPI contract

---

## 📦 Output

* Every backend microservice module includes a fully valid OpenAPI spec
* Integrated via Vert.x OpenAPI router
* Aligned with frontend bindings and testable via OpenAPI tooling

---

🧬 **Contract-first. Validator-enforced. Platform-aligned.**
