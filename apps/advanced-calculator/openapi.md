# 📘 OpenAPI Integration for Advanced Calculator Module

## 🎯 Purpose

Apply contract-first API design in the `apps/advanced-calculator` backend module using Vert.x 5’s OpenAPI capabilities. This enables dynamic router generation, input/output validation, Swagger UI documentation, and role-based security integration.

---

## 📁 File Structure

```
apps/
  advanced-calculator/
    src/main/resources/openapi/
      advanced-calculator.yaml
      components.yaml
    src/main/java/... (Verticle implementation)
```

---

## 🔧 Initialization

```java
OpenAPIContract.from(vertx, "openapi/advanced-calculator.yaml", Map.of(
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
        .sendFile("openapi/advanced-calculator.yaml"));

    router.route("/docs/*").handler(StaticHandler.create("swagger-ui"));

    vertx.createHttpServer().requestHandler(router).listen(8082);
  });
});
```

---

## 🔄 Supported Endpoints

Will vary by feature mode:

```yaml
/calculate/temperature
/calculate/currency
/calculate/scientific
/calculate/weight
```

Each path should define:

* Request body schema
* Typed response schema
* Role-based access restrictions where applicable

---

## ✅ Validation

* Full input and output validation via Vert.x RouterBuilder
* Shared schemas reused from components.yaml
* Default responses:

  * `200 OK`: content
  * `204 No Content`: void
  * `400+`: via shared error definitions

---

## 📚 Documentation Access

* `/openapi.yaml` – Static OpenAPI YAML
* `/docs/` – Swagger UI frontend with interactive playground

---

## 🔐 Security

* Token-auth via JWT
* Endpoint tagging by role for authorization middleware (e.g. `calculator-user`, `scientific-user`, `admin`)

---

## 🧪 Dev Notes

* All examples generated and served from schema
* Contract version pinned to API version of module
* Easily mockable during CI using schema

---

## 📦 Output

* Role-enforced, fully validated REST endpoints
* Contract-first with OAS 3.1 compliance
* Swagger UI included for interactive access
* Reusable component references for errors and data

Advanced, validated, and production ready ✅
