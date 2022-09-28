import { BrowserRouter as Router, Route, Link, Routes } from "react-router-dom";
import { ListGroup, Col } from "react-bootstrap";
import { Nav } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import RanKingPage from "./LanKingPage";
import RanKingDish from "./useranking";

function Rankinglayout() {
  let navigate = useNavigate();

  return (
    <div>
      <div className="floatL">
        <div className="info">
          <ListGroup>
            <Col>
              <Nav className="flex-column">
                <ListGroup.Item>
                  <Nav.Link
                    onClick={() => {
                      navigate("/Rankinglayout/RanKingPage/");
                    }}
                  >
                    <strong className="black">유저랭킹</strong>
                  </Nav.Link>
                </ListGroup.Item>

                <ListGroup.Item>
                  <Nav.Link
                    onClick={() => {
                      navigate("/Rankinglayout/RanKingDish/");
                    }}
                  >
                    <strong className="black">레시피랭킹</strong>
                  </Nav.Link>
                </ListGroup.Item>
                <br />
              </Nav>
            </Col>
          </ListGroup>
        </div>
      </div>
      <Routes>
        <Route path="RanKingPage" element={<RanKingPage />} />
        <Route path="RanKingDish" element={<RanKingDish />} />
      </Routes>
    </div>
  );
}
export default Rankinglayout;
