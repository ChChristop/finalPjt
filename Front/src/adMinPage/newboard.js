import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "./admin.css";
import { useSelector } from "react-redux";

function NewBoard() {
  const { state } = useLocation();

  const [file01, setfile01] = useState(state ? state.file01 : "");
  const [file02, setfile02] = useState(state ? state.file02 : "");
  const [file03, setfile03] = useState(state ? state.file03 : "");
  const [file04, setfile04] = useState(state ? state.file04 : "");
  const [file05, setfile05] = useState(state ? state.file05 : "");
  const [file06, setfile06] = useState(state ? state.file06 : "");
  const [foodList, setfoodList] = useState(state ? state.dish_name : "");
  const [mainname, setmainname] = useState(state ? state.mainname : "");
  const [mainIMG, setmainIMG] = useState(state ? state.mainIMG : "");
  const [recipe1, setrecipe1] = useState(state ? state.recipe1 : "");
  const [recipe2, setrecipe2] = useState(state ? state.recipe2 : "");
  const [recipe3, setrecipe3] = useState(state ? state.recipe3 : "");
  const [recipe4, setrecipe4] = useState(state ? state.recipe4 : "");
  const [recipe5, setrecipe5] = useState(state ? state.recipe5 : "");
  const [recipe6, setrecipe6] = useState(state ? state.recipe6 : "");

  const onEdit = () => {
    const frm = new FormData();
    frm.append("file01", file01);
    frm.append("file02", file02);
    frm.append("file03", file03);
    frm.append("file04", file04);
    frm.append("file05", file05);
    frm.append("file06", file06);
    frm.append("RCP_PARTS_DTLS", mainname);
    frm.append("RCP_NM", foodList);
    frm.append("mainIMG", mainIMG);
    frm.append("MANUAL01", recipe1);
    frm.append("MANUAL01", recipe2);
    frm.append("MANUAL03", recipe3);
    frm.append("MANUAL04", recipe4);
    frm.append("MANUAL05", recipe5);
    frm.append("MANUAL06", recipe6);
    // let [...mod1, setmod1] = useState([]);

    axios
      .put(`http://3.38.19.221:8081/api/dish/edit/${state.dish_num}/1`, frm)
      .then((response) => {
        //setBoards(boards.map((item) => item.));
        console.log(response);
      });
  };

  const onUploadmainIMG = (e) => {
    console.log(e.target.files);
    setmainIMG(e.target.files[0]);
  };
  const onUploadText = (e) => {
    console.log(e.target.value);
    setmainname(e.target.value);
  };
  //----------------------------------
  const onUploadImage1 = (e) => {
    console.log(e.target.files);
    setfile01(e.target.files[0]);
  };
  const onUploadrecipe1 = (e) => {
    console.log(e.target.value);
    setrecipe1(e.target.value);
  };
  //-----------------------------------

  const onUploadfood = (e) => {
    console.log(e.target.value);
    setfoodList(e.target.value);
  };
  //-----------------------------------

  const onUploadImage2 = (e) => {
    console.log(e.target.files);
    setfile02(e.target.files[0]);
  };
  const onUploadrecipe2 = (e) => {
    console.log(e.target.value);
    setrecipe2(e.target.value);
  };
  //----------------------------------

  const onUploadImage3 = (e) => {
    console.log(e.target.files);
    setfile03(e.target.files[0]);
  };
  const onUploadrecipe3 = (e) => {
    console.log(e.target.value);
    setrecipe3(e.target.value);
  };
  //----------------------------------

  const onUploadImage4 = (e) => {
    console.log(e.target.files);
    setfile04(e.target.files[0]);
  };
  const onUploadrecipe4 = (e) => {
    console.log(e.target.value);
    setrecipe4(e.target.value);
  };
  //----------------------------------

  const onUploadImage5 = (e) => {
    console.log(e.target.files);
    setfile05(e.target.files[0]);
  };
  const onUploadrecipe5 = (e) => {
    console.log(e.target.value);
    setrecipe5(e.target.value);
  };
  //----------------------------------

  const onUploadImage6 = (e) => {
    console.log(e.target.files);
    setfile06(e.target.files[0]);
  };
  const onUploadrecipe6 = (e) => {
    console.log(e.target.value);
    setrecipe6(e.target.value);
  };
  //----------------------------------
  const auths = useSelector((state) => state.auth.user);

  const mbcc = async () => {
    const frm = new FormData();
    frm.append("file01", file01);
    frm.append("file02", file02);
    frm.append("file03", file03);
    frm.append("file04", file04);
    frm.append("file05", file05);
    frm.append("file06", file06);
    frm.append("RCP_PARTS_DTLS", mainname);
    frm.append("RCP_NM", foodList);
    frm.append("mainIMG", mainIMG);
    frm.append("MANUAL01", recipe1);
    frm.append("MANUAL01", recipe2);
    frm.append("MANUAL03", recipe3);
    frm.append("MANUAL04", recipe4);
    frm.append("MANUAL05", recipe5);
    frm.append("MANUAL06", recipe6);

    axios
      .post(`http://3.38.19.221:8081/api/dish/add/33`, frm, {
        headers: {
          // authorization:
          //   "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMzMiLCJyb2xlIjoiW01FTUJFUl0iLCJtdW0iOjMwNiwiYWRtaW5DaGVjayI6ImZhbHNlIiwiZXhwIjoxNjY0MTU5ODU0LCJtZW1iZXJJRCI6IjMzMyJ9.67ffVMOyxmRyXXNkNuV_jTIRKVP9pSxtVOJ--ngokff7KAjPyBD9EBi2Nt1iY_fUq9DPz-SLevYlawWVKlGp3w",
          authorization: auths.authorization,
          refreshtoken: auths.refreshtoken,
          "Content-Type": "application/json; charset=utf-8",
        },
        responseType: "json",
        responseEncoding: "utf8",
      })
      .then((response) => {
        //document.location.href = "/admin-Page/NewBoard/";

        console.log("????????? ????????????!", response);
        alert("????????????");
      })
      .catch((error) => {
        console.log("An error occurred:", error);
      });
  };

  return (
    <div className="rmfwkrtjd">
      <h2>????????? ??????</h2>
      <div className="boardlaout">
        <p>
          ???????????? :
          <input
            type="file"
            accept="image/*"
            onChange={onUploadmainIMG}
          ></input>
        </p>
        <p>
          <input
            type="text"
            placeholder="?????? ??????"
            value={foodList}
            onChange={onUploadfood}
          ></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="?????? : ex) ??????, ??????, ..."
            value={mainname}
            onChange={onUploadText}
          ></input>
        </p>
        <p>
          ????????? ?????? 1 :
          <input type="file" accept="image/*" onChange={onUploadImage1}></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="????????? ??????"
            value={recipe1}
            onChange={onUploadrecipe1}
          ></input>
        </p>
        <p>
          ????????? ?????? 2 :
          <input type="file" accept="image/*" onChange={onUploadImage2}></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="????????? ??????"
            value={recipe2}
            onChange={onUploadrecipe2}
          ></input>
        </p>
        <p>
          ????????? ?????? 3 :
          <input type="file" accept="image/*" onChange={onUploadImage3}></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="????????? ??????"
            value={recipe3}
            onChange={onUploadrecipe3}
          ></input>
        </p>
        <p>
          ????????? ?????? 4 :
          <input type="file" accept="image/*" onChange={onUploadImage4}></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="????????? ??????"
            value={recipe4}
            onChange={onUploadrecipe4}
          ></input>
        </p>
        <p>
          ????????? ?????? 5 :
          <input type="file" accept="image/*" onChange={onUploadImage5}></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="????????? ??????"
            value={recipe5}
            onChange={onUploadrecipe5}
          ></input>
        </p>
        <p>
          ????????? ?????? 6 :
          <input type="file" accept="image/*" onChange={onUploadImage6}></input>
        </p>
        <p>
          <input
            className="board_text"
            type="text"
            placeholder="????????? ??????"
            value={recipe6}
            onChange={onUploadrecipe6}
          ></input>
        </p>
        {!state ? (
          <p>
            <button onClick={mbcc}>??????</button>
          </p>
        ) : (
          <p>
            <button onClick={onEdit}>??????</button>
          </p>
        )}
      </div>
    </div>
  );
}

export default NewBoard;

/*
  const addImage = (e) => {
    const nowSelectImageList = e.target.files;
    const nowImageURLList = [...myImage];
    for (let i = 0; i < nowSelectImageList.length; i += 1) {
      const nowImageUrl = URL.createObjectURL(nowSelectImageList[i]);

      nowImageURLList.push(nowImageUrl);
    }
    setmyImage(nowImageURLList);
    console.log(nowImageURLList);
  };
*/
