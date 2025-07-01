# 📘 Rules: Advanced Calculator Module (advanced-calculator)

## Purpose

Defines platform-level structural and functional boundaries for the advanced-calculator module, a pluggable microservice/microfrontend pair within the ExperimentalAIApp. This module supports advanced computational logic, including scientific, temperature, currency, and weight conversions.

---

## 🧱 Domain Scope

* Domain: `expai.advanced-calc`
* Exposed via EventBus endpoints per operation mode
* Provides frontend UI interaction and backend computational service via BFF or direct EventBus (if integrated)

---

## 🧮 Supported Modes

* **Scientific**: Trigonometry, logarithmic, power, factorial
* **Temperature**: Celsius ⇌ Fahrenheit ⇌ Kelvin
* **Currency**: Via `javax.money` with pluggable FX provider
* **Weight**: kg ⇌ lb ⇌ g ⇌ oz

Each mode must support both string-based user input and numerical UI input for accessibility.

---

## 🔌 Integration Points

* Frontend communicates via Vert.x Web EventBus bridge (SockJS)
* Backend exposes address endpoints:

  * `expai.advanced-calc.scientific`
  * `expai.advanced-calc.temperature`
  * `expai.advanced-calc.currency`
  * `expai.advanced-calc.weight`

---

## 🧩 Frontend Selector

```html
<expai-advanced-calc></expai-advanced-calc>
```

* Rendered via Angular 20 Web Component (Shadow DOM encapsulated)
* Mode selector toggles form inputs per calculation category
* Frontend must emit user-friendly error messages on invalid computation requests

---

## 📜 Java Module Rules

* JPMS module name: `za.experimentalai.advancedcalc`
* Uses: `javax.money`, `java.math`, `java.time`
* Provides: `AdvancedCalculationService`
* Services discovered via `ServiceLoader`
* All logic encapsulated to allow hotplug without recompiling ExperimentalAIApp

---

## ⚠️ Constraints

* UI must not expose all modes unless backend support is confirmed via health check
* Currency must fallback or warn if FX rate provider is not available
* Module must register itself dynamically during shell startup for menu injection

---

## ✅ Acceptance

* Manual and e2e tests per operation mode
* BrowserStack compatibility for web component
* Passes sonar + unit tests
* EventBus bridge health tested and exposed

---

Module is now ready for guide and implementation scoping.
