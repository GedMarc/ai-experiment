import { InjectionToken } from '@angular/core';

export interface AppConfig {
  enabledModules: string[];
}

export const APP_CONFIG = new InjectionToken<AppConfig>('APP_CONFIG');

export const defaultAppConfig: AppConfig = {
  enabledModules: ['dashboard', 'settings']
};