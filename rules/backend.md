# Backend Rules

Defines rules for backend services:

* Must be Vert.x 5 modular JPMS-compliant.
* Use Mutiny for reactive patterns.
* Service-loading for downward DI, registered in `module-info.java`.
* One package name per module (no overlap between test and main).
* Support local Docker Compose and Cloud Run launch.
* Use `Vertx.builder()` to compose the runtime.
* Avoid use of `deployVerticle` directly from `main`; use `Launcher` pattern or `MainVerticle`.
* Avoid Vert.x codegen usage; prefer manually defined types/interfaces.
* Use `vertx-health-check` module for per-resource health checks. Health endpoints must reflect internal dependencies including:

  * Database connectivity
  * Cache readiness
  * Outbound service reachability
  * Message broker (if applicable)
