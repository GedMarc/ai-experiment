# Documentation Rules

* All backend and frontend modules must include a `README.md` at root level explaining purpose, usage, endpoints, and environment.
* README files must include Mermaid diagrams if applicable (e.g., flowcharts, sequence diagrams).
* A `docs/` folder must exist in each module for additional `.md` files like `rules.md`, `guides.md`, `setup.md`.
* Microfrontends must include self-contained documentation without referencing third-party component libraries.
* GitHub Pages must be generated on merge into `main`, automatically publishing module and system-level documentation.
* Diagrams must be valid Mermaid syntax and pass diagram linting.
* Internal documentation (e.g., ADRs, analysis) must reside in `.docs/` and be excluded from public docs.