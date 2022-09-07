import Table from "react-bootstrap/Table";
import UserCustomer from "../component/Customer";
import { useEffect, useState } from "react";
import axios from "axios";
import "./admin.css";

const UserList = () => {
  // axiox의 값을 받아오는 메서드-----------------------------------------
  const [Users, setUsers] = useState([]);

  //------------------------------------------------------------------------
  useEffect(() => console.log(Users), [Users]);

  useEffect(() => {
    axios
      .get("http://192.168.0.10:8080/api/member/member-list")
      .then((response) => {
        setUsers(response.data);
        console.log(response.data);
      });
  }, []);

  return (
    <div className="rmfwkrtjd">
      <h2>유저 정보</h2>
      <Table striped bordered hover className="Table">
        <thead>
          <tr>
            <th>등록번호</th>
            <th>아이디</th>
            <th>이름</th>
            <th>전화번호</th>
            <th>등록일</th>
            <th>포인트</th>
          </tr>
        </thead>
        <tbody>
          {Users.dtoList &&
            Users.dtoList.map((item) => (
              <tr key={item.mnum}>
                <th className="th1">{item.mnum}</th>
                <th className="th1">{item.memberID}</th>
                <th className="th1">{item.nickname}</th>
                <th className="th1">{item.phoneNumber}</th>
                <th className="th1">{item.date}</th>
                <th className="th1">{item.point}</th>
              </tr>
            ))}
        </tbody>
      </Table>

      <br />
    </div>
  );
};

export default UserList;
