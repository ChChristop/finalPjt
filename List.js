import './../App.css';
import { useSelector, useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { TreeItem, TreeView } from '@material-ui/lab';
import { useNavigate } from 'react-router';

function List() {
  const [dishData, setDishData] = useState();
  const [valueState, setValueState] = useState();

  let navigate = useNavigate();
  const onChangeHandler = (event) => {
    const value = event.target.value;
    setValueState(value);
  };

  const [filteredData, setFilteredData] = useState(dishData);

  function handleInput(event) {
    const input = event.target.value;
    console.log(input);
    const mydata = dishData.filter((recipe) => {
      // return recipe.title.toLowerCase().includes(input.toLowerCase());
      if (valueState === '1')
        return recipe.writer.toLowerCase().includes(input.toLowerCase());
      else if (valueState === '2')
        return recipe.date.toLowerCase().includes(input.toLowerCase());
      else return recipe.dish_name.toLowerCase().includes(input.toLowerCase());
    });
    if (input === '') {
      setFilteredData(dishData);
    } else {
      setFilteredData(mydata);
    }
  }

  useEffect(() => {
    const getDish = async () => {
      try {
        await axios
          .get('http://192.168.0.23:8080/api/dish/get', {
            headers: {
              'Content-type': 'application/json',
            },
          })
          .then((response) => {
            if (response.data) setDishData(response.data);
            console.log(response.data);
          });
      } catch (error) {
        console.log(error + '');
      }
    };
    getDish();
  }, []);

  useEffect(() => setFilteredData(dishData), [dishData]);

  let realData = useSelector((state) => state.allData);
  console.log(realData);

  // indexOf로 각 페이지의 첫번째와 마지막 인덱스 번호 구하기

  // const currentPosts = (realData) => {
  //   const currentPosts = 0;
  //   // slice로 시작번째 ~ 끝-1번째까지를 복사본으로 반환
  //   currentPosts = realData.slice(indexOfFirst, indexOfLast);
  //   return currentPosts;
  // };

  // return (
  //   <div>
  //     {dishData.map((item, index) =>
  //       index < 10 ? <img src={item.mainIMG}></img> : null
  //     )}

  //     <p> 이수진 이미지파일 테스트중입니다...</p>
  //     {/* <Posts realData={currentPosts(realData)} loading={loading}/> */}
  //     {/* <Pagination
  //         postsPerPage={postsPerPage}
  //         totalPosts={realData.length}
  //         paginate={setCurrentPage}
  //       ></Pagination> */}
  //   </div>
  // );

  return (
    <div className="detailList">
      <div className="write">
        <select defaultValue="0" className="select" onChange={onChangeHandler}>
          <option value="0">글제목</option>
          <option value="1">작성자</option>
          <option value="2">작성일</option>
        </select>
        <input
          type="text"
          className="searchI"
          onChange={handleInput}
          placeholder="Search"
        ></input>
        <span className="blind">-</span>
        <button className="searchB">검색</button>
      </div>
      <br />

      <div className="results">
        {filteredData?.length > 0 && (
          <TreeView multiselect>
            {filteredData.slice(0, 10).map((a, i) => {
              return (
                <TreeItem
                  // nodeId={}
                  label={
                    <div className="list" key={i}>
                      <h3
                        className="title"
                        onClick={() => {
                          navigate(`/detail/${a.dish_num}`);
                        }}
                      >
                        <div className="titleD">
                          {i + 1}. {filteredData[i].dish_name}
                        </div>
                        <small> ❤️{filteredData[i].dish_like}</small>
                        <small> 😋{filteredData[i].ate}</small>
                      </h3>
                      <p className="date">
                        {filteredData[i].userId} | {filteredData[i].date}
                      </p>
                    </div>
                  }
                />
              );
            })}
          </TreeView>
        )}
      </div>
      {/*
        dishData.map((a, i)=>
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
  );
}

export default List;

//   return(
//     <div className='detailList'>
//       <div className='write'>
//       <select defaultValue='0' className='select' onChange={onChangeHandler}>
//         <option value='0'>글제목</option>
//         <option value='1'>작성자</option>
//         <option value='2'>작성일</option>
//       </select>
//         <input type='text' className='searchI' onChange={handleInput} placeholder='Search'></input>
//         <span className='blind'>-</span>
//         <button className='searchB'>검색</button>
//       </div><br/>

//       {/* 레시피목록 검색 */}
//       <div className="results">
//         {filteredData.length > 0 && (
//           <TreeView multiselect>
//             {filteredData.slice(0, 10).map((a, i) => {
//               return (
//                 <TreeItem
//                   // nodeId={}
//                   label={
//                   <div className='list' key={i}>
//                     <h3 className='title' onClick={()=>{
//                       navigate('/detail/'+ a.id)
//                       }}>
//                       <div className='titleD'>{i+1}. {filteredData[i].title}</div>
//                       <small> ❤️{filteredData[i].dish_like}</small>
//                       <small> 😋{filteredData[i].ate}</small>
//                     </h3>
//                     <p className='date'>{filteredData[i].userId} | {filteredData[i].date}</p>
//                   </div>
//                 }
//                 />
//               );
//             })}
//           </TreeView>
//         )}
//       </div>

//         {/*
//         recipedata.map((a, i)=>
//             <div className='list' key={i}>
//               <h3 className='title' onClick={()=>{
//                 navigate('/detail/'+ a.id)
//               }}>
//                 <div className='titleD'>{i+1}. {recipedata[i].title}</div>
//                 <small onClick={(e)=>{
//                   e.stopPropagation();
//                   setLike(!like)
//                 }}>
//                 <span>{
//                   like == true ? '❤️' : '🤍'
//                 }</span>
//                 {recipedata[i].dish_like}</small>
//                 <small> 😋{recipedata[i].ate}</small>
//               </h3>
//               <p className='date'>{recipedata[i].date}</p>
//             </div>
//           )
//         }*/}

//     </div>
//   )
// }
