# 📘 Micro Frontend Shell and Web Component Strategy

## Purpose

This document defines the rules and guide strategy for implementing:

* A unified Angular 20 shell for dynamic microfrontend composition
* Self-contained W3C-compliant Web Components (Custom Elements + Shadow DOM)
* Runtime-controlled frontend inclusion logic
* Component testing through BrowserStack

---

# 📗 Rules

## Shell Frontend (`shell-frontend`)

* Must be implemented in Angular 20.
* Responsible for rendering navigation, user login, and dynamic loading of micro frontends.
* Microfrontend entries are defined via manifest with `name`, `module`, `route`, and `loadCondition()` predicate.
* Build system must allow conditional inclusion of microfrontends in the final artifact.
* Menu items must only render for microfrontends present in the build.
* Uses scoped routing and Angular lazy loading for each microfrontend.

## Microfrontends (e.g., `login-mf`, `dashboard-mf`, etc.)

* Must be Angular 20 projects exporting Web Components via `@angular/elements` or native Custom Elements.
* Each component must:

  * Be W3C compliant
  * Register itself via `customElements.define()`
  * Encapsulate DOM using Shadow DOM
  * Include encapsulated styling either via `<style>` tag or `adoptedStyleSheets`
* Each microfrontend provides a module-level `README.md` and `docs/rules.md`, `docs/guides.md`, `docs/implementation.md`.
* No use of external styling frameworks (Bootstrap, Bulma, etc.).
* Components must expose a simple public API via attributes, events, and slots.

## Example Web Component Structure

```html
<!-- Minimal W3C-Compliant Styled Web Component -->
<my-component></my-component>

<script>
  class MyComponent extends HTMLElement {
    constructor() {
      super();
      const shadow = this.attachShadow({ mode: 'open' });

      const wrapper = document.createElement('div');
      wrapper.textContent = 'Hello, Web Component!';

      const style = document.createElement('style');
      style.textContent = `
        div {
          font-family: Arial, sans-serif;
          color: white;
          background-color: #007BFF;
          padding: 10px;
          border-radius: 5px;
          text-align: center;
        }
      `;

      shadow.appendChild(style);
      shadow.appendChild(wrapper);
    }
  }

  customElements.define('my-component', MyComponent);
</script>
```

## Testing

* Must be validated via BrowserStack CI for:

  * Component rendering
  * Event emissions
  * Slot and style encapsulation
* Functional flows tested using Cypress (or Playwright) with cloud-based runners.

## Deployment

* Frontend deployment artifacts are published via GitHub Actions.
* The shell must expose metadata indicating included microfrontends.
* Each microfrontend must be deployable standalone, and optionally embedded into the unified frontend.
* GitHub Pages publishes documentation and playground for all microfrontends.

---

# 📙 Guides

## Shell Frontend Composition

* The shell project loads manifest files defining available microfrontends.
* Navigation menu renders based on `loadCondition()` predicates at runtime.
* Microfrontends are dynamically imported via scoped Angular routes.

## Microfrontend Authoring Strategy

* Each microfrontend is built as an independent Angular project.
* Register a custom element and expose a shadow DOM-based root.
* Use build-time configuration to export Web Component wrappers.

---

# 📒 Implementation

## File Structure

```
frontend/
  shell-frontend/
    src/
    docs/
  login-mf/
    src/
    docs/
  dashboard-mf/
    src/
    docs/
```

## Shadow DOM Setup

All components use:

```ts
const shadow = this.attachShadow({ mode: 'open' });
```

Encapsulated styles should be defined in templates using `<style>` tags or injected via `adoptedStyleSheets`.

## Conditional Inclusion

Microfrontends not present in the build must be excluded from the shell menu via runtime evaluation of `loadCondition()`.

## Dev Server Strategy

* Use `ng serve` per microfrontend.
* Use `ng proxy` in `shell-frontend` for local development routing.

## BrowserStack Integration

* Use CLI integration in GitHub Actions workflows.
* Ensure cross-browser compatibility across latest Chrome, Firefox, Edge, Safari.

## Accessibility

* Each microfrontend must follow WCAG AA minimums.
* Shadow DOM must not block semantic structure.

---
