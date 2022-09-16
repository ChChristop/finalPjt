import { configureStore, createSlice } from '@reduxjs/toolkit'


// let like = createSlice({
//   name : 'like',
//   initialState : 0,
  // reducers : {
  //   cLike(state){
  //     return state += 1
  //   }
  // }
// })


let tmpdata = createSlice({
  name : 'recipe',
  initialState : [
    {
        id: 0,
        dnum: 3,
        writer: "writer11",
        title: "title1555",
        ingredient: "ingredient24",
        recipe: "recipe34",
        url: "url14",
        dish_like: 3,
        ate: 2,
        hit: 0,
        date: "2022-08-25 15:56:55",
        editdate: null,
        t_time: "t_time14",
        m_ingredient: null,
        cookery: "cookery",
        t_weather: "t_weather",
        dpicture: {
            pnum: 0,
            dnum: 3,
            pname: null,
            pictures: null
        }
    },
    {
        id: 1,
        dnum: 4,
        writer: "writer22",
        title: "title2555",
        ingredient: "ingredient24",
        recipe: "recipe34",
        url: "url14",
        dish_like: 6,
        ate: 5,
        hit: 18,
        date: "2022-08-24 15:57:17",
        editdate: null,
        t_time: "t_time14",
        m_ingredient: null,
        cookery: "cookery",
        t_weather: "t_weather",
        dpicture: {
            pnum: 0,
            dnum: 4,
            pname: null,
            pictures: "./../image/진라면.jpg"
        }
    },
    {
        id: 2,
        dnum: 5,
        writer: "writer33",
        title: "title3555",
        ingredient: "ingredient24",
        recipe: "recipe34",
        url: "url14",
        dish_like: 4,
        ate: 2,
        hit: 0,
        date: "2022-08-26 17:15:03",
        editdate: null,
        t_time: "t_time14",
        m_ingredient: null,
        cookery: "cookery",
        t_weather: "t_weather",
        dpicture: {
            pnum: 0,
            dnum: 5,
            pname: null,
            pictures: "./../image/신라면.jpg"
        }
    },
    {
      id: 3,
      dnum: 3,
      writer: "writer44",
      title: "title4555",
      ingredient: "ingredient24",
      recipe: "recipe34",
      url: "url14",
      dish_like: 3,
      ate: 2,
      hit: 0,
      date: "2022-08-25 15:56:55",
      editdate: null,
      t_time: "t_time14",
      m_ingredient: null,
      cookery: "cookery",
      t_weather: "t_weather",
      dpicture: {
          pnum: 0,
          dnum: 3,
          pname: null,
          pictures: null
      }
  },
  {
      id: 4,
      dnum: 4,
      writer: "writer55",
      title: "title5555",
      ingredient: "ingredient24",
      recipe: "recipe34",
      url: "url14",
      dish_like: 6,
      ate: 5,
      hit: 18,
      date: "2022-08-24 15:57:17",
      editdate: null,
      t_time: "t_time14",
      m_ingredient: null,
      cookery: "cookery",
      t_weather: "t_weather",
      dpicture: {
          pnum: 0,
          dnum: 4,
          pname: null,
          pictures: "./../image/진라면.jpg"
      }
  },
  {
      id: 5,
      dnum: 5,
      writer: "writer66",
      title: "title6555",
      ingredient: "ingredient24",
      recipe: "recipe34",
      url: "url14",
      dish_like: 4,
      ate: 2,
      hit: 0,
      date: "2022-08-26 17:15:03",
      editdate: null,
      t_time: "t_time14",
      m_ingredient: null,
      cookery: "cookery",
      t_weather: "t_weather",
      dpicture: {
          pnum: 0,
          dnum: 5,
          pname: null,
          pictures: "./../image/신라면.jpg"
      }
  }
  ]
  }
)

export default configureStore({
  reducer: { 
    // like : like.reducer,
    tmpdata : tmpdata.reducer
  }
}) 

