# 🩺 Health & Observability for ExperimentalAIApp (Backend Shell)

## 🎯 Objective

Provide foundational observability for the shell backend application of ExperimentalAIApp, enabling full GCP-native health and monitoring via Vert.x.

This app is the top-level router/auth gateway for micro frontends and backends.

---

## 📍 Endpoints

| Path          | Purpose                      | Auth | Format          |
| ------------- | ---------------------------- | ---- | --------------- |
| `/health`     | Aggregated system status     | ❌    | JSON (detailed) |
| `/metrics`    | Prometheus metrics           | ❌    | Prometheus      |
| `/info`       | Module metadata & version    | ❌    | JSON            |
| `/prometheus` | Explicit Prometheus endpoint | ❌    | Prometheus      |

These are public routes and must be allowed unauthenticated through Cloud Run and Cloud Armor.

---

## ♻️ Aggregated Health Checks

This module acts as a dependency hub. Recommended health indicators:

```java
healthHandler.register("keycloak", promise ->
  webClient.get(443, "auth.example.com", "/realms/experimental-ai/.well-known/openid-configuration")
    .ssl(true)
    .send(ar -> {
      if (ar.succeeded()) promise.complete(Status.OK());
      else promise.fail("Keycloak not reachable");
    })
);

healthHandler.register("env-ready", promise ->
  if (System.getenv("ENVIRONMENT") != null) promise.complete(Status.OK());
  else promise.fail("ENV not set")
);
```

---

## 🔭 Tracing

Use shared OpenTelemetry setup, with `service.name=experimental-ai-app`.

```java
OpenTelemetryOptions telemetry = new OpenTelemetryOptions()
  .setServiceName("experimental-ai-app")
  .setVersion("1.0.0");

VertxOptions options = new VertxOptions().setTracingOptions(telemetry);
```

All inbound/outbound requests (incl. EventBus proxy) will carry trace metadata.

---

## 📊 Metrics

Prometheus + Micrometer configuration mirrors other modules:

```java
MicrometerMetricsOptions metrics = new MicrometerMetricsOptions()
  .setEnabled(true)
  .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true));

VertxOptions options = new VertxOptions().setMetricsOptions(metrics);
```

---

## 💡 Extra Info Route

```java
router.get("/info").handler(ctx -> {
  JsonObject info = new JsonObject()
    .put("module", "experimental-ai-app")
    .put("version", "1.0.0")
    .put("git", System.getenv("GIT_COMMIT"));
  ctx.json(info);
});
```

---

## ✅ Cloud Integration

* Terraform exposes the observability endpoints as `public_noauth_paths`
* GCP Prometheus scrapes `/metrics`
* Traces appear under GCP Cloud Trace with full propagation
* Health surfaces to uptime checks and alerting dashboards

---

This backend shell forms the heartbeat of the ExperimentalAIApp 💓
