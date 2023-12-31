import { OktaAuth, toRelativeUrl } from "@okta/okta-auth-js";
import { LoginCallback, SecureRoute, Security } from "@okta/okta-react";
import { Redirect, Route, Switch, useHistory } from "react-router-dom";
import "./App.css";
import LoginWidget from "./Auth/LoginWidget";
import { BookCheckoutPage } from "./layouts/BookCheckoutPage/BookCheckoutPage";
import { ReviewListPage } from "./layouts/BookCheckoutPage/components/ReviewListPage";
import { HomePage } from "./layouts/HomePage/HomePage";
import { ManageLibraryPage } from "./layouts/ManageLibraryPage/ManageLibraryPage";
import { MessagePage } from "./layouts/MessagesPage/MessagePage";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { SearchBookPage } from "./layouts/SearchBookPage/SearchBookPage";
import { ShelfPage } from "./layouts/ShelfPage/ShelfPage";
import { oktaConfig } from "./lib/oktaConfig";
import { PaymentPage } from "./layouts/PaymentPage/PaymentPage";

const oktaAuth = new OktaAuth(oktaConfig);

export const App = () => {
  const customAuthHandler = () => {
    history.push("/login");
  };

  const history = useHistory();

  const restoreOriginalUri = async (_oktaAuth: any, originalUri: any) => {
    history.replace(toRelativeUrl(originalUri || "/", window.location.origin));
  };

  return (
    <div className="d-flex flex-column min-vh-100">
      <Security
        oktaAuth={oktaAuth}
        restoreOriginalUri={restoreOriginalUri}
        onAuthRequired={customAuthHandler}
      >
        <Navbar />
        <div className="flex-grow-1">
          <Switch>
            {/* exact so that the / would not affect other paths */}
            <Route path="/" exact>
              <Redirect to="/home" />
            </Route>
            <Route path="/home">
              <HomePage />
            </Route>
            <Route path="/search">
              <SearchBookPage />
            </Route>
            <Route path="/reviewlist/:bookid">
              <ReviewListPage />
            </Route>
            <Route path="/checkout/:bookid">
              <BookCheckoutPage />
            </Route>
            <Route
              path="/login"
              render={() => <LoginWidget config={oktaConfig} />}
            ></Route>
            <Route path="/login/callback" component={LoginCallback}></Route>
            <SecureRoute path="/shelf">
              <ShelfPage />
            </SecureRoute>
            <SecureRoute path="/messages">
              <MessagePage />
            </SecureRoute>
            <SecureRoute path="/admin">
              <ManageLibraryPage />
            </SecureRoute>
            <SecureRoute path="/fees">
              <PaymentPage />
            </SecureRoute>
          </Switch>
        </div>
        <Footer />
      </Security>
    </div>
  );
};
