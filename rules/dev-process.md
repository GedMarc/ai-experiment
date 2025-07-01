# Development Process Rules

* Test-first development.
* Tests must use Testcontainers for PostgreSQL, not H2.
* Unit and integration coverage enforced via SonarQube.
* `dev`, `qe`, and `main` branches define environments.
* Maven release plugin used for controlled versioning.
* PR-triggered semantic commits and pre-commit checks.
* CI/CD must tag release notes and docs on GitHub Pages.