# 🔐 Backend Auth Integration for ExperimentalAIApp

## 🎯 Purpose

Secure backend endpoints for the ExperimentalAIApp, located in `apps/experimental-ai-app`, using Keycloak-based JWT validation and Vert.x role-based access control. This implementation ensures that authentication and authorization are consistent across all backend modules, including dynamic EventBus handlers in submodules such as `apps/basic-calculator` and `apps/advanced-calculator`.

---

## 🛠️ Vert.x Keycloak Setup

### 🔄 JWT Auth Provider

```java
JWTAuthOptions jwtOptions = new JWTAuthOptions()
  .setJWTOptions(new JWTOptions().setIgnoreExpiration(false))
  .setPubSecKeys(List.of(new PubSecKeyOptions()
    .setAlgorithm("RS256")
    .setPublicKey("-----BEGIN PUBLIC KEY-----...")));

JWTAuth provider = JWTAuth.create(vertx, jwtOptions);
```

### 🛡️ Securing Routes

```java
router.route("/api/*").handler(JWTAuthHandler.create(provider));
```

> 🔓 Note: Cloud observability and diagnostics endpoints should remain public and unauthenticated, including:
>
> * `/health`
> * `/metrics`
> * `/info`
> * `/prometheus`
>   These routes should be explicitly excluded from secured handlers.

---

## 📣 EventBus Permissions

Define roles for each module using metadata:

```java
consumer.handler(ctx -> {
  JWTPrincipal principal = ctx.user().principal();
  if (!principal.getJsonArray("roles").contains("calculator")) {
    ctx.fail(403);
    return;
  }
  // Handle request
});
```

Consumers should be registered with `localOnly = false` to support cross-instance communication.

---

## 🗂️ Address Scoping Convention

```
expai.calculator.compute          // Basic calculator
expai.advanced-calc.*             // Advanced calculator (scoped by mode)
expai.admin.*                     // Admin-only endpoints
```

EventBus consumers must validate role presence before processing. Each backend module uses a common `TokenAuthVerticle` as a base.

---

## 👤 Sample Realm Setup (Keycloak)

* Realm: `experimental-ai`
* Clients: `frontend-shell`, `calculator-backend`, `admin-backend`
* Roles:

  * `calculator`
  * `admin`
* Users:

  * `admineestrate` → Roles: admin
  * `calcuser` → Roles: calculator

---

## 🔍 Dev vs Prod Keys

* **Dev:** Use internal dev Keycloak with embedded public keys
* **Prod:** Use OpenID Discovery endpoint and auto-refresh cached keys

```java
OAuth2AuthOptions options = new OAuth2AuthOptions()
  .setFlow(OAuth2FlowType.AUTH_CODE)
  .setClientID("frontend-shell")
  .setSite("https://auth.example.com/realms/experimental-ai");
```

---

## 🧪 Testing

* Test containers for Keycloak in unit tests
* Mock JWTs in component tests
* Contract validation per role and module endpoint

---

## 📦 Output

Every secured backend module supports:

* JWT token validation via Keycloak
* Role-based restriction on API and EventBus endpoints
* Configurable addresses using EventBus patterns
* Shared security bootstrapping for consistent cross-module auth
* Explicit pass-through for observability endpoints to remain publicly accessible

---

End-to-end trust from frontend session to secure backend logic ✅
