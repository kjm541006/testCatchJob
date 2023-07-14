import { configureStore, createSlice } from "@reduxjs/toolkit";

const loadingSlice = createSlice({
  name: "loading",
  initialState: {
    isLoading: true,
  },
  reducers: {
    startLoading: (state) => {
      state.isLoading = true;
    },
    stopLoading: (state) => {
      state.isLoading = false;
    },
  },
});

export const { startLoading, stopLoading } = loadingSlice.actions;

const store = configureStore({
  reducer: {
    loading: loadingSlice.reducer,
  },
});

export default store;
