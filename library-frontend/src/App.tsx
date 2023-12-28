import { Route } from "react-router-dom";
import "./App.css";
import { HomePage } from "./layouts/HomePage/HomePage";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { SearchBookPage } from "./layouts/SearchBookPage/SearchBookPage";

export const App = () => {
  return (
    <div>
      <Navbar />
      <Route path="/">
        <HomePage />
      </Route>
      <Route path="/search">
        <SearchBookPage />
      </Route>
      <Footer />
    </div>
  );
};
