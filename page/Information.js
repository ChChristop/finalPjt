import { useState } from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function Information(){

let navigate = useNavigate();
let [pw, setPw] = useState('');

  return(
    <div className='comm'>
          <h2><strong>내정보관리</strong></h2><br/>
          <p className='out' onClick={()=> {/*navigate("/지훈님 회원탈퇴 라우터 경로")}*/}}>회원탈퇴</p>
          <hr/><br/><br/><br/><br/>
          회원님의 개인정보 보호를 위한 확인 절차를 위해 비밀번호를 입력해주세요.<br/><br/>
          <input type="password" className='inputpw' placeholder='비밀번호'
                  onChange={(e)=>{setPw(e.target.value)}} />
          <br/><br/><br/><br/><hr/>
          <br/><br/>
          <div className='blind'>
            <Button className='cnxl' variant="dark" 
                    onClick={()=>{navigate("/")}}>취소</Button>
            {'-----'}
            <Button className='ok' variant="dark"
                    onClick={()=>{pw == 1234 ? navigate("/editinfo") : alert('비번확인해')}}>확인</Button>
          </div>




    </div>
  );
}

export default Information;