# 🛠️ Implementation: Advanced Calculator Frontend

## Web Component: `<expai-advanced-calc>`

Encapsulated Angular 20 web component, self-contained with Shadow DOM, responsive UI, and scoped style rules.

---

## 🧱 Directory Layout

```
projects/advanced-calculator-ui/
├── src/
│   ├── app/
│   │   ├── advanced-calc.component.ts
│   │   ├── advanced-calc.component.html
│   │   ├── advanced-calc.component.scss
│   │   └── eventbus.service.ts
│   ├── assets/
│   └── index.ts
├── ng-package.json
├── package.json
└── README.md
```

---

## 🎨 Component Behavior

* Displays dropdown for selecting mode: Scientific, Temperature, Currency, Weight
* Renders dynamic form based on selected mode
* Collects user inputs (e.g. operands, units)
* On submit: sends formatted JSON payload to corresponding EventBus address using `eventbus.service.ts`
* Shows result or error inline

---

## 🔌 EventBus Communication

```ts
const eb = new EventBus('/eventbus');
eb.send('expai.advanced-calc.temperature', { from: 'C', to: 'F', value: 100 }, res => {
  if (res.body.result !== undefined) {
    this.result = res.body.result;
  }
});
```

---

## 📦 Output

* Exposed as `<expai-advanced-calc></expai-advanced-calc>`
* Bundled via Angular Elements
* Shadow DOM encapsulated
* Registered dynamically into the ExperimentalAIApp’s menu if present

---

## 📐 Testing

* Karma for unit logic and view
* Cypress for e2e interaction per mode
* BrowserStack for full device and browser compatibility
* Test fixture includes a stub EventBus with mocked handlers

---

## ✅ Registration

On initialization, the component must:

* Register with parent shell if menu API is exposed
* Fallback to standalone rendering if loaded directly (developer mode)

---

Fully modular frontend component, style-agnostic, runtime-discoverable, integrated via Web EventBus bridge.
