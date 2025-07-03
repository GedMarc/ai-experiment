/**
 * AI Application Module
 * 
 * This module provides an AI application for the AI Experiment platform.
 */
module za.co.ai.experiment.ai.app {
    // Export the API packages
    exports za.co.ai.experiment.ai.app;

    // Required dependencies
    requires za.co.ai.experiment.libcoreauth;
    requires za.co.ai.experiment.libcoreconfig;
    requires za.co.ai.experiment.libcoreobservability;

    // Required modules as per specification
    requires java.logging;
    requires io.vertx.core;
    // Jakarta Inject is required but handled through classpath
    // requires jakarta.inject;

    // For JSON processing
    requires com.fasterxml.jackson.databind;

    // For logging
    requires org.apache.logging.log4j;

    // For mapping
    requires org.mapstruct;

    // For Lombok
    requires static lombok;

    // For testing
    opens za.co.ai.experiment.ai.app to org.junit.platform.commons;
}
