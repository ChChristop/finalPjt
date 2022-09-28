import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { useMemo } from "react";
import { useDispatch } from "react-redux";
import { bindActionCreators } from "redux";

const initialState = {
  user: {},
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login(state, action) {
      state.user = action.payload;
    },
    logout(state) {
      state.user = {};
    },
  },
});

export default authSlice.reducer;
export const { login, logout } = authSlice.actions;

export function useAuthActions() {
  const dispatch = useDispatch();
  return useMemo(
    () => bindActionCreators({ login, logout }, dispatch),
    [dispatch]
  );
}
