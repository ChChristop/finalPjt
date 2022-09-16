import Customer from "../component/Customer";
import { useEffect, useState } from "react";
import axios from "axios";
import "./admin.css";
import Table from "react-bootstrap/Table";

function Board_Page() {
  // axiox의 값을 받아오는 메서드-----------------------------------------
  const [boards, setBoards] = useState([]);

  useEffect(() => {
    axios.get("http://192.168.0.23:8080/api/dish/get").then((response) => {
      setBoards(response.data);
    });
  }, []);
  console.log({ boards });
  //------------------------------------------------------------------------

  return (
    <div className="rmfwkrtjd">
      <h2>게시판 관리</h2>
      <Table striped bordered hover className="Table">
        <thead>
          <tr>
            <th>번호</th>
            <th>사진</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성일</th>
            <th>수 정 / 삭 제 </th>
          </tr>
        </thead>
        <tbody>
          {boards.map((board) => (
            <tr key={board.dish_num}>
              <th className="th1">{board.dish_num}</th>
              <th className="th1">
                <img src={board.mainIMG} width="100px"></img>
              </th>
              <th className="th1">{board.dish_name}</th>
              <th className="th1">{board.writer}</th>
              <th className="th1">{board.hit}</th>
              <th className="th1">{board.date}</th>
              <th className="th1">
                <button>🛠️</button>
                <button>❌</button>
              </th>
            </tr>
          ))}
          ;
        </tbody>
      </Table>
    </div>
  );
}
export default Board_Page;

/*
<Customer boards={boards} />

if (window.confirm("게시물을 삭제하시겠습니까?")) {
      alert("삭제되었습니다");
    } else {
      alert("취소되었습니다");
    }
*/
