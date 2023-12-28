import { Redirect, Route, Switch } from "react-router-dom";
import "./App.css";
import { HomePage } from "./layouts/HomePage/HomePage";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { SearchBookPage } from "./layouts/SearchBookPage/SearchBookPage";

export const App = () => {
  return (
    <div>
      <Navbar />
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
      </Switch>
      <Footer />
    </div>
  );
};
