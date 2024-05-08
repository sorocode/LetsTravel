import { createSlice } from "@reduxjs/toolkit";
import { toMySQLDate } from "../../util/toMySQLDate";

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(today.getDate() + 1);

const initialState = {
  cities: [], //도시 리스트
  startDate: toMySQLDate(today), //시작일 오늘로 설정
  endDate: toMySQLDate(tomorrow), //종료일 내일로 설정
  dateDif: 1, // 종료일과 시작일 사이의 날짜 차이
};

export const scheduleSlice = createSlice({
  name: "schedule",
  initialState,
  reducers: {
    //도시 추가
    addCity: (state, action) => {
      const newCity = action.payload;
      const existingCity = state.cities.find((item) => item.id === newCity.id);
      if (!existingCity) {
        state.cities.push({
          id: newCity.id,
          cityName: newCity.cityName,
          countryName: newCity.countryName,
        });
      } else {
        alert("이미 추가된 도시입니다.");
      }
    },
    // 도시 삭제
    removeCity: (state, action) => {
      const id = action.payload;
      const existingCity = state.cities.find((item) => item.id === id);
      if (!existingCity) {
        alert("리스트에 존재하지 않는 도시입니다. 다시 시도해주세요.");
        return;
      } else {
        state.cities = state.cities.filter((city) => city.id !== id);
        // state.totalQuantity--;
      }
    },
    //TODO:action 하나로 통일하기
    setStartDate: (state, action) => {
      state.startDate = action.payload;
    },
    setEndDate: (state, action) => {
      state.endDate = action.payload;
    },
  },
});

// Action creators are generated for each case reducer function
export const { addCity, removeCity, setStartDate, setEndDate } =
  scheduleSlice.actions;

export default scheduleSlice.reducer;
