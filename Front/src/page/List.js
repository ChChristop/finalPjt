import './../App.css';
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { TreeItem, TreeView } from '@material-ui/lab';
import axios from "axios";
import { useSelector } from "react-redux";

const PageUl = styled.ul`
  float: center;
  list-style: none;
  text-align: center;
  border-radius: 3px;
  color: black;
  padding: 1px;
  margin-left: 35%;
  margin-right: 35%;
  border-top: 3px solid #a7a0f2;
  border-bottom: 3px solid #a7a0f2;
  background-color: #FFFDDE;
`;

const PageLi = styled.li`
  display: inline-block;
  font-size: 20px;
  font-weight: 600;
  padding: 5px;
  border-radius: 5px;
  width: 25px;
  &:hover {
    cursor: pointer;
    color: white;
    background-color: #D9D7F1;
  }
  &:focus::after {
    color: white;
    background-color: #D9D7F1;
  }
`;

const PageSpan = styled.span`
  &:hover::after,
  &:focus::after {
    border-radius: 100%;
    color: white;
    background-color: #263a6c;
  }
`;

function List(){
  let navigate = useNavigate();

  const userInfo = useSelector((state) => state.auth.user);

  let [realData, setRealData] = useState([]);
  useEffect(() => {
    axios
      .get("http://3.38.19.221:8081/api/dish/get")
      .then((response) => {
        // console.log(response.data)
        setRealData(response.data);
      })
      .catch(() => {
        console.log("실패");
      });
  }, []);

  // ** 페이지네이션 **
  let [currentPage, setCurrentPage] = useState(1);
  let [postsPerPage, setPostsPerPage] = useState(10);

  // indexOf로 각 페이지의 첫번째와 마지막 인덱스 번호 구하기
  const indexOfLast = currentPage * postsPerPage;
  const indexOfFirst = indexOfLast - postsPerPage;

  let [simple, setSimple] = useState([]);
  const [valueState, setValueState] = useState('');
  const onChangeHandler = (event) => {
    const value = event.target.value
    setValueState(value)
  }

  const [inputState, setInputState] = useState('');
  useEffect(()=>setSimple(realData),[realData])
  // console.log(simple[0].cookery)

  const handleInput = (event) => {
    const input = event.target.value
    setCurrentPage(1);
    setInputState(input);
    console.log(inputState)  // 검색어 출력
    const filtered = realData.filter((itemList) => {
      if(valueState === '1')
        if(itemList.cookery === null)
          return '기타'.includes(input.toUpperCase());
        else
          return itemList.cookery.toUpperCase().includes(input.toUpperCase());
      else if(valueState === '2')
        return itemList.date.toUpperCase().includes(input.toUpperCase());
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

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(simple.length / postsPerPage); i++) {
      pageNumbers.push(i);
    }

    // 페이지네이션 합치기
  return (
    <div>
    {/* 목록 */}
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
      <br/><br/>
        {simple?.length > 0 && (
          <TreeView multiselect>
          {currentPosts.map((post, i) => {
            // console.log(post.dish_num)
             return (
              <TreeItem className='orderB'
                // nodeId={}
                label={
                  // <div className="results">
                    <div className='list' key={i} >
                  <h3
                  onClick={()=>{
                    navigate(`/detail/${post.dish_num}`, {state:post.dish_num})
                  }}>
                  <div className="titleD">
                  {/* <h3>**더미데이터{simple[i].id}**</h3> */}
                  {post.cookery === null ? "[기타]" : '[' + post.cookery + ']'} {post.dish_name}
                  </div>
                  <small> ❤️{post.dish_like}</small>
                  <small> 😋 {post.ate}
                    {/* {post.ate} */}
                    </small>
                  <div className="date">조회: {post.hit}<br/>{post.date}</div>
                  </h3>
                  </div>
                  }
                />
              );
            })}
          </TreeView>
        )
      }
     

    {/* 페이지번호 */}
    <div className='orderC'>
    <div className='pageDesign'>
        <PageUl>
      {pageNumbers.map((number) => (
            <PageLi key={number} className="page-item">
              <PageSpan onClick={() => setCurrentPage(number)}>
                {number}
              </PageSpan>
            </PageLi>
          ))}
        </PageUl>
      </div>
    </div>
    </div>
    </div>
  )


  // return (
  //   <div>
  //     <Posts realData={realData} indexOfLast={indexOfLast} indexOfFirst={indexOfFirst}/>
  //     <Pagination
  //       postsPerPage={postsPerPage}
  //       totalPosts={realData.length}
  //       paginate={setCurrentPage}
  //     ></Pagination>
  //   </div>
  // )
}

export default List;