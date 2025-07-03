import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  template: `
    <div class="dashboard-container">
      <h1>Dashboard</h1>
      <p>Welcome to the AI Shell Frontend Dashboard</p>
      <div class="placeholder-content">
        <p>This is a placeholder for the dashboard content.</p>
        <p>In a real application, this would be a micro frontend loaded dynamically.</p>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 20px;
    }
    
    h1 {
      margin-bottom: 16px;
    }
    
    .placeholder-content {
      margin-top: 24px;
      padding: 20px;
      background-color: #f5f5f5;
      border-radius: 4px;
    }
  `]
})
export class DashboardComponent {}