import { configureStore } from "@reduxjs/toolkit";
import scheduleReducer from "./schedule/scheduleSlice";
export const store = configureStore({
  reducer: { schedule: scheduleReducer },
});
