import React from "react";
import Cardflip from "./Cardflip";
import Search from "../Main/Search";

function Square({ realData }) {
  return (
    <>
      <div>
        <h2 className="my1">레시피 자랑해주세요(가제)</h2>
        <Search />
      </div>
      <div>
        <div className="inline">
          <Cardflip realData={realData} />
        </div>
      </div>
    </>
  );
}
export default Square;
