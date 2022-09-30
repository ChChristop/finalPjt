import { useNavigate } from 'react-router-dom';

function Gather({memData}) {
  console.log(memData && memData)
  let navigate = useNavigate();

  return (
    <div className="comm">
      <h2>
        <strong>마이페이지</strong>
      </h2>
      <hr />
      <br />
      {memData &&
      <div className="gather">
        <div className="gatherL">
          <span>{memData.nickname}</span> <span className='smallFont'>님</span>
        </div>
        <div className="gatherR">
          <div className="triple">
            <p className="upj">포인트</p>
            <p
              className="under"
              onClick={() => {
                navigate('/mypage/mypoint');
              }}
            >
              <span>{memData.point}</span>
            </p>
          </div>
          <div className="triple">
            <p className="upj">좋아요</p>
            <p
              className="under"
              onClick={() => {
                navigate('/mypage/mylike');
              }}
            >
              <span>{memData.likeCount}</span>
            </p>
          </div>
          <div className="triple">
            <p className="upj">먹어봄</p>
            <p
              className="under"
              onClick={() => {
                navigate('/mypage/myate');
              }}
            >
              <span>{memData.ateCount}</span>
            </p>
          </div>
          <div className="triple">
            <p className="upj">댓글수</p>
            <p
              className="under"
              onClick={() => {
                navigate('/mypage/reply');
              }}
            >
              <span>{memData.comment}</span>
            </p>
          </div>
        </div>
      </div>}
    </div>
  );
}

export default Gather;
