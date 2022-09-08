import React from "react";

const Customer = ({ boards }) =>
  boards.map((board) => (
    <tr key={board.dish_num}>
      <th className="th1">{board.dish_num}</th>
      <th className="th1">
        <img src={board.mainIMG} width="100px"></img>
      </th>
      <th className="th1">{board.dish_name}</th>
      <th className="th1">{board.writer}</th>
      <th className="th1">{board.date}</th>
    </tr>
  ));
export default Customer;
