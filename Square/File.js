
import React from "react";
import ReactDOM from "react-dom";

export default function File() {
  const handleCard = (e) => {
    let target = e.target;
    if (target.classList.contains("mouse-over-flip")) {
      target.classList.remove("mouse-over-flip");
    } else {
      target.classList.add("mouse-over-flip");
    }
  };
  return (
    <div className="App">
      <div className="card" onClick={handleCard}></div>
    </div>
  );
}