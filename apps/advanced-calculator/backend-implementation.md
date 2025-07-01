# 🛠️ Implementation: Advanced Calculator Backend

## Project Structure

```
advanced-calculator-service/
├── src/
│   ├── main/java/za/experimentalai/advancedcalc/
│   │   ├── AdvancedCalcVerticle.java
│   │   ├── spi/
│   │   │   ├── AdvancedCalculationService.java
│   │   │   ├── ScientificService.java
│   │   │   ├── TemperatureService.java
│   │   │   ├── CurrencyService.java
│   │   │   └── WeightService.java
│   ├── resources/META-INF/services/
│   │   └── za.experimentalai.advancedcalc.spi.AdvancedCalculationService
│   └── module-info.java
```

## Verticle Configuration

```java
public class AdvancedCalcVerticle extends AbstractVerticle {
  @Override
  public void start() {
    ServiceLoader<AdvancedCalculationService> services = ServiceLoader.load(AdvancedCalculationService.class);
    services.forEach(service ->
      vertx.eventBus().consumer(service.address(), msg -> {
        JsonObject response = service.handle(msg.body());
        msg.reply(response);
      })
    );
  }
}
```

## Module Info

```java
module za.experimentalai.advancedcalc {
  requires io.vertx.core;
  requires io.vertx.codegen;
  requires io.vertx.web;
  requires io.vertx.healthchecks;
  requires java.money;
  requires java.logging;
  provides za.experimentalai.advancedcalc.spi.AdvancedCalculationService
      with za.experimentalai.advancedcalc.spi.ScientificService,
           za.experimentalai.advancedcalc.spi.TemperatureService,
           za.experimentalai.advancedcalc.spi.CurrencyService,
           za.experimentalai.advancedcalc.spi.WeightService;
  uses za.experimentalai.advancedcalc.spi.AdvancedCalculationService;
}
```

## SPI Interface

```java
public interface AdvancedCalculationService {
  String address();
  JsonObject handle(Object payload);
}
```

## Healthcheck Handler

```java
HealthCheckHandler.create(vertx).register("advanced-calculator", promise -> {
  boolean allReady = ServiceLoader.load(AdvancedCalculationService.class)
    .stream().allMatch(s -> s != null);
  promise.complete(allReady ? Status.OK() : Status.KO());
});
```

## Notes

* ServiceLoader pattern allows easy plugging of future modes.
* Uses Vert.x EventBus bridge endpoints per operation type.
* All logic shaded inside JPMS boundary with full modular compliance.
* Designed to integrate seamlessly into ExperimentalAIApp shell.
* External currency providers must be declared in shaded runtime.
