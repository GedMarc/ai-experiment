# Frontend Rules

Defines microfrontend expectations:

* W3C Web Components rendered in Angular 20 templates.
* No use of Bootstrap, Bulma or non-native CSS frameworks.
* Each microfrontend loads in isolation with shared shell.
* Use `ng serve` and `ng proxy` for development.
* Frontend shell must enable dynamic microfrontend loading via scoped routes and provide identity/auth wiring.