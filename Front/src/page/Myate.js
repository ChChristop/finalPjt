import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const user = { mnum: 1 };

function Myate() {
  const [page, setPage] = useState(1);
  const [ateList, setAteList] = useState({ end: 0 });
  const size = 10;

  let navigate = useNavigate();
  const auths = useSelector((state) => state.auth.user);

  const getAte = async () => {
    const api = `http://3.38.19.221:8081/api/member/ate-list/${auths.mnum}`;
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
      if (page > ateList?.end) {
        res = await axios.get(`${api}/?page=1&size=${size}`, header);
        setPage(2);
        console.log(res.data);
        setAteList(res.data);
      } else {
        res = await axios.get(`${api}/?page=${page}&size=${size}`, header);
        setPage((prev) => prev + 1);
        setAteList({
          ...res.data,
          dtoList: [...ateList.dtoList, ...res.data.dtoList],
        });
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getAte();
  }, []);

  console.log(ateList.dtoList);

  return (
    <>
      <div className="comm">
        <h2>
          <strong>작성한 글</strong>
        </h2>
        <br />
        <hr />
        <>
          <div>
            {ateList.dtoList &&
              ateList.dtoList.map((a, i) => (
                <>
                  <div
                    className="mouse"
                    onClick={() => {
                      navigate("/detail/" + a.RCP_SEQ, { state: a.RCP_SEQ });
                    }}
                  >
                    <div className="smallL">
                      <img src={a.ate_picture} width="40%" />
                    </div>
                    <div className="smallR">
                      <h4>{a.RCP_NM}</h4>
                      내용: {a.ate_content}
                      <br />
                      작성일: {a.ate_date}
                    </div>
                    <hr />
                  </div>
                  {page <= ateList?.end &&
                    ateList?.dtoList?.length - 1 === i && (
                      <div onClick={getAte}>더보기</div>
                    )}
                </>
              ))}
          </div>
        </>
      </div>
    </>
  );
}

export default Myate;
