# 📘 OpenAPI Integration for Basic Calculator Module

## 🎯 Purpose

Enable contract-first routing and validation in the `apps/basic-calculator` backend module using Vert.x 5 OpenAPI tooling. Expose documentation via Swagger UI, auto-validate input/output against the spec, and align response types using shared component contracts.

---

## 📁 File Structure

```
apps/
  basic-calculator/
    src/main/resources/openapi/
      basic-calculator.yaml
      components.yaml
    src/main/java/... (Verticle implementation)
```

---

## 🔧 Initialization

```java
OpenAPIContract.from(vertx, "openapi/basic-calculator.yaml", Map.of(
  "https://expai.com/shared/components", "openapi/components.yaml")
).onSuccess(contract -> {
  RouterBuilder.create(vertx, contract).onSuccess(routerBuilder -> {

    // 🔐 Auth Binding (JWT)
    routerBuilder.security("http_bearer")
      .httpHandler(JWTAuthHandler.create(jwtProvider));

    // 🔀 Router Instance
    Router router = routerBuilder.createRouter();

    // 🧭 Swagger UI
    router.get("/openapi.yaml").handler(ctx ->
      ctx.response().putHeader("Content-Type", "application/yaml")
        .sendFile("openapi/basic-calculator.yaml"));

    router.route("/docs/*").handler(StaticHandler.create("swagger-ui"));

    vertx.createHttpServer().requestHandler(router).listen(8081);
  });
});
```

---

## 🔄 Supported Endpoints

Mapped in the OpenAPI YAML under `paths:`

```yaml
/calculate:
  post:
    summary: Perform calculation
    operationId: calculate
    requestBody:
      required: true
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/CalculationRequest'
    responses:
      '200':
        description: Result
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/CalculationResult'
      '400':
        $ref: 'https://expai.com/shared/components#/components/responses/BadRequest'
```

---

## ✅ Validation Layer

* Input auto-validated before handler via RouterBuilder
* Output validated using ResponseValidator
* `204` returned for `void` operations

---

## 📚 Documentation Access

* `/openapi.yaml` serves the OpenAPI YAML definition
* `/docs/` hosts the Swagger UI frontend

---

## 🧪 Dev Support

* All requests mocked via OpenAPI contract
* Swagger UI auto-detects schema changes in hot reload

---

## 📦 Output

* Fully OAS 3.1-conformant endpoint routing
* Dynamic OpenAPI validation
* Centralized response types and errors via `$ref`
* Validated JWT-based security integration

---

Contract-first, secure, documented API module ✅
