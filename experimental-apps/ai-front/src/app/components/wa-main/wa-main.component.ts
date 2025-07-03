import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'wa-main',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <div class="wa-main">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    .wa-main {
      height: 100%;
      width: 100%;
      padding: 20px;
    }
  `]
})
export class WaMainComponent {}