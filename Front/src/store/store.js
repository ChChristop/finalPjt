import { configureStore, createSlice } from "@reduxjs/toolkit";
import { combineReducers } from "redux";
import auth from "./authSlice";

const rootReducer = combineReducers({
  auth,
});

export default rootReducer;

export const store = configureStore({ reducer: rootReducer });
