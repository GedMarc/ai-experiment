# 📘 OpenAPI Integration for ExperimentalAIApp (Collective Frontend Shell)

## 🎯 Purpose

Define how the collective shell of `ExperimentalAIApp` exposes and integrates all aggregated OpenAPI documentation across its micro frontends and backend modules, offering unified discovery and documentation access.

---

## 📁 Structure

```
apps/
  experimental-ai-app/
    src/main/resources/openapi/
      shell.yaml                # Optional collective contract
    src/main/java/...          # Gateway router & docs handler
```

---

## 🧭 Gateway Router

Serves all contracts at logical paths and provides aggregation for public access.

```java
Router router = Router.router(vertx);

// Static Swagger UI
router.route("/docs/*").handler(StaticHandler.create("swagger-ui"));

// Dynamic openapi.yaml forwarding from known services
router.get("/docs/basic-calculator/openapi.yaml").handler(ctx ->
  ctx.response().sendFile("openapi/basic-calculator.yaml"));

router.get("/docs/advanced-calculator/openapi.yaml").handler(ctx ->
  ctx.response().sendFile("openapi/advanced-calculator.yaml"));

// Optional: Proxy from deployed modules if running independently
```

---

## 🗂 Swagger UI Directory Index

Customize the Swagger UI landing page to offer quick access to all exposed module contracts:

```html
<ul>
  <li><a href="/docs/basic-calculator/openapi.yaml">Basic Calculator</a></li>
  <li><a href="/docs/advanced-calculator/openapi.yaml">Advanced Calculator</a></li>
</ul>
```

Can be embedded inside the Swagger UI index or hosted as a simple HTML index alongside the Swagger bundle.

---

## 📜 Optional Shell Contract (shell.yaml)

You can define an umbrella `shell.yaml` OpenAPI contract to summarize available services, but without redefining their paths:

```yaml
openapi: 3.1.0
info:
  title: Experimental AI App Gateway
  version: 1.0.0
paths:
  /docs/basic-calculator/openapi.yaml:
    get:
      summary: Link to Basic Calculator API
  /docs/advanced-calculator/openapi.yaml:
    get:
      summary: Link to Advanced Calculator API
```

This can be served at `/openapi.yaml` and loaded into Swagger UI.

---

## 🔐 Security

This shell does **not** enforce JWT validation — it delegates to the mounted services themselves. All routes are for documentation discovery.

If desired, restrict `/docs` via gateway policies (e.g., IP or role-based access) using standard Vert.x middleware.

---

## 🔄 Module Awareness

The Swagger landing page and mounted routes should only list modules that are:

* Included in the final build
* Active at runtime (if dynamic routing is used)

This enables flexible micro frontend packaging per environment.

---

## 📦 Output

* `/docs/` offers interactive Swagger UI for all services
* `/docs/<module>/openapi.yaml` for static specs
* Optional `/openapi.yaml` as umbrella spec

Public, dynamic, and micro frontend aware ✅
