import { ListGroup, Col, Nav } from "react-bootstrap";
import Information from "./Information";
import Reply from "./Reply";
import Mylike from "./Mylike";
import Gather from "./Gather";
import { useSelector } from "react-redux/es/exports";
import { Routes, Route, useNavigate } from "react-router-dom";
import Myate from "./Myate";

let Mypage = (props) => {
  let recipedata = useSelector((state) => state.tmpdata);
  let navigate = useNavigate();

  return (
    <div>
      <div className="floatL">
        <div className="info">
          <strong> My Page </strong>
        </div>
        <ListGroup>
          <Col>
            <Nav className="flex-column">
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate("/mypage/myinfo");
                  }}
                >
                  <strong className="black">내정보관리</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate("/mypage/mylike");
                  }}
                >
                  <strong className="black">좋아요❤️</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate("/mypage/myate");
                  }}
                >
                  <strong className="black">먹어봄😋</strong>
                </Nav.Link>
              </ListGroup.Item>
              <ListGroup.Item>
                <Nav.Link
                  onClick={() => {
                    navigate("/mypage/reply");
                  }}
                >
                  <strong className="black">댓글관리📝</strong>
                </Nav.Link>
              </ListGroup.Item>
            </Nav>
          </Col>
        </ListGroup>
      </div>

      <Routes>
        <Route
          path="/"
          element={
            <div className="floatR">
              <Gather />
            </div>
          }
        />
        <Route
          path="myinfo"
          element={
            <div className="floatR">
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
      </Routes>
    </div>
  );
};

export default Mypage;
