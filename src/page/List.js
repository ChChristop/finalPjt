import './../App.css';
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { useState } from "react";
import TreeView from '@material-ui/lab/TreeView';
import TreeItem from '@material-ui/lab/TreeItem';
import Pagination from 'react-js-pagination';

const Paging = () => {
  const [page, setPage] = useState(1);
  const handlePageChange = (page) => {
    setPage(page);
    console.log(page);
  };
  return (
    <Pagination
      activePage={page}           // 현재페이지
      itemsCountPerPage={5}      // 한 페이지당 보여줄 리스트 아이템의 개수
      totalItemsCount={450}       // 총 아이템의 개수
      pageRangeDisplayed={5}      // Paginator 내에서 보여줄 페이지의 범위
      // prevPageText={"‹"}
      // nextPageText={"›"}
      onChange={handlePageChange} // 페이지가 바뀔 때 핸들링해줄 함수
    />
  );
};

function List (){
  let recipedata = useSelector((state)=>state.tmpdata);
  let navigate = useNavigate();
  let [search, setSearch] = useState('');

  const [valueState, setValueState] = useState('');
  const onChangeHandler = (event) => {
    const value = event.target.value
    setValueState(value)
  }
  
  const [filteredData, setFilteredData] = useState([]);

  function handleInput(event) {
    const input = event.target.value;
    const mydata = recipedata.filter((recipe) => {
      // return recipe.title.toLowerCase().includes(input.toLowerCase());
      if(valueState === '0')
        return recipe.title.toLowerCase().includes(input.toLowerCase());
      if(valueState === '1')
        return recipe.writer.toLowerCase().includes(input.toLowerCase());
      if(valueState === '2')
        return recipe.date.toLowerCase().includes(input.toLowerCase());
    });
    if (input === '') {
      setFilteredData(recipedata);
    } else {
      setFilteredData(mydata);
    }
  }


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
            {filteredData.slice(0, 15).map((a, i) => {
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
                    <p className='date'>{filteredData[i].writer} | {filteredData[i].date}</p>
                  </div>
                }
                />
              );
            })}
          </TreeView>
        )}
      </div>
      <Pagination/>
      {/* {
      recipedata.map((a, i)=> 
        <div className='list' key={i}>
          <h3 className='title' onClick={()=>{
            navigate('/detail/'+ a.id)
            }}>
            <div className='titleD'>{i+1}. {recipedata[i].title}</div>
            
            <small> ❤️{recipedata[i].dish_like}</small>
            <small> 😋{recipedata[i].ate}</small>
          </h3>
          <p className='date'>{recipedata[i].date}</p>
        </div>
        )
      } */}
    </div>
  )
}

export default List;