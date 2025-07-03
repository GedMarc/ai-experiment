package za.co.ai.experiment.ai.app;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

import java.util.logging.Level;

/**
 * Main entry point for the AI Application.
 */
@Log4j2
public class Main {
    private static Vertx vertx;
    private static HttpServer server;
    private static final int DEFAULT_PORT = 8080;

    /**
     * Application entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        log.info("Starting AI Application");

        // Initialize application components
        initializeApplication();

        // Setup shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down AI Application");
            if (vertx != null) {
                vertx.close().onComplete(ar -> {
                    log.info("Vert.x instance closed");
                });
            }
        }));

        log.info("AI Application started successfully");
    }

    /**
     * Initialize application components.
     */
    private static void initializeApplication() {
        log.info("Initializing application components");

        // Configure Vert.x options
        VertxOptions options = new VertxOptions()
                .setWorkerPoolSize(50)
                .setEventLoopPoolSize(10)
                .setPreferNativeTransport(true);

        // Create Vert.x instance
        vertx = Vertx.vertx(options);

        // Configure HTTP server
        HttpServerOptions serverOptions = new HttpServerOptions()
                .setPort(getPort())
                .setLogActivity(true);

        // Create HTTP server
        server = vertx.createHttpServer(serverOptions);

        // Configure routes
        server.requestHandler(req -> {
            if (req.path().equals("/health")) {
                req.response()
                   .putHeader("content-type", "application/json")
                   .end(new JsonObject().put("status", "UP").encode());
            } else {
                req.response()
                   .putHeader("content-type", "application/json")
                   .end(new JsonObject()
                        .put("message", "Welcome to AI Application")
                        .put("version", "1.0.0")
                        .encode());
            }
        });

        // Start server
        server.listen().onComplete(ar -> {
            if (ar.succeeded()) {
                log.info("HTTP server started on port {}", getPort());
            } else {
                log.error("Failed to start HTTP server", ar.cause());
            }
        });
    }

    /**
     * Get the port from environment variable or use default.
     * 
     * @return The port number
     */
    private static int getPort() {
        String portEnv = System.getenv("PORT");
        if (portEnv != null && !portEnv.isEmpty()) {
            try {
                return Integer.parseInt(portEnv);
            } catch (NumberFormatException e) {
                log.warn("Invalid PORT environment variable value: {}, using default: {}", portEnv, DEFAULT_PORT);
            }
        }
        return DEFAULT_PORT;
    }
}
