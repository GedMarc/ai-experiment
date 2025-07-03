import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'AI Application';
  isDrawerOpen = false;
  
  // Navigation items
  navItems = [
    { name: 'Dashboard', route: '/dashboard', icon: 'dashboard' },
    { name: 'AI Tools', route: '/ai-tools', icon: 'psychology' },
    { name: 'Settings', route: '/settings', icon: 'settings' }
  ];

  constructor() {}

  ngOnInit(): void {
    // Initialize application
    console.log('AI Application initialized');
  }

  toggleDrawer(): void {
    this.isDrawerOpen = !this.isDrawerOpen;
  }
}