# Use the smallest secure base image
FROM alpine:latest

# Install necessary dependencies for running JLink applications
RUN apk add --no-cache libc6-compat libstdc++ zlib

# Set working directory
WORKDIR /app

# Copy and extract the JLink zip file
COPY target/maven-jlink/*.zip /tmp/jlink-runtime.zip
RUN unzip /tmp/jlink-runtime.zip -d /app && \
    rm /tmp/jlink-runtime.zip && \
    chmod +x /app/bin/* && \
    find /app -type f -name "*.so" -exec chmod +x {} \; && \
    find /app -type f -name "java" -exec chmod +x {} \;

# Environment variables
ENV APP_HOME=/app \
    JAVA_OPTS="" \
    PORT=8080

# Expose the port
EXPOSE ${PORT}

# Set the entrypoint
ENTRYPOINT ["/app/bin/basic-calculator"]
