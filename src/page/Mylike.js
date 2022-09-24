import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const user = { mnum: 1 };

function Mylike() {
  let navigate = useNavigate();

  const [page, setPage] = useState(1);
  const [likeList, setLikeList] = useState({ end: 0 });
  const size = 3;

  const getLike = async () => {
    const api = `http://3.38.19.221:8081/api/member/like-list/${user.mnum}`;
    const headers = {
      'Content-type': 'application/json',
    };
    try {
      let res = null;
      if (page > likeList?.end) {
        res = await axios.get(`${api}/?page=1&size=${size}`, headers);
        setPage(2);
        console.log(res.data);
        setLikeList(res.data);
      } else {
        res = await axios.get(`${api}/?page=${page}&size=${size}`, headers);
        setPage((prev) => prev + 1);
        console.log(res.data);
        setLikeList({
          ...res.data,
          dtoList: [...likeList.dtoList, ...res.data.dtoList],
        });
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {getLike()}, []);
  {console.log(likeList.dtoList)}
  return (
    <>
      <div className="comm">
        <h2>
          <strong>좋아하는 레시피</strong>
        </h2>
        <br />
        <hr />
        {likeList.dtoList &&
          likeList.dtoList.map((a, i) => (
            <>
              <div className="inlinePic">
                {/* <div className="delLike">
                  <button
                    className="close"
                    onClick={() => {
                      let copy = [...likeList.dtoList];
                      copy.splice(i, 1);
                      setDel(copy);
                    }}
                  >
                    {' '}
                    ✖{' '}
                  </button>
                </div> */}
                <img
                  src={a.ATT_FILE_NO_MAIN}
                  width="100%"
                  onClick={() => {
                    navigate('/detail/' + a.RCP_SEQ,{state:a.RCP_SEQ});
                  }}
                  
                />
                <br />
                <br />
                <h4>{a.RCP_NM}</h4>
              </div>
              {page <= likeList?.end && likeList?.dtoList?.length - 1 === i && (
                <div onClick={getLike}>더보기</div>
              )}
            </>
          ))}
      </div>
    </>
  );
}

export default Mylike;
