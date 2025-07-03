# Use the smallest secure base image
FROM alpine:latest

# Install necessary dependencies for running JLink applications
RUN apk add --no-cache libc6-compat libstdc++ zlib

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
