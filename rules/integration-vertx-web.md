# 📗 Rules: Vert.x Web EventBus Integration

## Purpose

Defines communication rules between frontend (micro frontends) and backend (Vert.x-based services) using Vert.x Web EventBus. This includes configuration for SockJS bridging, JSON messaging formats, secure access patterns, and service resolution.

---

## 💬 Core Protocol

* All frontend modules communicate with backend modules via EventBus JSON messages.
* A standard message format is enforced:

  ```json
  {
    "a": 10,
    "b": 20,
    "op": "add"
  }
  ```
* All responses must be wrapped in:

  ```json
  {
    "result": 30
  }
  ```
* Error messages must respond with a structured envelope:

  ```json
  {
    "error": "Invalid operation"
  }
  ```

---

## 🔌 Backend Bridge Setup

### Enable EventBus Bridge

Use `SockJSHandler` in `MainVerticle`:

```java
Router router = Router.router(vertx);
SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
BridgeOptions options = new BridgeOptions()
  .addInboundPermitted(new PermittedOptions().setAddressRegex("expai\\..+"))
  .addOutboundPermitted(new PermittedOptions().setAddressRegex("expai\\..+"));
sockJSHandler.bridge(options);
router.route("/eventbus/*").handler(sockJSHandler);
```

### CORS and Web Allow List

```java
router.route().handler(CorsHandler.create("http://localhost:4200")
  .allowCredentials(true)
  .allowedHeader("Content-Type")
  .allowedMethod(HttpMethod.GET)
  .allowedMethod(HttpMethod.POST));
```

### Deployment Server

```java
vertx.createHttpServer().requestHandler(router).listen(8080);
```

---

## 🌐 Frontend JS Client Setup

Include EventBus client:

```html
<script src="/eventbus/eventbus.js"></script>
```

Then connect:

```ts
const eb = new EventBus('/eventbus');
eb.onopen = () => {
  eb.send('expai.calculator.compute', { a: 10, b: 20, op: 'add' }, res => {
    if (res.body.result !== undefined) {
      console.log('Result:', res.body.result);
    }
  });
};
```

---

## 📦 Integration Guidelines

* Address format: expai.{module}.{operation} (e.g., expai.calculator.compute). Multiple calculators (e.g., basic and advanced) can coexist using address namespaces like expai.advanced-calc.*
* Frontend must detect connection state and retry logic
* Backend services must return valid JSON and fail gracefully
* Health check endpoints must be served separately and not require EventBus availability

---

## 🔐 Security & Isolation

* All bridges must be scoped by module address namespace.
* Optional: Secure bridge using JWT and token headers (TBD in later extension)
* Frontends may only publish/receive on their permitted addresses

---

## 🧪 Testing

* Frontend JS/Angular eventbus integration should be tested via unit mocks and e2e integration
* Backend bridges tested with Vert.x Web clients and simulated SockJS clients

---