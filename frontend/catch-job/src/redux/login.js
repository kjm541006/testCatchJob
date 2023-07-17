import { createSlice } from "@reduxjs/toolkit";

export const loginSlice = createSlice({
  name: "login",
  initialState: { user: null, token: null, isLoggedIn: false },
  reducers: {
    setCredentials: (state, action) => {
      const { user, accessToken } = action.payload;
      state.user = user;
      state.token = accessToken;
      state.isLoggedIn = true;
    },
    logOut: (state, action) => {
      state.user = null;
      state.token = null;
      state.isLoggedIn = false;
    },
  },
});

export const { setCredentials, logOut } = loginSlice.actions;

export default loginSlice.reducer;

export const selectUser = (state) => state.auth.user;
export const selectCurrentToken = (state) => state.auth.token;
export const selectLoggedIn = (state) => state.auth.isLoggedIn;
