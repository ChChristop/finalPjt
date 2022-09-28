import "./Card.css";
import "./../App.css";
import { useNavigate } from "react-router-dom";
import { Nav, Button, Navbar, Card } from "react-bootstrap";
import LikeButton from "./LikeButton";
import Comment from "./Comment";
import AteBoard from "./AteBoard";
import axios from "axios";
import { useState, useEffect } from "react";

function Cardfilp() {
  let navigate = useNavigate();
  //  axios로 데이터 가져오기
  let [MukData, setMukData] = useState([]);
  useEffect(() => {
    axios
      .get("http://3.38.19.221:8081/api/ate/get")

      .then((response) => {
        console.log(response.data);
        setMukData(response.data);
      })
      .catch(() => {
        console.log("실패");
      });
  }, []);
  console.log(MukData);
  // const [reversed, setReversed] = useState(false);
  return (
    <>
      {MukData &&
        MukData.map((a, i) => {
          return (
            <div>
              <Card className="border_card" style={{ width: "18rem" }}>
                <Card.Img
                  variant="top"
                  src={a.ate_picture}
                  className="gridImg"
                />
                <Card.Body>
                  <Card.Title>{a.RCP_NM}</Card.Title>
                  <Card.Text>{a.ate_content}</Card.Text>
                  <Button
                    onClick={() => {
                      navigate("/AteBoard");
                    }}
                    variant="primary"
                  >
                    먹음😋
                  </Button>
                  <LikeButton anum={a.ate_num} />
                  <Comment />
                </Card.Body>
              </Card>
              {/* <Button
                onClick={() => {
                  navigate("/fileform");
                }}
                variant="primary"
              >
                먹음
              </Button>
              <img src={a.mainIMG} className="gridImg" /> */}
            </div>
          );
        })}
    </>
  );
}

export default Cardfilp;
