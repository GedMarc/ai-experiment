# 🔐 Environment Variables – advanced-calculator

This document defines required and optional environment variables specifically for the `advanced-calculator` module. It extends the base configuration defined in `orchestration/rules/env-variables.md`.

---

## 🧪 Purpose

This microservice provides scientific, temperature, currency, and weight conversion calculations. It supports dynamic plugin registration and asynchronous integration via the event bus.

---

## 🌍 Scope: Application-Level

These variables are expected to be configured at the **Cloud Run service level** or within **docker-compose overrides** for local execution.

---

## 🔧 Required Variables

| Key                          | Description                                                     | Example Value                     |
| ---------------------------- | --------------------------------------------------------------- | --------------------------------- |
| `APP_NAME`                   | Service name identifier for logs/traces                         | `advanced-calculator`             |
| `PORT`                       | Internal HTTP port used by Vert.x                               | `8080`                            |
| `CALC_MODE_ENABLED`          | Comma-separated list of calculation modes                       | `scientific,temperature,currency` |
| `CURRENCY_API_ENDPOINT`      | External API endpoint for currency conversion                   | `https://api.exchangeratesapi.io` |
| `CURRENCY_API_KEY`           | Key/token for accessing the currency API                        | `super-secret-api-key`            |
| `MONEY_FORMAT_LOCALE`        | Locale to format monetary results                               | `en-ZA`                           |
| `CURRENCY_DEFAULT_LOCALE`    | Default locale for formatting currency values                   | `en-US`                           |
| `CURRENCY_DEFAULT_ROUNDING`  | Rounding strategy (e.g., `default`, `cash`, or ISO code)        | `default`                         |
| `CURRENCY_FACTORY_TYPE`      | Factory implementation (`money`, `fastmoney`, or custom SPI)    | `money`                           |
| `CURRENCY_SUPPORTED_CODES`   | Comma-separated ISO currency codes for validation and filtering | `USD,EUR,ZAR`                     |
| `CURRENCY_EXCHANGE_PROVIDER` | Optional exchange rate provider SPI name                        | `ECB`                             |

---

## 🔐 Optional / Internal Defaults

| Key                    | Purpose                                 | Default     |
| ---------------------- | --------------------------------------- | ----------- |
| `JVM_OPTS`             | Additional JVM flags                    | `-Xmx256m`  |
| `TRACE_ENABLED`        | Toggle for distributed tracing          | `true`      |
| `VERTX_EVENT_BUS_HOST` | Host used for connecting to central bus | `localhost` |
| `VERTX_EVENT_BUS_PORT` | TCP port for Vert.x EventBus bridge     | `7000`      |

---

## 🔒 Secure Key Notes

* Secrets (e.g., `CURRENCY_API_KEY`) must be stored in **GCP Secret Manager** and mounted at runtime.
* For GitHub Actions or Cloud Build, inject secrets via environment substitutions.

---

## 🔁 Local Development

When using Docker Compose:

* Populate a `.env` file at the root of this module
* Copy from `.env.example` and override accordingly

```dotenv
APP_NAME=advanced-calculator
PORT=8080
CALC_MODE_ENABLED=scientific,temperature,currency
CURRENCY_API_ENDPOINT=https://api.exchangeratesapi.io
CURRENCY_API_KEY=super-secret-api-key
MONEY_FORMAT_LOCALE=en-ZA
CURRENCY_DEFAULT_LOCALE=en-US
CURRENCY_DEFAULT_ROUNDING=default
CURRENCY_FACTORY_TYPE=money
CURRENCY_SUPPORTED_CODES=USD,EUR,ZAR
CURRENCY_EXCHANGE_PROVIDER=ECB
```

---

## 📦 Related:

* `Dockerfile`
* `cloudbuild.yaml`
* `.github/workflows/advanced-calc-ci.yml`
* `rules/env-variables.md` (shared conventions)
* `enhancements/currency-enhancement.md`
