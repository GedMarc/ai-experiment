# 🔒 Security & Authentication Rules — ExperimentalAIApp

## Purpose

This document defines the authentication and security propagation rules for the ExperimentalAIApp shell and its dynamically mounted micro frontends (MFEs) and microservices.

---

## Identity Strategy

* The ExperimentalAIApp functions as the identity authority for all MFEs.
* Authentication is delegated to **Keycloak**, deployed in private mode with realm-specific configuration.
* All identity assertions are transformed into a **signed JWT** which contains:

  * `sub` (subject)
  * `roles`
  * `exp`, `iat`
  * `tenantId` (if multi-tenant)
  * `features` (optional: feature flags)

---

## Token Flow

* Users authenticate once through the shell
* Shell obtains a signed JWT from Keycloak
* JWT is stored in secure, HTTP-only cookie or local memory depending on client mode
* JWT is propagated to:

  * All API calls via `Authorization: Bearer <token>`
  * All MFEs via injected config as `AUTH_TOKEN`
  * Vert.x event bus via message metadata

---

## Cross-MFE Session Propagation

* All MFEs must:

  * Accept the injected `AUTH_TOKEN`
  * Parse and validate token client-side
  * Reuse the token in all outgoing requests

* MFEs must not:

  * Perform independent authentication
  * Persist tokens to storage (unless approved)
  * Modify auth headers outside defined mechanism

---

## Microservice Expectations

* Services must validate the JWT on every request
* Recommended: lightweight JWT verifier (e.g., Vert.x JWT auth, Keycloak adapters, or Nimbus)
* Claims such as `roles` and `tenantId` must drive authorization decisions
* Sensitive APIs must check:

  * Token expiration
  * Correct audience and issuer (Keycloak realm URL)
  * Required scopes/roles

---

## Configuration and Injection

| Key             | Description                                    |
| --------------- | ---------------------------------------------- |
| `AUTH_TOKEN`    | Injected bearer token (signed JWT)             |
| `AUTH_PROVIDER` | Set to `keycloak`                              |
| `USER_CLAIMS`   | Parsed user claims from JWT (optional preload) |

> Claims can be made available via a shared auth service or hook in the shell

---

## Failure Handling

* Expired/invalid tokens must:

  * Trigger logout sequence in shell
  * Unmount active MFEs
  * Display reauthentication prompt or redirect to Keycloak login

* Unauthorized MFEs must:

  * Display error component or fallback content
  * Never crash the shell or leak token errors

---

## Development & Testing

* Local test environments may use mock tokens with:

  * Short expiry (5-15 min)
  * Known shared secrets
  * Dev-only roles (e.g., `DEV_ADMIN`)

* Test tools:

  * JWT.io + test payloads
  * Postman for API flow
  * Cypress/Playwright for token propagation
  * Keycloak Dev UI for realm, role, user management

---

## Compliance Checklist

* [ ] Shell defines and maintains the canonical token via Keycloak
* [ ] All MFEs accept injected token and do not re-authenticate
* [ ] All services validate and authorize based on JWT from Keycloak
* [ ] Failure scenarios are gracefully handled
* [ ] Claims usage and propagation are documented

---

## Core Features

* The following features are implemented as part of the base authentication mechanism:

  * Token renewal (silent reauth) via Keycloak refresh tokens
  * Session revocation via Keycloak push events or polling
  * Tenant-based authorization filtering via Keycloak mappers
  * User metadata synchronization and session hooks

---

## Related Contracts & API Exposure

| Endpoint        | Description                        |
| --------------- | ---------------------------------- |
| `/openapi.yaml` | Static contract for shell API      |
| `/openapi.json` | Optional generated OpenAPI spec    |
| `/docs`         | Swagger UI frontend for shell APIs |

Other services must expose OpenAPI at the same root levels for consistency.

---
