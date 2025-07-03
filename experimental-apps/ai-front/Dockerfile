# Use Node.js as the base image for building
FROM node:20 as build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Use Nginx as the base image for serving
FROM nginx:alpine

# Copy the build output to replace the default nginx contents
COPY --from=build /app/dist/ai-front/browser /usr/share/nginx/html

# Copy custom nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Environment variables will be replaced at runtime
ENV AUTH_HOST=https://auth.gedmarc.co.za \
    OAUTH_CLIENT_ID=ai-front \
    OAUTH_REDIRECT_URI=https://ai-front.run.app \
    PORT=8080

# Expose the port
EXPOSE ${PORT}

# Use a script to replace environment variables in the built files at runtime
COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

# Set the entrypoint
ENTRYPOINT ["/docker-entrypoint.sh"]

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
