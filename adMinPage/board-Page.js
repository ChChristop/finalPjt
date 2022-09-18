import { useEffect, useState } from "react";
import axios from "axios";
import "./admin.css";
import Table from "react-bootstrap/Table";
import { Link, useNavigate } from "react-router-dom";
import HorizonLine from "../component/HorizonLine";

function Board_Page() {
  // axiox의 값을 받아오는 메서드-----------------------------------------
  const [boards, setBoards] = useState([]);
  let navigate = useNavigate();

  const handleClick = (board) =>
    navigate("/admin-Page/NewBoard/", { state: board });

  const onDelete = (dish_num) => {
    axios
      .delete(`http://192.168.0.23:8080/api/dish/delete/${dish_num}`)
      .then((response) => {
        setBoards(boards.filter((item) => item.dish_num !== dish_num));
        console.log(response);
      });
  };

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
                <button onClick={() => handleClick(board)}>🛠️</button>
                <button onClick={() => onDelete(board.dish_num)}>❌</button>
              </th>
            </tr>
          ))}
          <HorizonLine></HorizonLine>
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
