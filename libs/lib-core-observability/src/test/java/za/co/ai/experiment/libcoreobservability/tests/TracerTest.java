package za.co.ai.experiment.libcoreobservability.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import za.co.ai.experiment.libcoreobservability.Tracer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Tracer class.
 */
public class TracerTest {
    
    private static final String SERVICE_NAME = "test-service";
    
    @Test
    @DisplayName("Constructor should accept valid service name")
    void testConstructorWithValidServiceName() {
        Tracer tracer = new Tracer(SERVICE_NAME, true, true);
        assertEquals(SERVICE_NAME, tracer.getServiceName(), "Service name should be stored correctly");
        assertTrue(tracer.isTracingEnabled(), "Tracing should be enabled");
        assertTrue(tracer.isGcpTraceExport(), "GCP trace export should be enabled");
    }
    
    @Test
    @DisplayName("Constructor should use default service name for null")
    void testConstructorWithNullServiceName() {
        Tracer tracer = new Tracer(null, true, true);
        assertEquals("unknown-service", tracer.getServiceName(), "Default service name should be used");
    }
    
    @Test
    @DisplayName("Constructor should use default service name for blank")
    void testConstructorWithBlankServiceName() {
        Tracer tracer = new Tracer("", true, true);
        assertEquals("unknown-service", tracer.getServiceName(), "Default service name should be used");
    }
    
    @Test
    @DisplayName("createSpan should return non-empty ID when tracing is enabled")
    void testCreateSpanWithTracingEnabled() {
        Tracer tracer = new Tracer(SERVICE_NAME, true, false);
        String spanId = tracer.createSpan("test-span");
        assertNotNull(spanId, "Span ID should not be null");
        assertFalse(spanId.isEmpty(), "Span ID should not be empty");
    }
    
    @Test
    @DisplayName("createSpan should return empty ID when tracing is disabled")
    void testCreateSpanWithTracingDisabled() {
        Tracer tracer = new Tracer(SERVICE_NAME, false, false);
        String spanId = tracer.createSpan("test-span");
        assertEquals("", spanId, "Span ID should be empty when tracing is disabled");
    }
    
    @Test
    @DisplayName("endSpan should not throw exception")
    void testEndSpan() {
        Tracer tracer = new Tracer(SERVICE_NAME, true, true);
        String spanId = tracer.createSpan("test-span");
        
        // This should not throw an exception
        assertDoesNotThrow(() -> tracer.endSpan(spanId), "endSpan should not throw an exception");
    }
    
    @Test
    @DisplayName("addAttribute should not throw exception")
    void testAddAttribute() {
        Tracer tracer = new Tracer(SERVICE_NAME, true, true);
        String spanId = tracer.createSpan("test-span");
        
        // This should not throw an exception
        assertDoesNotThrow(() -> tracer.addAttribute(spanId, "key", "value"), 
                "addAttribute should not throw an exception");
    }
    
    @Test
    @DisplayName("recordException should not throw exception")
    void testRecordException() {
        Tracer tracer = new Tracer(SERVICE_NAME, true, true);
        String spanId = tracer.createSpan("test-span");
        Exception exception = new RuntimeException("Test exception");
        
        // This should not throw an exception
        assertDoesNotThrow(() -> tracer.recordException(spanId, exception), 
                "recordException should not throw an exception");
    }
}