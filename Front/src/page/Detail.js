import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Nav, Button, Tab, Tabs, Form } from "react-bootstrap";
import { useState, useEffect } from "react";
import axios from "axios";
// import EditCom from './EditCom';
import Heart from "./Heart";
import moment from "moment";
import { useSelector } from "react-redux";

function Detail() {
  // hook
  const { state } = useLocation();
  // console.log(state)
  let navigate = useNavigate();
  const auths = useSelector((state) => state.auth.user);
  // 날짜
  const now = moment().format("YYYY-MM-DDTHH:mm:ss");

  const header = {
    headers: {
      authorization: auths.authorization,
      refreshtoken: auths.refreshtoken,
      "Content-Type": "application/json; charset=utf-8",
    },
    responseType: "json",
    responseEncoding: "utf8",
  };

  // 게시글 하단 탭
  // let [tab, setTab] = useState('');
  // let [레시피, set레시피] = useState('');
  // let [영상, set영상] = useState('');

  // 댓글 저장
  let [reply, setReply] = useState("");
  let [inputText, setInputText] = useState("");
  // let [MSGnum, setMSGnum] = useState('');
  // let [allReply, setAllReply] = useState();

  // 댓글 수정
  let [editText, setEditText] = useState(false);

  // 게시글 데이터 가져오기
  let [postData, setPostData] = useState([]);

  // 재료 분리
  let str = postData.ingredient;
  let ingre = (str || "").split(",");

  // 게시글 데이터 하나씩 가져오기
  useEffect(() => {
    axios
      .get(
        `http://3.38.19.221:8081/api/dish/get/${state}/${auths.mnum}`,
        header
      )
      .then((response) => {
        console.log(response.data);
        setPostData(response.data.result);
        // setAllReply(postData && postData.commList);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [state]);

  console.log(postData);

  // 댓글 삭제
  function delMSG(dc_num) {
    axios
      .delete(
        `http://3.38.19.221:8081/api/dish/comm/delete/${auths.mnum}/${dc_num}/${state}`,
        header
      )
      .then((res) => {
        setPostData({
          ...postData,
          commList: postData.commList.filter((msg) => msg.dc_num !== dc_num),
        });
        console.log(res);
      });
  }

  // 댓글 등록
  function addMSG() {
    const fdata = new FormData();
    fdata.append("content", inputText);
    axios
      .post(
        `http://3.38.19.221:8081/api/dish/comm/add/${auths.mnum}/${state}`,
        fdata,
        header
      )
      .then((res) => {
        console.log(res);
        setPostData({
          ...postData,
          commList: [
            ...postData.commList,
            {
              mnum: auths.mnum,
              RCP_SEQ: state,
              content: inputText,
              dc_num: res.data,
              date: now.toString(),
              nickname: auths.nickname,
            },
          ],
        });
      })
      // setInputText('')
      .catch((error) => {
        console.log(error + "에러");
      });
  }
  {
    postData.commList && console.log(postData);
  }

  // 댓글 수정
  function editMSG(dc_num) {
    const fdata = new FormData();
    fdata.append("content", inputText);
    axios
      .put(
        `http://3.38.19.221:8081/api/dish/comm/edit/${auths.mnum}/${dc_num}`,
        fdata,
        header
      )
      .then((res) => {
        console.log(res);
        // setPostData({...postData, commList:[...postData.commList, {mnum:1, RCP_SEQ:state, content:inputText, editdate: now.toString()}],});
        console.log(now.toString());
        let commList = postData.commList.map((r, i) => {
          return r.dc_num === dc_num
            ? { ...r, content: inputText, editdate: now.toString() }
            : r;
        });
        setPostData({ ...postData, commList });
      })
      .catch((error) => {
        console.log(error + "에러");
      });
  }

  console.log(postData.commList);

  return (
    <>
      {/* 첫화면 */}
      <Button
        className="back"
        variant="light"
        onClick={() => {
          navigate(-1);
        }}
      >
        {"<<"} 뒤로가기
      </Button>
      <div className="container">
        {postData && (
          <div className="all">
            <h1 className="nameD">{postData.dish_name}</h1>
            <div className="detailR">조회: {postData.hit}</div>
            <div className="detailR">작성자: {postData.amdinID}</div>
            <div className="detailR">작성일: {postData.date}</div>
            <div className="middle">
              <img src={postData.mainIMG} width="100%" />
              <br />
              <div className="small">
                <span className="oneline">
                  <Heart
                    reply={reply}
                    postData={postData}
                    setPostData={setPostData}
                  />
                </span>
                <span className="small"> 😋{postData.ate}</span>
              </div>
              <br />
            </div>
          </div>
        )}

        {/* 하단 탭 */}
        <div>
          <Tabs
            defaultActiveKey="레시피"
            id="fill-tab-example"
            className="tabOut"
            fill
          >
            {/* 레시피 탭 */}
            <Tab eventKey="레시피" title="레시피" className="tab">
              <br />
              <br />
              <br />
              {postData.recipe &&
                postData.recipe.map((order, i) => {
                  return (
                    <div key={i}>
                      <p>
                        <img
                          src={postData.imgList[i]}
                          className="imgSize"
                        ></img>
                      </p>
                      <h4>{order}</h4>
                      <br />
                      <br />
                    </div>
                  );
                })}
            </Tab>

            {/* 재료 탭 */}
            <Tab eventKey="재료" title="재료" className="tab">
              <br />
              <br />
              <br />
              {ingre &&
                ingre.map((res, i) => {
                  return (
                    <div className="nameD" key={i}>
                      <div>
                        <p>{res}</p>
                      </div>
                    </div>
                  );
                })}
            </Tab>

            {/* 댓글 탭 */}
            <Tab eventKey="댓글" title="댓글" className="tab">
              <br />
              <br />
              <br />
              <div className="nameD">
                <input
                  className="replyTab"
                  type="text"
                  value={inputText}
                  onChange={(e) => {
                    console.log(e.target.value);
                    setInputText(e.target.value);
                  }}
                />
                <span className="blind">-</span>
                <button
                  className="replyB"
                  onClick={() =>
                    inputText === "" ? alert("댓글을 입력하세요") : addMSG()
                  }
                >
                  등록
                </button>
                <br />
                <br />
                <hr />
              </div>
              <div>
                {postData.commList &&
                  postData.commList.map((r, i) => {
                    return (
                      <div key={i}>
                        <h6 className="oneline">
                          {editText === r.dc_num ? (
                            <div>
                              <div>
                                <input
                                  type="text"
                                  value={inputText}
                                  placeholder={r.content}
                                  onChange={(e) => {
                                    setInputText(e.target.value);
                                  }}
                                ></input>
                              </div>
                              <span className="blind">-</span>
                            </div>
                          ) : (
                            <div>{r.content}</div>
                          )}
                        </h6>
                        <div className="outline">
                          <h6 className="arrReply">
                            {r.nickname} | {r.editdate ? r.editdate : r.date}
                          </h6>
                          <span className="blind">-----</span>

                          {editText === r.dc_num ? (
                            <button
                              className="delrepB"
                              onClick={() => {
                                inputText === ""
                                  ? alert("댓글을 입력하세요")
                                  : editMSG(r.dc_num);
                                setInputText("");
                                setEditText(-1);
                              }}
                            >
                              등록
                            </button>
                          ) : (
                            <button
                              className="delrepB"
                              onClick={() => {
                                setEditText(r.dc_num);
                              }}
                            >
                              수정
                            </button>
                          )}

                          <span className="blind">-</span>
                          <button
                            className="delrepB"
                            onClick={() => {
                              delMSG(r.dc_num);
                            }}
                          >
                            삭제
                          </button>
                        </div>
                        <hr />
                      </div>
                    );
                  })}
              </div>
            </Tab>
          </Tabs>
        </div>
      </div>
    </>
  );
}

export default Detail;
