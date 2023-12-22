import "./App.css";
import { Carousel } from "./layouts/HomePage/Carousel";
import { ExploreTopBooks } from "./layouts/HomePage/ExploreTopBooks";
import { Footer } from "./layouts/HomePage/Footer";
import { Heros } from "./layouts/HomePage/Heros";
import { LibrayServices } from "./layouts/HomePage/LibaryServices";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";

function App() {
  return (
    <div>
      <Navbar />
      <ExploreTopBooks />
      <Carousel />
      <Heros />
      <LibrayServices />
      <Footer />
    </div>
  );
}

export default App;
