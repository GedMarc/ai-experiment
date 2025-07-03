package za.co.ai.experiment.libcoreevents.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import za.co.ai.experiment.libcoreevents.CloudEvent;

import java.time.OffsetDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CloudEvent class.
 */
public class CloudEventTest {

    private static final String SOURCE = "https://example.com/events";
    private static final String TYPE = "com.example.event.created";
    private static final String SUBJECT = "resource123";
    private static final String DATA_CONTENT_TYPE = "application/json";

    @Test
    @DisplayName("Builder should create CloudEvent with required attributes")
    void testBuilderWithRequiredAttributes() {
        CloudEvent event = CloudEvent.builder()
                .source(SOURCE)
                .type(TYPE)
                .build();

        assertNotNull(event.getId(), "ID should be auto-generated");
        assertEquals(SOURCE, event.getSource(), "Source should match");
        assertEquals(TYPE, event.getType(), "Type should match");
        assertNotNull(event.getTime(), "Time should be auto-generated");
    }

    @Test
    @DisplayName("Builder should create CloudEvent with all attributes")
    void testBuilderWithAllAttributes() {
        String id = "test-id-123";
        OffsetDateTime time = OffsetDateTime.now().minusHours(1);
        String dataSchema = "https://example.com/schemas/event";
        Object data = Map.of("key", "value");

        CloudEvent event = CloudEvent.builder()
                .id(id)
                .source(SOURCE)
                .type(TYPE)
                .time(time)
                .subject(SUBJECT)
                .dataContentType(DATA_CONTENT_TYPE)
                .dataSchema(dataSchema)
                .data(data)
                .extension("traceid", "abc123")
                .build();

        assertEquals(id, event.getId(), "ID should match");
        assertEquals(SOURCE, event.getSource(), "Source should match");
        assertEquals(TYPE, event.getType(), "Type should match");
        assertEquals(time, event.getTime(), "Time should match");
        assertEquals(SUBJECT, event.getSubject(), "Subject should match");
        assertEquals(DATA_CONTENT_TYPE, event.getDataContentType(), "Data content type should match");
        assertEquals(dataSchema, event.getDataSchema(), "Data schema should match");
        assertEquals(data, event.getData(), "Data should match");
        assertEquals("abc123", event.getExtensions().get("traceid"), "Extension should match");
    }

    @Test
    @DisplayName("Builder should throw exception when source is missing")
    void testBuilderWithMissingSource() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
                CloudEvent.builder()
                        .type(TYPE)
                        .build());

        assertEquals("Source is required", exception.getMessage());
    }

    @Test
    @DisplayName("Builder should throw exception when type is missing")
    void testBuilderWithMissingType() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
                CloudEvent.builder()
                        .source(SOURCE)
                        .build());

        assertEquals("Type is required", exception.getMessage());
    }
}
