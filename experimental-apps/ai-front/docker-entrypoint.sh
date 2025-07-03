#!/bin/sh

# Replace environment variables in the built files
find /usr/share/nginx/html -type f -name "*.js" -exec sed -i "s|\${AUTH_HOST}|$AUTH_HOST|g" {} \;
find /usr/share/nginx/html -type f -name "*.js" -exec sed -i "s|\${OAUTH_CLIENT_ID}|$OAUTH_CLIENT_ID|g" {} \;
find /usr/share/nginx/html -type f -name "*.js" -exec sed -i "s|\${OAUTH_REDIRECT_URI}|$OAUTH_REDIRECT_URI|g" {} \;

# Configure nginx to listen on the PORT environment variable
sed -i.bak "s|listen\s*80;|listen $PORT;|g" /etc/nginx/conf.d/default.conf

# Start nginx
exec "$@"