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
      <h2>레시피 랭킹</h2>
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
            <th>음식이름</th>
            <th>좋아요</th>
            <th>조회수</th>
            <th>먹은수</th>
          </tr>
        </thead>
        <tbody>
          {toDish.map((bestDish, index) => (
            <tr key={bestDish.RCP_SEQ}>
              <th className="th1">{index + 1}</th>
              <th className="th1">{bestDish.RCP_NM}</th>
              <th className="th1">{bestDish.DISH_LIKE}</th>
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
