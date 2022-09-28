import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { useAuthActions } from '../store/authSlice';

function DelMem(){

  const auths = useSelector(state => state.auth.user);
  const user = useAuthActions();
  let [onemem, setOnemem] = useState([]);
  let navigate = useNavigate();

  const header = {
    headers: {
      authorization: auths.authorization,
      refreshtoken: auths.refreshtoken,
      "Content-Type": "application/json; charset=utf-8",
    },
    responseType: "json",
    responseEncoding: "utf8",
  };

  // 특정 회원 가져오기
  useEffect(() => {
    axios
      .get(`http://3.38.19.221:8081/api/member/search/${auths.mnum}`, {
        headers: {
          authorization: auths.authorization,
          refreshtoken: auths.refreshtoken,
          "Content-Type": "application/json; charset=utf-8",
        },
        responseType: "json",
        responseEncoding: "utf8",
      })
      .then((response) => {
        setOnemem(response.data);
      })
      .catch(() => {
        console.log('실패');
      });
  }, []);

  // 회원 탈퇴
  function delMem() {
    console.log(auths.mnum)
    axios
    .delete(
      `http://3.38.19.221:8081/api/member/delete/${auths.mnum}`,
      header
      )
      .then((res) => {
        console.log("result: ", res.data);
        // setOnemem(onemem.filter((member) => member.mnum !== auths.mnum));
    }).catch((error) => {
      console.log(error.response);
    });
  }
console.log("onemem: ", onemem)

  return (
<>
      <div className="comm">
        <h2>
          <strong>회원 탈퇴</strong>
        </h2>
        <br />
        <hr />
        <div className='outbox'>
          <h6 className='outtext'><strong>탈퇴사유</strong></h6>
          <p>
            <div className='threeby'><input type="radio" /><label>사용빈도 낮음</label></div>
            <div className='threeby'><input type="radio" /><label>종류가 다양하지 않음</label></div>
            <div className='threeby'><input type="radio" /><label>개인정보 유출 우려</label></div>
          </p>
          <p>
          <div className='threeby'><input type="radio" /><label>다른 사이트 이용</label></div>
          <div className='threeby'><input type="radio" /><label>혜택이 다양하지 않음</label></div>
          <div className='threeby'><input type="radio" /><label>기타</label></div>
          </p>
          <div className='textarea'>
            <input type="textarea" placeholder='자유롭게 기재해주세요.' className='outreason'/>
          </div>
          <button className='outbtn' onClick={()=>{
            navigate('/mypage');
            }}>취소</button><span className='blind'>---</span>
            <button className='outbtn' onClick={()=>{
            delMem();
            // user.logout();
            alert('탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.')
            navigate('/');
            }}>탈퇴</button>
        </div>
      </div>
    </>
  )
}

export default DelMem;