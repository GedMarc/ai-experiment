# 📒 Implementation: Basic Calculator Frontend (basic-calculator-mf)

## Module Overview

Implements a Web Component using Angular 20 that interfaces with the backend via BFF for arithmetic calculations. Packaged for use in both standalone and shell-based compositions.

---

## 📦 Module Location

```
apps/ExperimentalAIApp/basic-calculator-mf/
└── src/
    ├── app/
    │   └── basic-calc.component.ts
    ├── main.ts
    └── index.ts
```

---

## 🧩 Component Selector

```html
<expai-basic-calc></expai-basic-calc>
```

---

## 🧪 Component Class

```ts
@Component({
  selector: 'expai-basic-calc',
  standalone: true,
  template: `
    <div>
      <input [(ngModel)]="a" type="number" placeholder="A">
      <input [(ngModel)]="b" type="number" placeholder="B">
      <select [(ngModel)]="op">
        <option value="add">+</option>
        <option value="subtract">-</option>
        <option value="multiply">*</option>
        <option value="divide">/</option>
      </select>
      <button (click)="compute()">Compute</button>
      <div *ngIf="result !== null">Result: {{ result }}</div>
    </div>
  `,
  styles: [`
    :host {
      display: block;
      font-family: Arial;
    }
    div {
      padding: 1rem;
      border: 1px solid #ccc;
      border-radius: 8px;
    }
    input, select {
      margin-right: 0.5rem;
    }
  `],
  imports: [FormsModule, CommonModule],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class BasicCalcComponent {
  a: number = 0;
  b: number = 0;
  op: string = 'add';
  result: number | null = null;

  compute() {
    fetch('/api/bff/compute', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ a: this.a, b: this.b, op: this.op })
    })
    .then(res => res.json())
    .then(data => this.result = data.result)
    .catch(() => this.result = NaN);
  }
}
```

---

## 📦 Bootstrapping and Element Definition

```ts
// main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { BasicCalcComponent } from './app/basic-calc.component';
import { createCustomElement } from '@angular/elements';
import { Injector } from '@angular/core';

bootstrapApplication(BasicCalcComponent).then(appRef => {
  const injector = appRef.injector;
  const el = createCustomElement(BasicCalcComponent, { injector });
  customElements.define('expai-basic-calc', el);
});
```

---

## ✅ CI/CD

* Unit tested via Jest
* BrowserStack tests on Chrome, Firefox, Safari
* Cypress tests flow with backend BFF relay mock

---
