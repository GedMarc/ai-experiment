# AI Experiment JPMS Project

This project demonstrates a refined JPMS Maven structure with JLink and GitHub Packages publishing capabilities.

## Project Structure

The project is organized into the following directories:

- `apps/`: Contains application modules that are built as JLink images
- `experimental-apps/`: Contains experimental application modules that are built as JLink images
- `libs/`: Contains library modules that are built as JARs and published to GitHub Packages

## Module Structure

Each module follows the JPMS (Java Platform Module System) structure:

- Source code: `src/main/java/za/co/ai/experiment/<name>`
- Test code: `src/test/java/za/co/ai/experiment/<name>/tests`
- Module descriptor: `src/main/java/module-info.java`
- Test module descriptor: `src/test/java/module-info.java`

## Library Modules

Library modules are located in the `libs/` directory and are built as JARs. They are published to GitHub Packages for consumption by application modules.

Each library module has:
- A `pom.xml` file with individual versioning (semver)
- A GitHub Actions workflow for publishing to GitHub Packages

## Application Modules

Application modules are located in the `apps/` and `experimental-apps/` directories. Each application consists of two Maven modules:

1. **Core App Module (JAR)**
   - Contains the application code and JPMS module
   - Packaging: `jar`
   - Contains: `src/main/java`, `src/test/java`, `module-info.java`
   - Named: `basic-calculator`, `advanced-calculator`, etc.

2. **JLink Runtime Module**
   - Depends on the core app module
   - Packaging: `jlink`
   - No source code, only `pom.xml`
   - Named: `basic-calculator-runtime`, `advanced-calculator-runtime`
   - Responsible for generating the runtime image

Each application has:
- Core module with a `pom.xml` file with JPMS configuration
- Runtime module with a `pom.xml` file with JLink plugin setup
- Separate GitHub Actions workflows for building the core module and the runtime module
- A Dockerfile for containerization (in the runtime module)
- A cloudbuild.yaml file for Cloud Build integration (in the runtime module)

## Building and Running

### Building a Library Module

```bash
mvn clean install -pl libs/<lib-name> -am
```

### Building an Application Core Module

```bash
mvn clean package -pl apps/<app-name> -am
```

### Building an Application Runtime Module

```bash
mvn clean package -pl apps/<app-name>-runtime -am
```

### Running an Application

```bash
./apps/<app-name>-runtime/target/jlink-image/bin/<app-name>
```

## GitHub Actions Workflows

The project includes GitHub Actions workflows for:

- Publishing library modules to GitHub Packages
- Building application core modules and publishing JAR artifacts
- Building application runtime modules and publishing JLink images

## Cloud Compatibility

Application runtime modules are configured for cloud deployment:

- They pull dependencies from GitHub Packages
- They use JLink-generated runtime images as container base
- They work with Cloud Build Docker builds
- They follow environment variable conventions for startup
- The Dockerfile and cloudbuild.yaml files are located in the runtime module
