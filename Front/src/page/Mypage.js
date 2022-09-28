import { ListGroup, Col, Nav } from 'react-bootstrap';
import Information from './Information';
import Reply from './Reply';
import Mylike from './Mylike';
import Mypoint from './Mypoint';
import Gather from './Gather';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Myate from './Myate';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import EditInfo from './EditInfo';
import DelMem from './DelMem'

let Mypage = () => {
  const auths = useSelector(state => state.auth.user);
  
  // 멤버 데이터 가져오기
  let [memData, setMemData] = useState([]);

  useEffect(() => {
    axios
      .get(`http://3.38.19.221:8081/api/member/search/${auths.mnum}`, {
        headers: {
          authorization: auths.authorization,
          // authorization:
          //   "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMzMiLCJyb2xlIjoiW01FTUJFUl0iLCJtdW0iOjMwNiwiYWRtaW5DaGVjayI6ImZhbHNlIiwiZXhwIjoxNjY0MTU5ODU0LCJtZW1iZXJJRCI6IjMzMyJ9.67ffVMOyxmRyXXNkNuV_jTIRKVP9pSxtVOJ--ngokff7KAjPyBD9EBi2Nt1iY_fUq9DPz-SLevYlawWVKlGp3w",
          refreshtoken: auths.refreshtoken,
          "Content-Type": "application/json; charset=utf-8",
        },
        responseType: "json",
        responseEncoding: "utf8",
      })
      .then((response) => {
        // console.log(response.data)
        setMemData(response.data);
      })
      .catch(() => {
        console.log('실패');
      });
  }, []);


  let navigate = useNavigate();

  return (
    <div>
      <div className="floatL">
        <div className="info">
          <strong className='mouse' 
            onClick={()=>{
              navigate('/mypage')
            }}> My Page </strong>
        </div>
        <ListGroup>
          <Col>
            <Nav className="flex-column">
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate('/mypage/myinfo');
                  }}
                >
                  <strong className="black">내정보관리</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate('/mypage/mypoint');
                  }}
                >
                  <strong className="black">포인트🎈</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate('/mypage/mylike');
                  }}
                >
                  <strong className="black">좋아요❤️</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate('/mypage/myate');
                  }}
                >
                  <strong className="black">먹어봄😋</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate('/mypage/reply');
                  }}
                >
                  <strong className="black">댓글관리📝</strong>
                </Nav.Link>
              </ListGroup.Item>
            </Nav>
          </Col>
        </ListGroup>
      </div>
      {memData && (
        <Routes>
          <Route
            path="/"
            element={
              <div className="floatR">
              <Gather memData={memData}/>
              {/* <NoMem /> */}
              </div>
            }
          />
          <Route
            path="mypoint"
            element={
              <div className="floatR">
                <Mypoint />
              </div>
            }
          />
          <Route
            path="myinfo"
            element={
              <div className="floatR">
                {/* <EditInfo/> */}
                <Information />
              </div>
            }
          />
          <Route
            path="mylike"
            element={
              <div className="floatR">
                <Mylike />
              </div>
            }
          />
          <Route
            path="myate"
            element={
              <div className="floatR">
                <Myate />
              </div>
            }
          />
          <Route
            path="reply"
            element={
              <div className="floatR">
                <Reply />
              </div>
            }
          />
          <Route
            path="delmem"
            element={
              <div className="floatR">
                <DelMem />
              </div>
            }
          />
        </Routes>
      )}
    </div>
  );
};

export default Mypage;
