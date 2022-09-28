import { useEffect, useState } from "react";
import "./lanking.css";
import axios from "axios";
import Table from "react-bootstrap/Table";

function RanKingPage() {
  const [toUsers, setToUsers] = useState([]);
  // 좋아요 순 페이지

  useEffect(() => {
    axios.get(" http://3.38.19.221:8081/api/topUser").then((response) => {
      setToUsers(response.data);
    });
  }, []);
  console.log({ toUsers });

  return (
    <div className="rankings">
      <h2>유저 랭킹</h2>
      <div className="wrapper">
        <div>
          <div className="circle1">
            <img className="imgfile1" src="../../../image/황금올리브치킨.jpg" />
          </div>
          <div className="line111">
            <div className="circle2">
              <img className="imgfile2" src="../../../image/스모크치킨.jpg" />
            </div>
            <div className="circle3">
              <img className="imgfile3" src="../../../image/국밥.jpg" />
            </div>
          </div>
        </div>
      </div>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>순위</th>
            <th>아이디</th>
            <th>좋아요</th>
            <th>조회수</th>
            <th>먹은수</th>
            <th>댓글수</th>
          </tr>
        </thead>
        <tbody>
          {toUsers.map((bestuser, index) => (
            <tr key={bestuser.mnum}>
              <th className="th1">{index + 1}</th>
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
