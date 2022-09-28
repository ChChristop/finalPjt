import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const user = { mnum: 1 };

function Reply() {
  let navigate = useNavigate();
  const [page, setPage] = useState(1);
  const [replyList, setReplyList] = useState({ end: 0 });
  const size = 1;
  const auths = useSelector((state) => state.auth.user);

  const getReply = async () => {
    const api = `http://3.38.19.221:8081/api/member/dishcomm-list/${auths.mnum}`;
    const header = {
      headers: {
        authorization: auths.authorization,
        refreshtoken: auths.refreshtoken,
        "Content-Type": "application/json; charset=utf-8",
      },
      responseType: "json",
      responseEncoding: "utf8",
    };
    try {
      let res = null;
      if (page > replyList?.end) {
        res = await axios.get(`${api}/?page=1&size=${size}`, header);
        setPage(2);
        setReplyList(res.data);
      } else {
        res = await axios.get(`${api}/?page=${page}&size=${size}`, header);
        setPage((prev) => prev + 1);
        setReplyList({
          ...res.data,
          dtoList: [...replyList.dtoList, ...res.data.dtoList],
        });
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getReply();
  }, []);

  return (
    <>
      <div className="comm">
        <h2>
          <strong>댓글관리</strong>
        </h2>
        <br />
        <hr />
        {replyList.dtoList &&
          replyList.dtoList.map((a, i) => (
            <div key={i}>
              <div
                className="mouse"
                onClick={() => {
                  navigate("/detail/" + a.RCP_SEQ, { state: a.RCP_SEQ });
                }}
              >
                <div className="smallL">
                  <img src={a.ATT_FILE_NO_MAIN} width="40%" />
                </div>
                <div className="smallR">
                  <h4>{a.RCP_NM}</h4>
                  댓글 : {a.content}
                  <br />
                  작성일{a.date}
                </div>
                <hr />
              </div>
              {page <= replyList?.end &&
                replyList?.dtoList?.length - 1 === i && (
                  <div onClick={getReply}>더보기</div>
                )}
            </div>
          ))}
      </div>
    </>
  );
}

export default Reply;
