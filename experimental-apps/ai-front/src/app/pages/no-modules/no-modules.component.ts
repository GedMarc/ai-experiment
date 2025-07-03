import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-no-modules',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="empty-state">
      <h2>No available modules found at this time.</h2>
      <p>Please check back later.</p>
    </div>
  `,
  styles: [`
    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 100%;
      padding: 2rem;
      text-align: center;
    }
    
    h2 {
      margin-bottom: 1rem;
      color: #555;
    }
    
    p {
      color: #777;
    }
  `]
})
export class NoModulesComponent {}