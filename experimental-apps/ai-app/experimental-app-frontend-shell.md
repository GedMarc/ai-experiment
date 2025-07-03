# 🛠️ Implementation: ExperimentalAIApp Frontend Shell

## 🧭 Overview

This shell application hosts all available micro frontends via dynamic menu integration and scoped slot rendering. It is Angular 20-based, supports runtime discovery of available modules, and serves as the root deployment target. It also manages authentication, role-based page access, and session state.

---

## 🗂️ Structure

```
apps/experimental-ai-app-frontend/
├── src/app/
│   ├── app.component.ts
│   ├── app.component.html
│   ├── menu.service.ts
│   ├── auth.service.ts
│   ├── login.component.ts
│   ├── guard.service.ts
│   ├── microfrontend-loader.directive.ts
│   ├── app.routes.ts
│   └── styles.scss
├── assets/
├── environments/
├── index.html
└── main.ts
```

---

## 🔐 Authentication & Access Control

* Login managed via `auth.service.ts` and `login.component.ts`
* Session token retained via secure cookie
* Protected routes guarded via `guard.service.ts`
* Sample users:

  * Admin: `admineestrate / unlockthedoorhandles123`

    * Can access all frontends and configuration pages
  * Calculator User: `calcuser / calc4life`

    * Only permitted access to calculator modules
* Roles attached to tokens and enforced on frontend

---

## 📋 Menu Registry (Dynamic)

* `menu.service.ts` maintains a list of known microfrontends (fetched statically or loaded at runtime)

* Each menu item includes:

  ```ts
  export interface MicroFrontend {
    name: string;
    selector: string;
    address: string;
    roles?: string[];
    featureFlag?: string;
    modes?: string[];
  }
  ```

* Current registered microfrontends:

  * `Basic Calculator`

    ```ts
    { name: 'Basic Calculator', selector: 'expai-basic-calc', address: 'expai.calculator.compute', roles: ['calculator'] }
    ```
  * `Advanced Calculator`

    ```ts
    { name: 'Advanced Calculator', selector: 'expai-advanced-calc', address: 'expai.advanced-calc.*', roles: ['calculator'] }
    ```

---

## 🔍 Microfrontend Loader

A dynamic loader directive checks the active route and injects the `<expai-*></expai-*>` element into the DOM.

---

## 🔌 EventBus Ready

EventBus JS client initialized on root and shared through service injection:

```ts
export const eb = new EventBus('/eventbus');
```

---

## 🖥️ Layout & Shell Behavior

* Responsive top nav and side menu
* Shell reads available micro frontends from menu registry
* Only those present in the final build are shown
* Feature-flagged items are hidden if flag is not enabled
* Navigating to a route renders the corresponding component dynamically
* User roles determine which menu items are shown

---

## 📦 Output

* Serves as main frontend deployed to GCP Cloud Run
* Includes all built micro frontends scoped via Web Components
* Defaults to dashboard/intro shell if no routes match
* All login/auth pages included and styled internally

---

## 🧪 Testing

* Angular unit tests on shell and service logic
* e2e tests for loading each microfrontend
* Auth flow tested with mock and real tokens
* Visual snapshots via BrowserStack for major resolutions

---

Shell fully supports multi-microfrontend modular Angular Web Component loading with style and runtime scoping, login and access control, and full test orchestration. Designed for integration with Keycloak or custom JWT providers.
