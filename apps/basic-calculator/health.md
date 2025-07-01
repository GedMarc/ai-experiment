# 🩺 Health & Observability for Basic Calculator

## 🎯 Objective

Enable full diagnostics visibility for the `basic-calculator` backend using Vert.x Health Checks, OpenTelemetry tracing, and Prometheus metrics endpoints—all wired for native GCP ingestion.

---

## 📍 Endpoints

| Path          | Purpose                      | Auth | Format          |
| ------------- | ---------------------------- | ---- | --------------- |
| `/health`     | Liveness + readiness probe   | ❌    | JSON (detailed) |
| `/metrics`    | Prometheus metrics           | ❌    | Prometheus      |
| `/info`       | Module metadata              | ❌    | JSON            |
| `/prometheus` | Explicit Prometheus endpoint | ❌    | Prometheus      |

> All endpoints are **excluded from auth** and publicly accessible on Cloud Run.

---

## ♻️ Health Details

```java
HealthCheckHandler healthHandler = HealthCheckHandler.create(vertx);

healthHandler.register("postgres", promise ->
  postgresClient.getConnection().onComplete(res -> {
    if (res.succeeded()) promise.complete(Status.OK());
    else promise.fail("DB not available");
  })
);
```

Use named health indicators for each system dependency. This feeds directly into GCP Cloud Monitoring.

---

## 🔭 Tracing Setup

```java
OpenTelemetryOptions telemetryOptions = new OpenTelemetryOptions()
  .setServiceName("basic-calculator")
  .setVersion("1.0.0")
  .setResourceAttributes(Map.of("env", System.getenv("ENVIRONMENT")));

vertx = Vertx.vertx(new VertxOptions().setTracingOptions(telemetryOptions));
```

Traces propagate automatically through HTTP routes and EventBus messages.

---

## 📊 Metrics Setup

```java
MicrometerMetricsOptions metricsOptions = new MicrometerMetricsOptions()
  .setEnabled(true)
  .setPrometheusOptions(new VertxPrometheusOptions()
    .setEnabled(true)
    .setStartEmbeddedServer(false));

VertxOptions options = new VertxOptions().setMetricsOptions(metricsOptions);
vertx = Vertx.vertx(options);
```

Use shared config so these values can be overridden via environment variables.

---

## 🧪 Dev Setup

Local run profile:

```
ENVIRONMENT=dev
VERTX_METRICS_ENABLED=true
JDK_JAVA_OPTIONS="-Dotel.resource.attributes=env=dev -Dotel.service.name=basic-calculator"
```

Dockerfile must expose `/metrics`, `/health`, `/info` via lightweight HTTP server.

---
