/**
 * Core Observability Module
 * 
 * This module provides GCP-native log/trace integrations for the AI Experiment platform.
 */
module za.co.ai.experiment.libcoreobservability {
    requires java.logging;
    // Export the API packages
    exports za.co.ai.experiment.libcoreobservability;
    
    // For testing
    opens za.co.ai.experiment.libcoreobservability to org.junit.platform.commons;
}