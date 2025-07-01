# 📗 Rules: Basic Calculator Plugin (expai-basic-calculator)

## Purpose

Defines the rules for the `basic-calculator` plugin module within the `ExperimentalAIApp`. This plugin includes:

* A backend microservice (`basic-calculator-service`)
* A frontend micro-frontend (`basic-calculator-mf`)
* Shared contracts for event communication and interaction

---

## Naming

* Angular selectors and prefixes: `expai-basic-calc-*`
* Backend module name: `expai.basic.calculator`
* Frontend entry: `apps/ExperimentalAIApp/basic-calculator-mf`
* Backend entry: `apps/ExperimentalAIApp/basic-calculator-service`

---

## Backend (basic-calculator-service)

* Built in Java 24 with Vert.x 5 and JPMS
* Provides basic operations: `add`, `subtract`, `multiply`, `divide`
* Exposes event bus endpoints:

  * `expai.calculator.compute`
* Stateless and idempotent service calls
* Fully tested with Testcontainers and JUnit
* Health endpoint must confirm Vert.x bus connectivity and event loop load
* Secure via Keycloak if deployed to prod env

## Frontend (basic-calculator-mf)

* Built in Angular 20 and exported as Web Component via `@angular/elements`
* Selector: `<expai-basic-calc></expai-basic-calc>`
* Web Component renders:

  * Input fields for A, B
  * Dropdown to select operation
  * Result panel
* Communicates with backend via event bus using HTTP BFF relay (via shell)
* Must work standalone or as part of shell composition
* Styled using Shadow DOM-scoped `<style>`

---

## Contract

```json
{
  "type": "compute-request",
  "payload": {
    "a": number,
    "b": number,
    "op": "add" | "subtract" | "multiply" | "divide"
  }
}
```

* Replies with:

```json
{
  "type": "compute-result",
  "result": number
}
```

---

## Testing

* Frontend tested via BrowserStack for interaction and layout
* Backend tested via full unit and integration coverage
* BFF interface mocked for standalone frontend CI

---

## Documentation

* All three files: `rules.md`, `guides.md`, `implementation.md`
* Included in GitHub Pages deployment if built

---

Ready for microfrontend + service guides.
