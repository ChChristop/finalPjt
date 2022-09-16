import React from "react";
import "./Flip.css";
import FlipImg from './FlipImg';
import Write from "./Write";

export default function Flip() {
  return (
    <div className="App">
      <Card />
    </div>
  );
}

function Card() {
  return (
    <div className="card">
      <div className="card-inner">
        <div className="card-inner-front"><FlipImg/></div>
        <div className="card-inner-back"> <Write/></div>
      </div>
    </div>
  );
}