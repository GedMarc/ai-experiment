import { Routes } from '@angular/router';
import { inject } from '@angular/core';
import { RouteService } from './services/route.service';

// Use a factory function to generate routes dynamically
export const routes: Routes = (() => {
  // This will be executed during app initialization
  // We can't use DI directly here, so we use the inject function
  const routeService = inject(RouteService);
  return routeService.generateRoutes();
})();
