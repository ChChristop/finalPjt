import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Nav, Button, Tab, Tabs, Form } from "react-bootstrap";
import { useState, useEffect } from "react";
import axios from "axios";
// import EditCom from './EditCom';
import LikeButton from "./LikeButton";
import "./Board.css";
import InputGroup from "react-bootstrap/InputGroup";

function Comment() {
  const { state } = useLocation();
  const [recipe1, setrecipe1] = useState(state ? state.recipe1 : "");

  const onUploadrecipe1 = (e) => {
    console.log(e.target.value);
    setrecipe1(e.target.value);
  };
  //-----------------------------------

  const mbcc = async () => {
    const frm = new FormData();

    frm.append("ate_content", recipe1);

    axios
      .get("http://3.38.19.221:8081/api/ate/get/70/5", frm, {
        headers: {
          "Content-type": "multipart/form-data",
        },
      })
      .then((response) => {
        //document.location.href = "/admin-Page/NewBoard/";
        console.log("게시물 등록완료!", response);
        alert("등록완료");
      })
      .catch((error) => {
        console.log("An error occurred:", error);
      });
  };

  return (
    <div>
      <h2 className="mukwritecomment"></h2>
      <div className="textcomment">
        <div className="inputcomment">
          <Button onClick={mbcc} variant="outline-dark">
            등록
          </Button>
          <Button variant="outline-dark">수정</Button>
          <Button variant="outline-dark">삭제</Button>
        </div>
      </div>
    </div>
  );
}
export default Comment;
