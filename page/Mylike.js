import { useState } from 'react';
import {Card} from 'react-bootstrap';
import { useSelector } from "react-redux/es/exports";


function Mylike(props){
  let recipedata = useSelector((state)=>state.tmpdata);
  let [del, setDel] = useState(recipedata)

  return (
    <>
      <div className='comm'>
        <h2><strong>좋아하는 레시피</strong></h2><br/>
        <hr/>
      {
        del.map((a,i)=>
          <div className='inlinePic'>
            <div className='delLike'>
              <button className='close' onClick={()=>{
              let copy = [...del];
              copy.splice(i, 1);
              setDel(copy);
              }}> ✖ </button></div>
              <img src="./../image/진라면.jpg" width="100%" /><br/><br/>
              <h4>{ del[i].title }</h4>
            </div>
          )
        }
      </div>
    </>
  );
}

export default Mylike;