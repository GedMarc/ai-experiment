# 📒 Implementation: Basic Calculator Backend (basic-calculator-service)

## Module Overview

Implements the Vert.x-based microservice for arithmetic operations within `ExperimentalAIApp`.

---

## 📦 Module Info

```java
module expai.basic.calculator {
  requires io.vertx.core;
  requires io.vertx.healthchecks;
  requires io.vertx.json;
  requires io.vertx.codegen.annotations;

  exports za.co.experimentalai.calculator;
  uses za.co.experimentalai.calculator.ComputeHandler;
  provides za.co.experimentalai.calculator.ComputeHandler with za.co.experimentalai.calculator.impl.BasicArithmeticHandler;
}
```

---

## 🧩 Package Structure

```
apps/ExperimentalAIApp/basic-calculator-service/
└── src/main/java/
    └── za/co/experimentalai/calculator/
        ├── MainVerticle.java
        ├── ComputeHandler.java
        └── impl/
            └── BasicArithmeticHandler.java
```

---

## 🎯 ComputeHandler Interface

```java
public interface ComputeHandler {
  Future<JsonObject> compute(JsonObject request);
}
```

---

## 🔢 BasicArithmeticHandler Implementation

```java
public class BasicArithmeticHandler implements ComputeHandler {
  public Future<JsonObject> compute(JsonObject request) {
    double a = request.getDouble("a");
    double b = request.getDouble("b");
    String op = request.getString("op");
    double result;

    switch (op) {
      case "add": result = a + b; break;
      case "subtract": result = a - b; break;
      case "multiply": result = a * b; break;
      case "divide": result = b != 0 ? a / b : Double.NaN; break;
      default: return Future.failedFuture("Unknown operation: " + op);
    }

    return Future.succeededFuture(new JsonObject().put("result", result));
  }
}
```

---

## 🚀 MainVerticle

```java
public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {
    ComputeHandler handler = ServiceLoader.load(ComputeHandler.class).findFirst()
      .orElseThrow(() -> new IllegalStateException("No handler found"));

    vertx.eventBus().<JsonObject>consumer("expai.calculator.compute", message -> {
      handler.compute(message.body()).onSuccess(result -> message.reply(result))
        .onFailure(err -> message.fail(500, err.getMessage()));
    });

    // Health check
    HealthCheckHandler hc = HealthCheckHandler.create(vertx);
    hc.register("eventbus", future -> future.complete(Status.OK()));
    vertx.createHttpServer().requestHandler(hc).listen(8081);

    startPromise.complete();
  }
}
```

---

## 🧪 Testing

* Use JUnit 5
* Wrap `MainVerticle` inside `VertxExtension`
* Test JSON contract handling and error paths
* Use Testcontainers for integration with Postgres (if needed)

---

## 🚀 Deployment

* Built as part of multi-module Maven project
* Exposed on Cloud Run via internal networking
* EventBus TCP bridge configured for outbound usage from BFF relay

---
