package za.co.ai.experiment.libcoreconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Configuration manager for the AI Experiment platform.
 * 
 * This class provides functionality to manage environment variables and configuration properties.
 */
public class ConfigManager {
    
    private final Map<String, String> configValues = new HashMap<>();
    
    /**
     * Creates a new configuration manager with default values.
     */
    public ConfigManager() {
        // Default constructor
    }
    
    /**
     * Creates a new configuration manager with the specified values.
     * 
     * @param initialValues The initial configuration values
     */
    public ConfigManager(Map<String, String> initialValues) {
        if (initialValues != null) {
            configValues.putAll(initialValues);
        }
    }
    
    /**
     * Gets a configuration value.
     * 
     * @param key The configuration key
     * @return The configuration value, or empty if not found
     */
    public Optional<String> get(String key) {
        return Optional.ofNullable(configValues.get(key));
    }
    
    /**
     * Gets a configuration value with a default.
     * 
     * @param key The configuration key
     * @param defaultValue The default value to return if the key is not found
     * @return The configuration value, or the default if not found
     */
    public String getOrDefault(String key, String defaultValue) {
        return configValues.getOrDefault(key, defaultValue);
    }
    
    /**
     * Sets a configuration value.
     * 
     * @param key The configuration key
     * @param value The configuration value
     */
    public void set(String key, String value) {
        configValues.put(key, value);
    }
    
    /**
     * Loads configuration from environment variables.
     * 
     * @return This configuration manager
     */
    public ConfigManager loadFromEnvironment() {
        System.getenv().forEach(configValues::put);
        return this;
    }
    
    /**
     * Loads configuration from environment variables with a prefix.
     * 
     * @param prefix The prefix to filter environment variables
     * @return This configuration manager
     */
    public ConfigManager loadFromEnvironment(String prefix) {
        System.getenv().forEach((key, value) -> {
            if (key.startsWith(prefix)) {
                configValues.put(key, value);
            }
        });
        return this;
    }
    
    /**
     * Gets all configuration values.
     * 
     * @return An unmodifiable view of the configuration values
     */
    public Map<String, String> getAll() {
        return Map.copyOf(configValues);
    }
    
    /**
     * Clears all configuration values.
     */
    public void clear() {
        configValues.clear();
    }
}