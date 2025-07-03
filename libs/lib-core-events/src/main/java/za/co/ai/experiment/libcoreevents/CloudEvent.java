package za.co.ai.experiment.libcoreevents;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * CloudEvent implementation based on the CloudEvents specification.
 * 
 * This class represents a cloud event that can be serialized and deserialized
 * for event-driven architectures.
 */
public class CloudEvent {
    
    // Required attributes
    private final String id;
    private final String source;
    private final String type;
    private final OffsetDateTime time;
    
    // Optional attributes
    private final String subject;
    private final String dataContentType;
    private final String dataSchema;
    private final Object data;
    
    // Extension attributes
    private final Map<String, Object> extensions;
    
    /**
     * Creates a new CloudEvent builder.
     * 
     * @return A new CloudEvent builder
     */
    public static Builder builder() {
        return new Builder();
    }
    
    private CloudEvent(Builder builder) {
        this.id = builder.id;
        this.source = builder.source;
        this.type = builder.type;
        this.time = builder.time;
        this.subject = builder.subject;
        this.dataContentType = builder.dataContentType;
        this.dataSchema = builder.dataSchema;
        this.data = builder.data;
        this.extensions = Map.copyOf(builder.extensions);
    }
    
    /**
     * Gets the event ID.
     * 
     * @return The event ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the event source.
     * 
     * @return The event source
     */
    public String getSource() {
        return source;
    }
    
    /**
     * Gets the event type.
     * 
     * @return The event type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Gets the event time.
     * 
     * @return The event time
     */
    public OffsetDateTime getTime() {
        return time;
    }
    
    /**
     * Gets the event subject.
     * 
     * @return The event subject
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Gets the data content type.
     * 
     * @return The data content type
     */
    public String getDataContentType() {
        return dataContentType;
    }
    
    /**
     * Gets the data schema.
     * 
     * @return The data schema
     */
    public String getDataSchema() {
        return dataSchema;
    }
    
    /**
     * Gets the event data.
     * 
     * @return The event data
     */
    public Object getData() {
        return data;
    }
    
    /**
     * Gets the extension attributes.
     * 
     * @return The extension attributes
     */
    public Map<String, Object> getExtensions() {
        return extensions;
    }
    
    /**
     * Builder for CloudEvent.
     */
    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String source;
        private String type;
        private OffsetDateTime time = OffsetDateTime.now();
        private String subject;
        private String dataContentType;
        private String dataSchema;
        private Object data;
        private final Map<String, Object> extensions = new HashMap<>();
        
        private Builder() {
        }
        
        /**
         * Sets the event ID.
         * 
         * @param id The event ID
         * @return This builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        
        /**
         * Sets the event source.
         * 
         * @param source The event source
         * @return This builder
         */
        public Builder source(String source) {
            this.source = source;
            return this;
        }
        
        /**
         * Sets the event type.
         * 
         * @param type The event type
         * @return This builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        
        /**
         * Sets the event time.
         * 
         * @param time The event time
         * @return This builder
         */
        public Builder time(OffsetDateTime time) {
            this.time = time;
            return this;
        }
        
        /**
         * Sets the event subject.
         * 
         * @param subject The event subject
         * @return This builder
         */
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }
        
        /**
         * Sets the data content type.
         * 
         * @param dataContentType The data content type
         * @return This builder
         */
        public Builder dataContentType(String dataContentType) {
            this.dataContentType = dataContentType;
            return this;
        }
        
        /**
         * Sets the data schema.
         * 
         * @param dataSchema The data schema
         * @return This builder
         */
        public Builder dataSchema(String dataSchema) {
            this.dataSchema = dataSchema;
            return this;
        }
        
        /**
         * Sets the event data.
         * 
         * @param data The event data
         * @return This builder
         */
        public Builder data(Object data) {
            this.data = data;
            return this;
        }
        
        /**
         * Adds an extension attribute.
         * 
         * @param name The extension name
         * @param value The extension value
         * @return This builder
         */
        public Builder extension(String name, Object value) {
            this.extensions.put(name, value);
            return this;
        }
        
        /**
         * Builds the CloudEvent.
         * 
         * @return The built CloudEvent
         * @throws IllegalStateException if required attributes are missing
         */
        public CloudEvent build() {
            if (source == null || source.isBlank()) {
                throw new IllegalStateException("Source is required");
            }
            if (type == null || type.isBlank()) {
                throw new IllegalStateException("Type is required");
            }
            return new CloudEvent(this);
        }
    }
}