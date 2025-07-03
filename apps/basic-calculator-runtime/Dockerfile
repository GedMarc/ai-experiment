# Use the smallest secure base image
FROM alpine:latest

# Install necessary dependencies for running JLink applications
RUN apk add --no-cache libc6-compat libstdc++ zlib unzip

# Set working directory
WORKDIR /app

# Copy and extract the JLink zip file
COPY target/*.zip /app/
RUN unzip -o /app/*.zip && rm /app/*.zip


# Environment variables
ENV APP_HOME=/app \
    JAVA_OPTS="" \
    PORT=8080

# Expose the port
EXPOSE ${PORT}

# Set the entrypoint
ENTRYPOINT ["/app/bin/basic-calculator"]
