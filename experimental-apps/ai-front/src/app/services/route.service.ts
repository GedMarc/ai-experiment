import { Injectable, Inject } from '@angular/core';
import { Routes } from '@angular/router';
import { APP_CONFIG, AppConfig } from '../config/app-config.token';
import { AuthGuard } from './auth.guard';

@Injectable({
  providedIn: 'root'
})
export class RouteService {
  constructor(@Inject(APP_CONFIG) private appConfig: AppConfig) {}

  /**
   * Generate dynamic routes based on enabled modules
   */
  generateRoutes(): Routes {
    const routes: Routes = [];

    // Default route
    routes.push({ path: '', redirectTo: this.appConfig.enabledModules.length > 0 ? this.appConfig.enabledModules[0] : 'no-modules', pathMatch: 'full' });

    // Add routes for each enabled module
    if (this.appConfig.enabledModules && this.appConfig.enabledModules.length > 0) {
      for (const module of this.appConfig.enabledModules) {
        routes.push({
          path: module,
          loadComponent: () => import(`../microfronts/${module}/${module}.component`).then(m => {
            // Get the component class name by capitalizing the first letter of the module name
            const componentName = module.charAt(0).toUpperCase() + module.slice(1) + 'Component';
            return m[componentName];
          }),
          canActivate: [AuthGuard]
        });
      }
    } else {
      // Add a route for the no-modules fallback
      routes.push({
        path: 'no-modules',
        loadComponent: () => import('../pages/no-modules/no-modules.component').then(m => m.NoModulesComponent)
      });
    }

    // Wildcard route
    routes.push({ path: '**', redirectTo: this.appConfig.enabledModules.length > 0 ? this.appConfig.enabledModules[0] : 'no-modules' });

    return routes;
  }
}