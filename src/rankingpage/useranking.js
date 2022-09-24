import { useEffect, useState } from "react";
import "./lanking.css";
import axios from "axios";
import Table from "react-bootstrap/Table";

function RanKingDish() {
  const [toDish, setToDish] = useState([]);
  // 레시피순 페이지

  useEffect(() => {
    axios.get(" http://3.38.19.221:8081/api/topDish").then((response) => {
      setToDish(response.data);
    });
  }, []);
  console.log({ toDish });

  return (
    <div className="rankings">
      <h2>베스트 레시피</h2>
      <div className="lankinglayout">
        <div>
          <div className="circle1">
            <img className="imgfile1" src="image/신라면.png" />
          </div>
          <div className="circle2">
            <img className="imgfile2" src="image/열라면.jpg" />
          </div>
          <div className="circle3">
            <img className="imgfile3" src="image/진라면.jpg" />
          </div>
        </div>
      </div>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>유저번호</th>
            <th>음식이름</th>
            <th>좋아요</th>
            <th>조회수</th>
            <th>먹은수</th>
          </tr>
        </thead>
        <tbody>
          {toDish.map((bestDish) => (
            <tr key={bestDish.RCP_SEQ}>
              <th className="th1">{bestDish.RCP_SEQ}</th>
              <th className="th1">{bestDish.RCP_NM}</th>
              <th className="th1">{bestDish.WRITER}</th>
              <th className="th1">{bestDish.rank_dish}</th>
              <th className="th1">{bestDish.commCount}</th>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

export default RanKingDish;
