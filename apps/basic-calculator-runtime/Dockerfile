# Use the JLink image as the base
FROM eclipse-temurin:24-jre-alpine AS runtime

# Set working directory
WORKDIR /app

# Copy the JLink image
COPY target/jlink-image /app/jlink-image

# Environment variables
ENV APP_HOME=/app \
    JAVA_OPTS="" \
    PORT=8080

# Expose the port
EXPOSE ${PORT}

# Set the entrypoint
ENTRYPOINT ["/app/jlink-image/bin/basic-calculator"]