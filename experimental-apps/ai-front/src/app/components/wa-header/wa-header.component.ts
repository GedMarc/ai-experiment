import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'wa-header',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="wa-header">
      <div class="wa-header-title">
        <h1>Junie Prompt — AI Shell Frontend</h1>
      </div>
      <div class="wa-header-actions">
        <button class="wa-header-avatar" (click)="logout()">
          <span class="avatar-icon">👤</span>
          <span>Logout</span>
        </button>
      </div>
    </div>
  `,
  styles: [`
    .wa-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 20px;
      height: 60px;
      width: 100%;
    }
    
    .wa-header-title h1 {
      margin: 0;
      font-size: 1.2rem;
      font-weight: 500;
    }
    
    .wa-header-actions {
      display: flex;
      align-items: center;
    }
    
    .wa-header-avatar {
      display: flex;
      align-items: center;
      background: none;
      border: none;
      cursor: pointer;
      padding: 8px 12px;
      border-radius: 4px;
      transition: background-color 0.2s;
    }
    
    .wa-header-avatar:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }
    
    .avatar-icon {
      font-size: 1.5rem;
      margin-right: 8px;
    }
  `]
})
export class WaHeaderComponent {
  constructor(private authService: AuthService) {}
  
  logout(): void {
    this.authService.logout();
  }
}