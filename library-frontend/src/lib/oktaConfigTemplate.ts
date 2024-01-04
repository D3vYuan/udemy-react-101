// 1. Replace <okta-client-id> and <okta-client-user>
// 2. Rename oktaConfigTemplate to oktaConfig
export const oktaConfigTemplate = {
  clientId: "<okta-client-id>",
  issuer: "https://<okta-client-user>/oauth2/default",
  redirectUri: "https://localhost:3000/login/callback",
  scopes: ["openid", "profile", "email"],
  pkce: true,
  disableHttpsCheck: true,
};
