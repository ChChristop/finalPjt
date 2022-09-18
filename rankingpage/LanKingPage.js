import { useEffect, useState } from "react";
import "./lanking.css";
import axios from "axios";
import Table from "react-bootstrap/Table";

function RanKingPage() {
  const [toUsers, setToUsers] = useState([]);

  useEffect(() => {
    axios.get("http://192.168.0.10:8080/api/topUser").then((response) => {
      setToUsers(response.data);
    });
  }, []);
  console.log({ toUsers });

  return (
    <div className="rankings">
      <h2>랭킹</h2>
      <div className="wrapper">
        <div className="circle1"></div>
        <div className="circle2"></div>
        <div className="circle3"></div>
      </div>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>순위</th>
            <th>유저번호</th>
            <th>아이디</th>
            <th>좋아요</th>
            <th>조회수</th>
            <th>먹은수</th>
            <th>댓글수</th>
          </tr>
        </thead>
        <tbody>
          {toUsers.map((bestuser) => (
            <tr key={bestuser.mnum}>
              <th className="th1" start="1"></th>
              <th className="th1">{bestuser.mnum}</th>
              <th className="th1">{bestuser.nickname}</th>
              <th className="th1">{bestuser.likeCount}</th>
              <th className="th1">{bestuser.refreCount}</th>
              <th className="th1">{bestuser.comment}</th>
              <th className="th1">{bestuser.ateCount}</th>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

export default RanKingPage;

/*

<th className="th1">
                <img src={bestuser.mainIMG} width="100px"></img>
              </th>


<>
      <h2>랭킹</h2>
      <div className="lankinglayout">
        <div className="wrapper">
          <div className="circle1"></div>
          <div className="circle2"></div>
          <div className="circle3"></div>
        </div>
        <div>
          <Table striped bordered hover className="Table">
            <thead>
              <tr>
                <th>번호</th>
                <th>사진</th>
                <th>제목</th>
                <th>조회수</th>
              </tr>
            </thead>
          </Table>
        </div>
        <div className="wrapper">
          <tbody>
            {toUsers.map((bestuser) => (
              <tr key={bestuser.dish_num}>
                <th className="th1">{bestuser.dish_num}</th>
                <th className="th1">
                  <img src={toUbestusersers.mainIMG} width="100px"></img>
                </th>
                <th className="th1">{bestuser.dish_name}</th>
                <th className="th1">{bestuser.hit}</th>
              </tr>
            ))}
          </tbody>
        </div>
      </div>
    </>

*/
