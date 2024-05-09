import { configureStore } from "@reduxjs/toolkit";
import scheduleReducer from "./schedule/scheduleSlice";
import navigationReducer from "./navigation/navigationSlice";
export const store = configureStore({
  reducer: { schedule: scheduleReducer, navigation: navigationReducer },
});
