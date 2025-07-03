# AI Front - Authenticated Micro Frontend Shell

## Purpose

AI Front is an authenticated micro frontend shell built with Angular 20 that serves as a container for dynamically loaded micro frontends. It provides:

- A consistent layout with header, sidebar, and main content areas
- Authentication via Keycloak
- Routing for embedded micro frontends
- Lazy loading of components for improved performance

## Authentication Mechanism

The application uses OAuth 2.0 / OpenID Connect (OIDC) for authentication with the following flow:

1. On startup, the application checks if the user is authenticated
2. If not authenticated, the user is redirected to Keycloak login page
3. After successful login, Keycloak redirects back to the application with an access token in the URL hash fragment
4. The application extracts and stores the token in session storage
5. JWT token is parsed to extract user information and check token expiration
6. Protected routes use an AuthGuard that redirects unauthenticated users to the login page
7. Logout functionality clears the token and redirects to Keycloak logout endpoint

## Build & Deploy Notes

### Local Development

1. Install dependencies:
   ```
   npm install
   ```

2. Run the development server:
   ```
   npm start
   ```

3. Build for production:
   ```
   npm run build
   ```

### Environment Variables

The application uses the following environment variables:

- `AUTH_HOST`: Keycloak host URL (default: https://auth.gedmarc.co.za)
- `OAUTH_CLIENT_ID`: OAuth client ID for the application
- `OAUTH_REDIRECT_URI`: Redirect URI after authentication

### CI/CD Pipeline

The application is built and deployed using GitHub Actions:

1. On push to main branch, the workflow is triggered
2. Node.js environment is set up
3. Dependencies are installed
4. Application is built for production
5. Docker image is built and pushed to GCP Artifact Registry
6. Application is deployed to Cloud Run with the following configuration:
   - CPU startup boost enabled
   - Environment variables injected from secrets
   - Public HTTPS endpoint for OAuth redirect

### Cloud Run Configuration

The application is deployed to Cloud Run with the following settings:

- CPU startup boost enabled via annotation
- Memory: 512Mi
- CPU: 1
- Port: 8080
- Unauthenticated access allowed (authentication handled by the application)
- Environment variables injected from secrets

## Technical Details

- **Framework**: Angular 20
- **Styling**: Native HTML (no external CSS frameworks)
- **Forms**: Template-driven using [(ngModel)]
- **Routing**: Angular Router with lazy loading
- **Authentication**: OIDC via Keycloak
- **Deployment**: Docker container on GCP Cloud Run