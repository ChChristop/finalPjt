import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { TreeItem, TreeView } from '@material-ui/lab';

const Posts = ({ realData, indexOfFirst, indexOfLast }) => {
  // 페이지 분리

  let navigate = useNavigate();
  // current(realData);
  let [simple, setSimple] = useState([]);
  const [valueState, setValueState] = useState('');
  const onChangeHandler = (event) => {
    const value = event.target.value
    setValueState(value)
  }

  const [inputState, setInputState] = useState('');

  useEffect(()=>{setSimple(realData)},[realData])

  const handleInput = (event) => {
    const input = event.target.value
    setInputState(input)
    console.log(inputState)  // 검색어 출력
    const filtered = realData.filter((itemList) => {
      if(valueState === '1')
        return itemList.cookery.toUpperCase().includes(input.toUpperCase());
      else if(valueState === '2')
        return itemList.date||itemList.writer.toUpperCase().includes(input.toUpperCase());
      else
        return itemList.dish_name.toUpperCase().includes(input.toUpperCase());
    });
    if (input === '') {
      setSimple(realData)
    } else {
      setSimple(filtered);
    }
  }
  let currentPosts = simple.slice(indexOfFirst, indexOfLast);

  return (
    <>
    <div className='write'>
      <select defaultValue='0' className='select' onChange={onChangeHandler}>
        <option value='0'>글제목</option>
        <option value='1'>조리법</option>
        <option value='2'>작성일</option>
      </select>

      <input type='text' className='searchI' onChange={handleInput} placeholder='Search'></input>
      <span className='blind'>-</span>
      <button className='searchB' onClick={()=>{
        inputState === '' ? alert('검색어를 입력해주세요.') : console.log(inputState)
      }}>검색</button>
      </div><br/>
      <div className="results">
        {simple?.length > 0 && (
          <TreeView multiselect>
          {currentPosts.map((post, i) => {
            //console.log(simple)
             return (
              <TreeItem
              key={i}
                // nodeId={}
                label={
                  <h3 className='list' key={i} 
                  onClick={()=>{
                  navigate('/detail/'+ post.dish_num)
                  // navigate('/detail/'+ post.id) //더미데이터주의
                  }}>
                  <div className="titleD">
                  {/* <h3>**더미데이터{simple[i].id}**</h3> */}
                  {post.cookery === null ? "[기타]" : '[' + post.cookery + ']'} {post.dish_name}
                  </div>
                  <small> ❤️{post.hit}</small>
                  <small> 😋 백번먹어
                    {/* {post.ate} */}
                    </small>
                  <div className="date">조회: {post.hit}<br/>{post.date}</div>
                  </h3>
                  }
                />
              );
            })}
          </TreeView>
        )}
      </div>
    </>
  );
};

export default Posts;