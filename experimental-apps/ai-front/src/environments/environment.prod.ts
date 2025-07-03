export const environment = {
  production: true,
  auth: {
    host: '${AUTH_HOST}',
    clientId: '${OAUTH_CLIENT_ID}',
    redirectUri: '${OAUTH_REDIRECT_URI}'
  },
  enabledModules: '${ENABLED_MODULES}'.split(',').filter(Boolean)
};
