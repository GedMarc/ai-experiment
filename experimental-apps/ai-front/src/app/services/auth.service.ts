import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';

interface JwtPayload {
  sub: string;
  name?: string;
  email?: string;
  preferred_username?: string;
  roles?: string[];
  exp: number;
  iat: number;
  [key: string]: any;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'access_token';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private userInfoSubject = new BehaviorSubject<JwtPayload | null>(null);

  constructor() {
    // Check if token exists in hash fragment on startup
    this.checkTokenInHash();

    // Check if token exists in memory
    const token = this.getToken();
    if (token) {
      try {
        const decodedToken = this.parseJwt(token);
        // Check if token is expired
        if (decodedToken && decodedToken.exp * 1000 > Date.now()) {
          this.isAuthenticatedSubject.next(true);
          this.userInfoSubject.next(decodedToken);
        } else {
          // Token is expired, remove it
          this.logout();
        }
      } catch (error) {
        console.error('Error parsing JWT token:', error);
        this.logout();
      }
    }
  }

  /**
   * Check if token exists in hash fragment and store it
   */
  private checkTokenInHash(): void {
    if (typeof window !== 'undefined') {
      const hash = window.location.hash;
      if (hash.includes('access_token=')) {
        const token = this.extractTokenFromHash(hash);
        if (token) {
          this.setToken(token);

          // Check if there's a redirect URL stored
          const redirectUrl = sessionStorage.getItem('redirectUrl');

          // Remove the token from the URL to prevent security issues
          window.history.replaceState({}, document.title, redirectUrl || window.location.pathname);

          // Clear the stored redirect URL
          if (redirectUrl) {
            sessionStorage.removeItem('redirectUrl');
          }
        }
      }
    }
  }

  /**
   * Extract token from hash fragment
   */
  private extractTokenFromHash(hash: string): string | null {
    const match = hash.match(/access_token=([^&]*)/);
    return match ? match[1] : null;
  }

  /**
   * Parse JWT token
   */
  private parseJwt(token: string): JwtPayload {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  }

  /**
   * Store token in memory
   */
  private setToken(token: string): void {
    try {
      const decodedToken = this.parseJwt(token);
      if (decodedToken && decodedToken.exp * 1000 > Date.now()) {
        sessionStorage.setItem(this.TOKEN_KEY, token);
        this.isAuthenticatedSubject.next(true);
        this.userInfoSubject.next(decodedToken);
      } else {
        console.error('Token is expired or invalid');
        this.logout();
      }
    } catch (error) {
      console.error('Error parsing JWT token:', error);
      this.logout();
    }
  }

  /**
   * Get token from memory
   */
  getToken(): string | null {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }

  /**
   * Check if user is authenticated
   */
  isAuthenticated(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  /**
   * Get user information from token
   */
  getUserInfo(): Observable<JwtPayload | null> {
    return this.userInfoSubject.asObservable();
  }

  /**
   * Check if user has a specific role
   */
  hasRole(role: string): Observable<boolean> {
    return new Observable<boolean>(observer => {
      this.getUserInfo().subscribe(userInfo => {
        if (userInfo && userInfo.roles && userInfo.roles.includes(role)) {
          observer.next(true);
        } else {
          observer.next(false);
        }
        observer.complete();
      });
    });
  }

  /**
   * Redirect to Keycloak login
   */
  login(): void {
    const keycloakUrl = `${environment.auth.host}/realms/ai-experiment/protocol/openid-connect/auth`;
    const clientId = environment.auth.clientId;
    const redirectUri = environment.auth.redirectUri;

    const url = `${keycloakUrl}?client_id=${clientId}&redirect_uri=${encodeURIComponent(redirectUri)}&response_type=token`;
    window.location.href = url;
  }

  /**
   * Logout user and redirect to Keycloak logout
   */
  logout(): void {
    const keycloakUrl = `${environment.auth.host}/realms/ai-experiment/protocol/openid-connect/logout`;
    const redirectUri = environment.auth.redirectUri;

    // Clear token from memory
    sessionStorage.removeItem(this.TOKEN_KEY);
    this.isAuthenticatedSubject.next(false);

    // Redirect to Keycloak logout
    window.location.href = `${keycloakUrl}?redirect_uri=${encodeURIComponent(redirectUri)}`;
  }
}
