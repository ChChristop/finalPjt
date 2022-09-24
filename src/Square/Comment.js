import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Nav, Button, Tab, Tabs, Form} from 'react-bootstrap';
import {useState, useEffect} from 'react';
import axios from 'axios';
// import EditCom from './EditCom';
import LikeButton from './LikeButton';
import "./Board.css";
import InputGroup from 'react-bootstrap/InputGroup';

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
      .get("http://192.168.0.23:8080/api/ate/get/70/5", frm, {
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
      {/* <div className="boardlaout"> */}
      <div className="textcomment">
        {/* <p className='inlinecommet'> */}
      <div className='inputcomment'>
      <InputGroup>
        <Form.Control
          placeholder="리뷰 등록"
          aria-label="Recipient's username with two button addons"
        />
        <Button onClick={mbcc} variant="outline-secondary">등록</Button>
        <Button variant="outline-secondary">수정</Button>
        <Button variant="outline-secondary">삭제</Button>
      </InputGroup>
        {/* </p> */}
         
      </div></div></div>
   
  );
}
       export default Comment;
