# 🛠️ Build & Publish Rules for AI Experiment Maven Modules

This guide defines the canonical rules and expectations for generating, publishing, and consuming Maven modules (both libraries and applications) within the `ai-experiment` platform.

---

## 📆 Module Types

### `libs/` Directory (Shared Libraries)

* Must follow JPMS (`module-info.java`)
* Must publish to GitHub Packages on commit/tag
* Must be independently buildable and installable
* Must not contain placeholder `.java` files
* Should include:

  * `lombok` (>= `1.18.38`)
  * `mapstruct` (`1.6.3`)
  * `jackson` (`2.18.3`) for JSON binding
  * `log4j2` for logging (optional SLF4J bridge)
  * If both Lombok and MapStruct are used together:

    ```xml
    <path>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok-mapstruct-binding</artifactId>
      <version>0.0.1</version>
    </path>
    ```

### `apps/` Directory (Microservices / Micro Frontends)

* Must depend **only** on published artifacts (not local paths)
* Must build and test independently on GitHub Actions and GCP Cloud Build
* Must use shared runtime env vars (see `env-variables.md`)
* Must contain a corresponding JLink packaging module for runtime image generation
* Dockerfiles must build from JLink runtime images and can use the **smallest base container images** (no full JDK required)

---

## 📇 Module Metadata & Structure

### Group ID

`za.co.ai.experiment`

### Artifact ID Format

* Libraries: `lib-<function>` (e.g., `lib-core-utils`, `lib-core-observability`)
* Applications: `app-<name>` (e.g., `app-basic-calculator`)
* Runtime Images: `<app>-runtime` (e.g., `basic-calculator-runtime`)

### Package Naming

* Source code: `za.co.ai.experiment.<module>`
* Test code: `za.co.ai.experiment.<module>.tests`

### JPMS Naming

* Source: `za.co.ai.experiment.<module>`
* Test: `za.co.ai.experiment.<module>.tests`

---

## 🚀 GitHub Packages Publishing

Each library module must:

* Include a GitHub Actions workflow to publish to:
  `https://maven.pkg.github.com/GedMarc/ai-experiment`
* Use `mvn deploy` with:

  * GitHub token authentication
  * Project version via release plugin (SemVer)
* Include:

  ```xml
  <distributionManagement>
    <repository>
      <id>github</id>
      <url>https://maven.pkg.github.com/GedMarc/ai-experiment</url>
    </repository>
  </distributionManagement>
  ```

---

## 🧲 CI/CD Integration

### GitHub Actions

* Each module must include a `.github/workflows/publish-<module>.yml`
* Should run unit tests, `jlink` validation (where applicable), and deploy on `main`
* JLink modules must build runnable JDK 24 images from app dependencies

### Cloud Build

* Apps will be built/deployed using GCP-native Cloud Build triggers
* All app dependencies must resolve from GitHub Packages

---

## ✅ Maven Enforcer Rules

We enforce:

* JDK compatibility (`maven.compiler.release`) — JDK 24
* Unique coordinates per module
* No duplicate dependencies
* No SNAPSHOT references for production builds

> ℹ️ Note: Maven Enforcer is currently not JDK 24 compatible — disable or override as needed.

---

## ♻️ Dependency Management

Root `pom.xml` should include:

* Centralized versions of:

  * `log4j2`
  * `lombok` (1.18.38+)
  * `jackson-databind` (2.18.3)
  * `mapstruct` (1.6.3)
  * `lombok-mapstruct-binding` (0.0.1, if applicable)
* Managed via `<dependencyManagement>`

---

## 🤩 Tips for AI Scaffold Generators

* Assume apps run on Cloud Build and deploy to Cloud Run
* Libraries must be published to GitHub Packages for consumption
* Do not generate empty `.java` files — focus on structure only
* All modules must include `module-info.java` with required `requires` clauses
* Test modules must mirror JPMS structure
* Default version is `1.0.0-SNAPSHOT`
* JLink builds must be in separate `<packaging>jlink</packaging>` modules
* Docker images must be based on runtime JLink builds using the smallest available base images (no JDKs)
* JLink plugin config **must** use updated syntax:

  ```xml
  <launcher>myapp=za.co.ai.experiment.app.module/com.example.Main</launcher>
  ```

  > ⚠️ Junie sometimes renders outdated configuration — avoid legacy plugin syntax where `<launcher>` was used with an invalid nested `<launcher><name>=<module></launcher>` structure. Use the correct `<launcher>` format with a string value like `<launcher>myapp=za.co.ai.experiment.module/com.example.Main</launcher>` instead.
