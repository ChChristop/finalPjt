import { useLocation, useNavigate, useParams } from 'react-router-dom';
import {useState, useEffect} from 'react';
import axios from 'axios';

function Heart({reply, postData, setPostData}){
  const {state} = useLocation();
  
  // 좋아요
  let [like, setLike] = useState('');

  // 좋아요 전송
  function likeTog() {
    const fdata = new FormData();
    fdata.append('content', reply);
    axios.post("http://192.168.0.23:8080/api/dish/like/" + state + "/1", fdata)
      .then((res) => {
        setLike(res.data)
      })
      .catch((error)=>{
        console.log(error + '에러');
      });
    }
    console.log(like)


    const onLikeHandle = ()=>{
      likeTog()
      setPostData({...postData, liked: postData.liked === "liked" ? "" : "liked" , dish_like: postData.liked === "liked" ? postData.dish_like -1 : postData.dish_like +1})
    }
  return(
<div>
  
  {/* 좋아요 버튼 */}
  <button onClick={onLikeHandle}>
    {/* { postData && postData.liked === "liked" ? '❤️' : '🤍'} */}
    {postData.liked ? '❤️' : '🤍'}
  </button>
  {postData.dish_like
  }
  </div>
  )
}

export default Heart;