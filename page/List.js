import './../App.css';
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import TreeView from '@material-ui/lab/TreeView';
import TreeItem from '@material-ui/lab/TreeItem';
// import {paginate}  from 'react-js-pagination';
import axios from 'axios';



function List (){
  let recipedata = useSelector((state)=>state.tmpdata);
  let navigate = useNavigate();
  let [search, setSearch] = useState('');
  const [filteredData, setFilteredData] = useState([]);
  const [valueState, setValueState] = useState('');
  const onChangeHandler = (event) => {
    const value = event.target.value
    setValueState(value)
  }


  // ** 데이터 **
  useEffect(()=>(temp),[])
  
  const temp = async () => {
  try {
    const temp = await axios.get("https://jsonplaceholder.typicode.com/posts",{
      headers:{
        'Content-type': 'application/json'
      }})
      
      setFilteredData(temp.data);
    } catch (error) {
      console.log(error)
    }}
    console.log(filteredData)

  function handleInput(event) {
    const input = event.target.value;
    const mydata = filteredData.filter((recipe) => {
      if(valueState === '1')
        return recipe.writer.toLowerCase().includes(input.toLowerCase());
      else if(valueState === '2')
        return recipe.date.toLowerCase().includes(input.toLowerCase());
      else
        return recipe.title.toLowerCase().includes(input.toLowerCase());
      });
    if (input === '') {
      setFilteredData(filteredData)
      console.log(filteredData)
    } else {
      setFilteredData(mydata);
    }
  }
  // ** 페이징 **
  // let [currentPage, setCurrentPage] = useState(1);
  // let pageSize = 4;
  // let totalPage = Math.ceil(filteredData.length / pageSize);
  // let pages = paginate(filteredData, currentPage, pageSize);

  

  return(
    <div className='detailList'>
      <div className='write'>
      <select defaultValue='0' className='select' onChange={onChangeHandler}>
        <option value='0'>글제목</option>
        <option value='1'>작성자</option>
        <option value='2'>작성일</option>
      </select>
        <input type='text' className='searchI' onChange={handleInput} placeholder='Search'></input>
        <span className='blind'>-</span>
        <button className='searchB'>검색</button>
      </div><br/>

      {/* 레시피목록 검색 */}
      <div className="results">
        {filteredData.length > 0 && (
          <TreeView multiselect>
            {filteredData.slice(0, 10).map((a, i) => {
              return (
                <TreeItem
                  // nodeId={}
                  label={
                  <div className='list' key={i}>
                    <h3 className='title' onClick={()=>{
                      navigate('/detail/'+ a.id)
                      }}>
                      <div className='titleD'>{i+1}. {filteredData[i].title}</div>
                      <small> ❤️{filteredData[i].dish_like}</small>
                      <small> 😋{filteredData[i].ate}</small>
                    </h3>
                    <p className='date'>{filteredData[i].userId} | {filteredData[i].date}</p>
                  </div>
                }
                />
              );
            })}
          </TreeView>
        )}
      </div>
      {/* <Pagination/> */}

     
        {/*
        recipedata.map((a, i)=> 
            <div className='list' key={i}>
              <h3 className='title' onClick={()=>{
                navigate('/detail/'+ a.id)
              }}>
                <div className='titleD'>{i+1}. {recipedata[i].title}</div>
                <small onClick={(e)=>{
                  e.stopPropagation();
                  setLike(!like)
                }}> 
                <span>{
                  like == true ? '❤️' : '🤍'
                }</span> 
                {recipedata[i].dish_like}</small>
                <small> 😋{recipedata[i].ate}</small>
              </h3>
              <p className='date'>{recipedata[i].date}</p>
            </div>
          )
        }*/}

    </div>
  )
}

// function Pagination({pages}, {currentPage}, {pageSize}) {
//   const startIndex = (currentPage - 1) * pageSize;
//   const sortedData = pages.sort((a,b) => 
//   new Date(a.date) <= new Date(b.date) ? -1 : 1);
//   const spliceData = [...sortedData].splice(startIndex, pageSize);
//   return spliceData;
// };


// const Paging = () => {
//   const [page, setPage] = useState(1);
//   const handlePageChange = (page) => {
//     setPage(page);
//     console.log(page);
//   };
//   return (
//     <Pagination
//       activePage={page}           // 현재페이지
//       itemsCountPerPage={5}       // 한 페이지당 보여줄 리스트 아이템의 개수
//       totalItemsCount={450}       // 총 아이템의 개수
//       pageRangeDisplayed={5}      // Paginator 내에서 보여줄 페이지의 범위
//       onChange={handlePageChange} // 페이지가 바뀔 때 핸들링해줄 함수
//     />
//   );
// };

export default List;