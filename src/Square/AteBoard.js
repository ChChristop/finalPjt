import axios from "axios";
import Button from "react-bootstrap/Button";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "./Board.css";

function NewBoard() {
  const { state } = useLocation();

  const [mainIMG, setmainIMG] = useState(state ? state.mainIMG : "");
  const [recipe1, setrecipe1] = useState(state ? state.recipe1 : "");

  const onUploadmainIMG = (e) => {
    console.log(e.target.files);
    setmainIMG(e.target.files[0]);
  };

  const onUploadrecipe1 = (e) => {
    console.log(e.target.value);
    setrecipe1(e.target.value);
  };
  //-----------------------------------

  const mbcc = async () => {
    const frm = new FormData();

    frm.append("file", mainIMG);
    frm.append("ate_content", recipe1);

    axios
      .post("http://192.168.0.23:8080/api/ate/add/1001/1", frm, {
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
      <h2 className="mukwrite">먹음 리뷰 작성</h2>
      {/* <div className="boardlaout"> */}
      <p className="picture">
        📸리뷰 사진 :
        <input type="file" accept="image/*" onChange={onUploadmainIMG}></input>
      </p>
      <p>
        <div className="text">
          <input
            className="board_text"
            type="text"
            placeholder="먹음 후기 입력"
            value={recipe1}
            onChange={onUploadrecipe1}
          ></input>
        </div>
      </p>

      <p className="push">
        <Button className="Link" onClick={mbcc} variant="link">
          등록
        </Button>
      </p>
    </div>
  );
}

export default NewBoard;
