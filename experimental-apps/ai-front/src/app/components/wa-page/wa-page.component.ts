import { Component } from '@angular/core';

@Component({
  selector: 'wa-page',
  standalone: true,
  template: `
    <div class="wa-page">
      <div class="wa-page-header">
        <ng-content select="[slot=header]"></ng-content>
      </div>
      <div class="wa-page-content">
        <div class="wa-page-sidebar">
          <ng-content select="[slot=sidebar]"></ng-content>
        </div>
        <div class="wa-page-main">
          <ng-content select="[slot=main]"></ng-content>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .wa-page {
      display: flex;
      flex-direction: column;
      height: 100vh;
      width: 100%;
    }

    .wa-page-header {
      flex: 0 0 auto;
      border-bottom: 1px solid #e0e0e0;
    }

    .wa-page-content {
      display: flex;
      flex: 1 1 auto;
      overflow: hidden;
    }

    .wa-page-sidebar {
      flex: 0 0 250px;
      border-right: 1px solid #e0e0e0;
      overflow-y: auto;
    }

    .wa-page-main {
      flex: 1 1 auto;
      overflow-y: auto;
      padding: 20px;
    }
  `]
})
export class WaPageComponent {}
