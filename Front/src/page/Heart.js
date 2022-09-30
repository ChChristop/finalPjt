import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { useSelector } from "react-redux";

function Heart({ reply, postData, setPostData }) {
  const { state } = useLocation();
  const auths = useSelector((state) => state.auth.user);

  // 좋아요
  let [like, setLike] = useState("");

  // header
  const header = {
    headers: {
      authorization: auths.authorization,
      refreshtoken: auths.refreshtoken,
      "Content-Type": "application/json; charset=utf-8",
    },
    responseType: "json",
    responseEncoding: "utf8",
  };

  // 좋아요 전송
  function likeTog() {
    const fdata = new FormData();
    fdata.append("content", reply);
    axios
      .post(
        `http://3.38.19.221:8081/api/dish/like/${state}/${auths.mnum}`,
        fdata,
        header
      )
      .then((res) => {
        setLike(res.data);
        console.log(res.data);
      })
      .catch((error) => {
        console.log(error + "에러");
      });
  }
  console.log(like);

  const onLikeHandle = () => {
    likeTog();
    setPostData({
      ...postData,
      liked: postData.liked === "liked" ? "" : "liked",
      dish_like:
        postData.liked === "liked"
          ? postData.dish_like - 1
          : postData.dish_like + 1,
    });
  };
  return (
    <div>
      {/* 좋아요 버튼 */}
      <button onClick={onLikeHandle}>
        {/* { postData && postData.liked === "liked" ? '❤️' : '🤍'} */}
        {postData.liked ? "❤️" : "🤍"}
      </button>
      {postData.dish_like}
    </div>
  );
}

export default Heart;
