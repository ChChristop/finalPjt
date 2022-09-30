import React from "react";
import Cardflip from "./Cardflip";
import Search from "../Main/Search";

function Square({ realData }) {
  return (
    <div>
      <Search />
      <div className="Cardflip">
        <Cardflip realData={realData} />
      </div>
    </div>
  );
}
export default Square;
