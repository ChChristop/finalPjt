import {Table, Button} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';

const EditInfo = () => {

  let navigate = useNavigate();
  let [pw, setPw] = useState('');
  let [chkpw, setChkPw] = useState('');
  let [phone, setPhone] = useState('');
  let [onemem, setOnemem] = useState([]);

  const auths = useSelector(state => state.auth.user);
  
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


  // 개인정보 변경
  function editPW() {
    const data = { ...auths,
      "mnum":auths.mnum,
      "memberPW" : pw ? pw : auths.memberPW,
      "nickname" : auths.nickname,
      "phoneNumber" : phone ? phone : auths.phoneNumber
      }
    axios
      .put(
        `http://3.38.19.221:8081/api/member/update`,
        data,{
          headers: {
            authorization: auths.authorization,
            refreshtoken: auths.refreshtoken,
            "Content-Type": "application/json; charset=utf-8",
          },
          responseType: "json",
          responseEncoding: "utf8",
        }
      )
      .then((res) => {
        console.log(res);
        if (res.data){
        setOnemem(data);
        alert('변경이 완료되었습니다.');
        console.log(data)
        navigate('/mypage')
      }
      })
      .catch((error) => {
        console.log(error + "에러");
      });
    }

  
  return(
    <div>
      <div className="editCenter">
        <br/>
        <h2><strong>회원정보 변경</strong></h2><br/>
        <br/>
        <p
          className="out"
          onClick={() => {
            navigate("/mypage/delmem")}
          }>회원탈퇴</p>
          <hr/>
        {onemem &&
      <Table striped className='table'>
        <tbody>
          <tr className='tr'>
            <td className='td1'>아이디(e-mail)</td>
            <td className='td2'>{onemem.memberID}</td>
          </tr>
          <tr className='tr'>
            <td className='td1'>닉네임</td>
            <td className='td2'>{onemem.nickname}</td>
          </tr>
          <tr className='tr'>
            <td className='td3'>비밀번호</td>
            <td className='td2'><input type="password" className='pw' onChange={(e)=>{setPw(e.target.value)}} placeholder='변경할 비밀번호'/>
            <br/><input type="password" className='pw' onChange={(e)=>{setChkPw(e.target.value)}} placeholder='비밀번호 확인'/></td>
          </tr>
          <tr className='tr'>
            <td className='td1'>휴대전화</td>
            <td className='td2'><input type="text" className='phone' onChange={(e)=>{setPhone(e.target.value)}} placeholder={onemem.phoneNumber}/></td>
          </tr>
        </tbody>
      </Table>
         }
      <br/>
      <br/>
      <div className='blind'>
            <Button className='cnxl' variant="dark" 
                    onClick={()=>{navigate('/mypage')}}>취소</Button>
            {'-----'}
            <Button className='ok' variant="dark"
              onClick={editPW}>저장</Button>
          </div>
      </div>
    </div>
  );
}
export default EditInfo;