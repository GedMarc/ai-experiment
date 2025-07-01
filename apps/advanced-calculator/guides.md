# 📙 Guide: Advanced Calculator Module (advanced-calculator)

## Overview

This guide describes how the `advanced-calculator` module operates within the ExperimentalAIApp. It supports advanced computational domains and illustrates the interaction patterns between the frontend, backend, and user.

---

## 🔄 User Interaction Flow

1. **User selects mode**: Scientific, Temperature, Currency, or Weight
2. **User enters operands**
3. **Frontend dispatches a structured JSON message** to the appropriate EventBus address
4. **Backend processes the request**, validates input, and computes result
5. **Response sent back** to frontend and rendered in the result area

---

## 💬 Message Contracts

Each operation sends messages to its respective address:

* Address format: `expai.advanced-calc.{mode}`
* Payloads:

  ```json
  // Scientific
  {
    "operation": "sin",
    "value": 30
  }

  // Temperature
  {
    "from": "C",
    "to": "F",
    "value": 100
  }

  // Currency
  {
    "from": "USD",
    "to": "ZAR",
    "amount": 10.00
  }

  // Weight
  {
    "from": "kg",
    "to": "lb",
    "value": 5
  }
  ```
* Response:

  ```json
  {
    "result": 212
  }
  ```

---

## 🖼️ Component Anatomy

Angular Web Component: `<expai-advanced-calc>`

### Features:

* Dropdown to select mode
* Dynamic form that adapts to mode
* Displays result or error inline
* Sends messages using `EventBus` via Vert.x Web bridge

---

## ⚙️ Backend Details

Each mode is handled by its own class implementing the `AdvancedCalculationService` SPI.

### Health

Each mode registers a health check contributing to the module’s `/health/advanced-calc` composite endpoint.

---

## 🧪 Testing Strategy

* Each mode has unit tests
* JS frontend integration tests via BrowserStack
* EventBus interaction simulated using mock bridges in test runtime

---

Module integration is designed to be self-contained, discoverable at runtime, and extendable with minimal coupling.
