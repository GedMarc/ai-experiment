package za.co.ai.experiment.ai.app.tests;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import za.co.ai.experiment.ai.app.Main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the Main class.
 */
public class MainTest {
    private static Vertx vertx;
    private static HttpClient client;
    private static final int TEST_PORT = 8080;

    @BeforeAll
    public static void setUp() {
        // Start the Main application in a separate thread
        Thread appThread = new Thread(() -> {
            Main.main(new String[]{});
        });
        appThread.setDaemon(true);
        appThread.start();

        // Give the server some time to start
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create Vertx instance and HTTP client for testing
        vertx = Vertx.vertx();
        client = vertx.createHttpClient(new HttpClientOptions()
                .setDefaultPort(TEST_PORT)
                .setDefaultHost("localhost"));
    }

    @AfterAll
    public static void tearDown() {
        if (vertx != null) {
            vertx.close();
        }
    }

    /**
     * Test that the server responds to the root path.
     */
    @Test
    @Timeout(10)
    public void testRootEndpoint() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        final Buffer[] responseBuffer = new Buffer[1];

        client.request(HttpMethod.GET, "/")
                .compose(request -> request.send())
                .compose(response -> {
                    assertThat(response.statusCode()).isEqualTo(200);
                    assertThat(response.getHeader("content-type")).isEqualTo("application/json");
                    return response.body();
                })
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        responseBuffer[0] = ar.result();
                        latch.countDown();
                    } else {
                        ar.cause().printStackTrace();
                        latch.countDown();
                    }
                });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        JsonObject json = new JsonObject(responseBuffer[0]);
        assertThat(json.getString("message")).isEqualTo("Welcome to AI Application");
        assertThat(json.getString("version")).isEqualTo("1.0.0");
    }

    /**
     * Test that the health endpoint returns UP status.
     */
    @Test
    @Timeout(10)
    public void testHealthEndpoint() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        final Buffer[] responseBuffer = new Buffer[1];

        client.request(HttpMethod.GET, "/health")
                .compose(request -> request.send())
                .compose(response -> {
                    assertThat(response.statusCode()).isEqualTo(200);
                    assertThat(response.getHeader("content-type")).isEqualTo("application/json");
                    return response.body();
                })
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        responseBuffer[0] = ar.result();
                        latch.countDown();
                    } else {
                        ar.cause().printStackTrace();
                        latch.countDown();
                    }
                });

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();

        JsonObject json = new JsonObject(responseBuffer[0]);
        assertThat(json.getString("status")).isEqualTo("UP");
    }
}
