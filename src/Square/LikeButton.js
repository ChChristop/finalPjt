import { useLocation, useNavigate, useParams } from 'react-router-dom';
import {useState, useEffect} from 'react';
import axios from 'axios';

function LikeButton({reply = '댓글',anum, postData= {dish_like:1}}){
  const {state} = useLocation();
  
  // 좋아요
  let [like, setLike] = useState('');

  // 좋아요 전송
  function likeTog() {
    console.log(anum)
    const fdata = new FormData();
    fdata.append('content', Comment);
    axios.post(`http://192.168.0.23:8080/api/ate/like/${anum}/2`)
      .then((res) => {
        setLike(res.data)
      })
      .catch((error)=>{
        console.log(error + '에러');
      });
    }
    console.log(like)
  return(
<div className='Likebutton'>
  
  {/* 좋아요 버튼 */}
  <button onClick={()=>{
    // setLike(!like);
    likeTog()
  }}>
    {/* { postData && postData.liked === "liked" ? '❤️' : '🤍'} */}
    {like === '좋아요 등록!' ? '❤️' : '🤍'}
  </button>
  {postData && 
    like === '좋아요 등록!' ?
    postData.dish_like +1
    : postData.dish_like
  }
</div>
  )
}

export default LikeButton;