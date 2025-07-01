# 📘 OpenAPI Implementation for ExperimentalAIApp (Vert.x 5)

## 🎯 Purpose

This implementation defines how the OpenAPI contract is loaded, validated, and integrated with Vert.x 5 using the new `vertx-openapi` and `vertx-web-openapi-router` modules.

---

## 📦 Dependencies

```xml
<dependency>
  <groupId>io.vertx</groupId>
  <artifactId>vertx-openapi</artifactId>
  <version>5.0.1</version>
</dependency>
<dependency>
  <groupId>io.vertx</groupId>
  <artifactId>vertx-web-openapi-router</artifactId>
  <version>5.0.1</version>
</dependency>
<dependency>
  <groupId>io.swagger.core.v3</groupId>
  <artifactId>swagger-ui</artifactId>
  <version>4.15.5</version>
</dependency>
```

---

## 📁 Contract Structure

* Contracts are stored under: `resources/openapi/<module>.yaml`
* Shared components defined under: `resources/openapi/components.yaml`

---

## 🚦 Initialization

```java
String pathToContract = "openapi/basic-calculator.yaml";
Map<String, String> references = Map.of(
  "https://expai.com/shared/components", "openapi/components.yaml"
);

OpenAPIContract.from(vertx, pathToContract, references).onSuccess(contract -> {
  RouterBuilder.create(vertx, contract).onSuccess(routerBuilder -> {

    routerBuilder.security("http_bearer")
      .httpHandler(JWTAuthHandler.create(jwtProvider));

    Router router = routerBuilder.createRouter();

    // Serve OpenAPI YAML
    router.get("/openapi.yaml").handler(ctx -> ctx.response()
      .putHeader("Content-Type", "application/yaml")
      .sendFile("openapi/basic-calculator.yaml"));

    // Swagger UI setup
    router.route("/docs/*").handler(StaticHandler.create("swagger-ui"));

    vertx.createHttpServer().requestHandler(router).listen(8080);
  });
});
```

---

## 🔐 Security

### OpenID Connect / OAuth2

```java
routerBuilder.security("openIdConnect")
  .openIdConnectHandler("/callback", discoveryUrl -> {
    return OpenIDConnectAuth.discover(vertx, new OAuth2Options()
      .setClientId("frontend-shell")
      .setClientSecret("secret"))
      .map(openIdConnect -> OAuth2AuthHandler.create(vertx, openIdConnect, "https://frontend.app/callback"));
  });
```

---

## ✅ Validation

### Requests

```java
RequestValidator validator = RequestValidator.create(vertx, contract);
validator.validate(httpServerRequest, "operationId").onSuccess(validatedRequest -> {
  JsonObject body = validatedRequest.getBody();
  // Handle request
});
```

### Responses

```java
ResponseValidator responseValidator = ResponseValidator.create(vertx, contract);
ValidatableResponse response = ValidatableResponse.create(200, resultBuffer, APPLICATION_JSON);
responseValidator.validate(response, "operationId")
  .onSuccess(validated -> validated.send(httpServerRequest.response()));
```

---

## 📂 Shared Behavior

### Default Error Types

Defined in `components.yaml`, referenced via `$ref`. Errors auto-bound to 400, 403, 404, 500.

### Default Responses

* `204`: Used when no content
* `200`: Used for successful responses with content
* `201`: For successful resource creation

### Route Metadata

```yaml
get:
  summary: "List all entries"
  operationId: "listEntries"
  tags: [Calculator]
  responses:
    '200':
      description: "Successful list"
```

---

## 🔍 Benefits

* Validated input/output based on spec
* Complete router generation via OpenAPI
* Fully spec-compliant OAS 3.1 definitions
* Integrated security from spec to router
* Default response behavior via shared component definitions
* 🧭 **Swagger UI documentation available at `/docs`**

---

## 📤 Output

Each backend exposes OpenAPI at:

```
/openapi.yaml  (static contract)
/openapi.json  (optional generated)
```

And optionally mounts Swagger UI at `/docs`

---

Ready for consistent and validated contract-first design ✅
