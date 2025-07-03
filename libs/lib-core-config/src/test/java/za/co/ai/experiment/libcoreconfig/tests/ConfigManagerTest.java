package za.co.ai.experiment.libcoreconfig.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import za.co.ai.experiment.libcoreconfig.ConfigManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ConfigManager class.
 */
public class ConfigManagerTest {
    
    @Test
    @DisplayName("Default constructor should create empty config")
    void testDefaultConstructor() {
        ConfigManager configManager = new ConfigManager();
        assertTrue(configManager.getAll().isEmpty(), "Config should be empty");
    }
    
    @Test
    @DisplayName("Constructor with initial values should store values")
    void testConstructorWithInitialValues() {
        Map<String, String> initialValues = Map.of(
                "key1", "value1",
                "key2", "value2"
        );
        
        ConfigManager configManager = new ConfigManager(initialValues);
        
        assertEquals(2, configManager.getAll().size(), "Config should have 2 entries");
        assertEquals("value1", configManager.getOrDefault("key1", null), "key1 should have value1");
        assertEquals("value2", configManager.getOrDefault("key2", null), "key2 should have value2");
    }
    
    @Test
    @DisplayName("Constructor with null initial values should create empty config")
    void testConstructorWithNullInitialValues() {
        ConfigManager configManager = new ConfigManager(null);
        assertTrue(configManager.getAll().isEmpty(), "Config should be empty");
    }
    
    @Test
    @DisplayName("get should return Optional.empty for missing key")
    void testGetMissingKey() {
        ConfigManager configManager = new ConfigManager();
        Optional<String> value = configManager.get("missing");
        assertTrue(value.isEmpty(), "Value should be empty for missing key");
    }
    
    @Test
    @DisplayName("get should return Optional with value for existing key")
    void testGetExistingKey() {
        ConfigManager configManager = new ConfigManager();
        configManager.set("key", "value");
        
        Optional<String> value = configManager.get("key");
        
        assertTrue(value.isPresent(), "Value should be present for existing key");
        assertEquals("value", value.get(), "Value should match");
    }
    
    @Test
    @DisplayName("getOrDefault should return default value for missing key")
    void testGetOrDefaultMissingKey() {
        ConfigManager configManager = new ConfigManager();
        String value = configManager.getOrDefault("missing", "default");
        assertEquals("default", value, "Should return default value for missing key");
    }
    
    @Test
    @DisplayName("getOrDefault should return actual value for existing key")
    void testGetOrDefaultExistingKey() {
        ConfigManager configManager = new ConfigManager();
        configManager.set("key", "value");
        
        String value = configManager.getOrDefault("key", "default");
        
        assertEquals("value", value, "Should return actual value for existing key");
    }
    
    @Test
    @DisplayName("set should store value")
    void testSet() {
        ConfigManager configManager = new ConfigManager();
        configManager.set("key", "value");
        
        assertEquals("value", configManager.getOrDefault("key", null), "Value should be stored");
    }
    
    @Test
    @DisplayName("set should overwrite existing value")
    void testSetOverwrite() {
        ConfigManager configManager = new ConfigManager();
        configManager.set("key", "value1");
        configManager.set("key", "value2");
        
        assertEquals("value2", configManager.getOrDefault("key", null), "Value should be overwritten");
    }
    
    @Test
    @DisplayName("clear should remove all values")
    void testClear() {
        ConfigManager configManager = new ConfigManager(Map.of("key", "value"));
        assertFalse(configManager.getAll().isEmpty(), "Config should not be empty before clear");
        
        configManager.clear();
        
        assertTrue(configManager.getAll().isEmpty(), "Config should be empty after clear");
    }
}