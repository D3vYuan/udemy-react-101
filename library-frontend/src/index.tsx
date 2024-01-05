import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { App } from "./App";
import "./index.css";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

// https://stackoverflow.com/questions/56462444/process-env-api-url-is-undefined
// Adding REACT_APP_ for .env variables
const stripePublisherKey: string =
  `${process.env.REACT_APP_STRIPE_PUBLISHER_KEY}` as string;
const stripePromise = loadStripe(stripePublisherKey);

root.render(
  <BrowserRouter>
    <Elements stripe={stripePromise}>
      <App />
    </Elements>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
