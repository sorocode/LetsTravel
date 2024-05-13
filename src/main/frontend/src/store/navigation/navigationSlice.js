import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  mode: "country",
};

export const navigationSlice = createSlice({
  name: "navigation",
  initialState,
  reducers: {
    chagneMode: (state) => {
      state.mode = state.mode === "country" ? "city" : "country";
    },
  },
});

// Action creators are generated for each case reducer function
export const { chagneMode } = navigationSlice.actions;

export default navigationSlice.reducer;
