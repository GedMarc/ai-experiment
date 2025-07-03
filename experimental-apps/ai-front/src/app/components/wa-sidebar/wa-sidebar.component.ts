import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'wa-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  template: `
    <div class="wa-sidebar">
      <nav class="wa-sidebar-nav">
        <ul class="wa-sidebar-nav-list">
          <li class="wa-sidebar-nav-item" *ngFor="let item of navItems">
            <a 
              [routerLink]="item.route" 
              routerLinkActive="active" 
              class="wa-sidebar-nav-link"
            >
              <span class="wa-sidebar-nav-icon">{{ item.icon }}</span>
              <span class="wa-sidebar-nav-text">{{ item.name }}</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  `,
  styles: [`
    .wa-sidebar {
      height: 100%;
      width: 100%;
      background-color: #f8f9fa;
    }
    
    .wa-sidebar-nav {
      padding: 20px 0;
    }
    
    .wa-sidebar-nav-list {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    
    .wa-sidebar-nav-item {
      margin-bottom: 4px;
    }
    
    .wa-sidebar-nav-link {
      display: flex;
      align-items: center;
      padding: 12px 20px;
      color: #333;
      text-decoration: none;
      transition: background-color 0.2s;
      border-radius: 0;
    }
    
    .wa-sidebar-nav-link:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }
    
    .wa-sidebar-nav-link.active {
      background-color: rgba(0, 0, 0, 0.1);
      font-weight: 500;
    }
    
    .wa-sidebar-nav-icon {
      margin-right: 12px;
      font-size: 1.2rem;
    }
  `]
})
export class WaSidebarComponent {
  navItems = [
    { name: 'Dashboard', route: '/dashboard', icon: '📊' },
    { name: 'AI Tools', route: '/ai-tools', icon: '🧠' },
    { name: 'Settings', route: '/settings', icon: '⚙️' }
  ];
}