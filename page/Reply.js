import { useSelector } from "react-redux/es/exports";

function Reply(props){
  let recipedata = useSelector((state)=>state.tmpdata);

  return(
    <>
    <div className='comm'>
      <h2><strong>댓글관리</strong></h2><br/>
      <hr/>
      {recipedata.map((a, i)=>{
        return <div>
          <div className="smallL">
            <img src="./../image/진라면.jpg" width='150px' />
          </div>
          <div className="smallR">
            <h4>{recipedata[i].title}</h4>
            댓글
            <br/>
            댓글작성일
          </div>
        <div className="blind">-</div>
        <hr/>
      </div>
      })}
      

    </div>
      </>
  );
}

export default Reply;