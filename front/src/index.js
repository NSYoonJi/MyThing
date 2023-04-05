import React from "react";
import ReactDOM from "react-dom/client";
import { RecoilRoot } from "recoil";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RecoilRoot>
      <div id="backdrop-root"></div>
      <div id="overlay-root"></div>
      <App />
    </RecoilRoot>
  </React.StrictMode>
);
