import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const user = { mnum: 1 };

function Mypoint() {
  let navigate = useNavigate();

  const [page, setPage] = useState(1);
  const [pointList, setPointList] = useState({ end: 0 });
  const size = 3;
  const auths = useSelector((state) => state.auth.user);

  const getPoint = async () => {
    const api = `http://3.38.19.221:8081/api/member/point-list/${auths.mnum}`;
    const header = {
      headers: {
        authorization: auths.authorization,
        // authorization:
        //   "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMzMiLCJyb2xlIjoiW01FTUJFUl0iLCJtdW0iOjMwNiwiYWRtaW5DaGVjayI6ImZhbHNlIiwiZXhwIjoxNjY0MTU5ODU0LCJtZW1iZXJJRCI6IjMzMyJ9.67ffVMOyxmRyXXNkNuV_jTIRKVP9pSxtVOJ--ngokff7KAjPyBD9EBi2Nt1iY_fUq9DPz-SLevYlawWVKlGp3w",
        refreshtoken: auths.refreshtoken,
        "Content-Type": "application/json; charset=utf-8",
      },
      responseType: "json",
      responseEncoding: "utf8",
    };
    try {
      let res = null;
      if (page > pointList?.end) {
        res = await axios.get(`${api}/?page=1&size=${size}`, header);
        console.log(res.data);
        setPage(2);
        setPointList(res.data);
      } else {
        res = await axios.get(`${api}/?page=${page}&size=${size}`, header);
        setPage((prev) => prev + 1);
        console.log(res.data);
        setPointList({
          ...res.data,
          dtoList: [...pointList.dtoList, ...res.data.dtoList],
        });
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getPoint();
  }, []);

  return (
    <>
      <div className="comm">
        <h2>
          <strong>내 포인트</strong>
        </h2>
        <br />
        <hr />
        {pointList.dtoList &&
          pointList.dtoList.map((a, i) => (
            <>
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
                  댓글 {a.content}
                  <br />
                  작성일{a.date}
                </div>
                <hr />
              </div>
              {page <= pointList?.end &&
                pointList?.dtoList?.length - 1 === i && (
                  <div onClick={getPoint}>더보기</div>
                )}
            </>
          ))}
      </div>
    </>
  );
}

export default Mypoint;
