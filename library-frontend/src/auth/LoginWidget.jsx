import { useOktaAuth } from "@okta/okta-react";
import { Redirect } from "react-router-dom";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";
import { OktaSignInWidget } from "./OktaSigninWidget";

export const LoginWidget = ({ config }) => {
  const { oktaAuth, authState } = useOktaAuth();
  const onSuccess = (tokens) => {
    oktaAuth.handleLoginRedirect(tokens);
  };

  const onError = (err) => {
    console.log(`Sign in error: ${err}`);
  };

  if (!authState) {
    return <SpinnerLoading />;
  }

  return authState.isAuthenticated ? (
    <Redirect to={{ pathname: "/" }} />
  ) : (
    <OktaSignInWidget config={config} onSuccess={onSuccess} onError={onError} />
  );
};
