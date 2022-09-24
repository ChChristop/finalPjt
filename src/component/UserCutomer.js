import React from "react";

const UserCustomer = ({ Users = [] }) =>
  Users.map((item) => (
    <tr key={item.mum}>
      <th className="th1">{item.mum}</th>
      <th className="th1">{item.memberID}</th>
      <th className="th1">{item.Email}</th>
      <th className="th1">{item.nickname}</th>
      <th className="th1">{item.phoneNumber}</th>
      <th className="th1">{item.Point}</th>
      <th className="th1">{item.date}</th>
    </tr>
  ));
export default UserCustomer;
