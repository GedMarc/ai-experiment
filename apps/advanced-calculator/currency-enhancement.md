# 💱 Currency Calculation Enhancement – Advanced Calculator

This enhancement proposal defines how the `advanced-calculator` module supports currency conversion and formatting using `javax.money` (JSR 354), with support for both static formatting and exchange rate conversions.

---

## 🧪 Purpose

Enhance currency-related calculations by:

* Using **JSR 354** standard interfaces
* Supporting **multi-currency conversions**
* Providing **locale-aware formatting**
* Allowing runtime customization via **environment variables**

---

## 📦 Libraries

* **API**: `javax.money:money-api:1.1`
* **RI (default)**: `org.javamoney:moneta:1.4.2` (or latest compatible)
* **Alternative**: `org.javamoney:moneta-core`, `moneta-fastmoney`

---

## 🧱 Implementation Goals

* All conversion operations use `Monetary.getCurrency()` and `MonetaryConversions.getConversion()`
* Fallback conversion strategy available (e.g., default 1:1 or configured multiplier)
* External exchange APIs (optional) supported via SPI or gateway service
* Default formatting behavior from:

  ```java
  MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.getDefault());
  format.format(money);
  ```

---

## 🔧 Environment Variables (See `env-variables.md`)

| Key                          | Purpose                                         |
| ---------------------------- | ----------------------------------------------- |
| `CURRENCY_DEFAULT_LOCALE`    | Sets formatting locale                          |
| `CURRENCY_DEFAULT_ROUNDING`  | Defines rounding name ("default", "cash", etc.) |
| `CURRENCY_FACTORY_TYPE`      | Implementation (`money`, `fastmoney`, etc.)     |
| `CURRENCY_SUPPORTED_CODES`   | Filter for allowed currencies                   |
| `CURRENCY_EXCHANGE_PROVIDER` | Optional exchange rate SPI provider             |

---

## 🌐 Exchange Rate Support

If `CURRENCY_EXCHANGE_PROVIDER` is defined, the system will attempt to resolve conversions using:

```java
MonetaryConversions.getConversion("ZAR")
```

Otherwise, the fallback strategy is:

```java
amount.multiply(DEFAULT_RATE)
```

where `DEFAULT_RATE = 1` or configured.

---

## 🧪 Example Code Snippet

```java
MonetaryAmount amount = Monetary.getDefaultAmountFactory()
  .setCurrency("USD")
  .setNumber(100)
  .create();

CurrencyConversion conversion = MonetaryConversions.getConversion("ZAR");
MonetaryAmount converted = amount.with(conversion);
```

---

## ✅ Unit Test Considerations

* Validate formatting across locales
* Mock exchange rates using custom `ExchangeRateProvider`
* Assert that disallowed currencies trigger errors or fallbacks

---

## 🗺️ Future Enhancements

* Local FX cache via distributed store (Hazelcast)
* Plugin architecture for dynamic SPI registration
* Live monitoring of currency usage metrics

---

## 📚 References

* [JSR 354: Money and Currency API](https://jcp.org/en/jsr/detail?id=354)
* [Moneta GitHub](https://github.com/JavaMoney/moneta)
* [Baeldung Guide](https://www.baeldung.com/java-money-and-currency)
