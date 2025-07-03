import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

import { AuthService } from './services/auth.service';
import { WaPageComponent } from './components/wa-page/wa-page.component';
import { WaHeaderComponent } from './components/wa-header/wa-header.component';
import { WaSidebarComponent } from './components/wa-sidebar/wa-sidebar.component';
import { WaMainComponent } from './components/wa-main/wa-main.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    WaPageComponent,
    WaHeaderComponent,
    WaSidebarComponent,
    WaMainComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  isAuthenticated$: Observable<boolean>;

  constructor(private authService: AuthService) {
    this.isAuthenticated$ = this.authService.isAuthenticated();
  }

  ngOnInit(): void {
    // Initialize application
    console.log('AI Shell Frontend initialized');
  }

  login(): void {
    this.authService.login();
  }
}
