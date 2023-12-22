import { Carousel } from "./components/Carousel";
import { ExploreTopBooks } from "./components/ExploreTopBooks";
import { Heros } from "./components/Heros";
import { LibrayServices } from "./components/LibaryServices";

export const HomePage = () => {
  return (
    <>
      {/* Return each component as a single element without being in a <div> tag */}

      <ExploreTopBooks />
      <Carousel />
      <Heros />
      <LibrayServices />
    </>
  );
};
