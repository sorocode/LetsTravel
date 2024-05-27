import { createSlice } from "@reduxjs/toolkit";
import { toMySQLDate } from "../../util/toMySQLDate";
import { getDaysDifference } from "../../util/getDateDif";

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(today.getDate() + 1);

const initialState = {
  country: [],
  cities: [], //도시 리스트
  spots: [], //여행지 리스트
  selectedSchedule: {
    day1: [
      {
        id: "ChIJVze90XnzImARoRp3YqEpbtU",
        spotName: "나리타 국제공항",
        latitude: 35.770178,
        longitude: 140.3843215,
      },
      {
        id: "ChIJaZF5wHmLGGARycW1JhshDjY",
        spotName: "日本料理 龍吟",
        latitude: 35.6742704,
        longitude: 139.7592781,
      },
      {
        id: "ChIJ9fcZgFWJGGARba-URZY-YKw",
        spotName: "Tapas Molecular Bar",
        latitude: 35.6870501,
        longitude: 139.77304949999998,
      },
      {
        id: "ChIJzxA2KWWLGGARDmDYTOuQ_jc",
        spotName: "House",
        latitude: 35.661057,
        longitude: 139.721872,
      },
      {
        id: "ChIJAW9ZwviNGGARtucA2V4IOwU",
        spotName: "Shibuya spot",
        latitude: 35.659404200000004,
        longitude: 139.6997233,
      },
      {
        id: "ChIJ1cz1KtbyGGARvFXyNAgoMz0",
        spotName: "APA 호텔 & 리조트 니시신주쿠-고초메-에키 타워",
        latitude: 35.6896389,
        longitude: 139.68356,
      },
    ],
    day2: [
      {
        id: "ChIJ1cz1KtbyGGARvFXyNAgoMz0",
        spotName: "APA 호텔 & 리조트 니시신주쿠-고초메-에키 타워",
        latitude: 35.6896389,
        longitude: 139.68356,
      },
      {
        id: "ChIJVze90XnzImARoRp3YqEpbtU",
        spotName: "나리타 국제공항",
        latitude: 35.770178,
        longitude: 140.3843215,
      },
    ],
  },
  startDate: toMySQLDate(today), //시작일 오늘로 설정
  endDate: toMySQLDate(tomorrow), //종료일 내일로 설정
  dateDif: 1, // 종료일과 시작일 사이의 날짜 차이
};

export const scheduleSlice = createSlice({
  name: "schedule",
  initialState,
  reducers: {
    setCountry: (state, action) => {
      state.country = action.payload;
    },
    unsetCountry: (state) => {
      state.country = [];
      state.cities = [];
      state.spots = [];
    },
    //도시 추가
    addCity: (state, action) => {
      const newCity = action.payload;
      const existingCity = state.cities.find((item) => item.id === newCity.id);
      if (!existingCity) {
        state.cities.push({
          id: newCity.id,
          cityName: newCity.cityName,
          cityNameTranslated: newCity.cityNameTranslated,
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
    //도시 추가
    addSpot: (state, action) => {
      const newSpot = action.payload;
      const existingspot = state.spots.find((item) => item.id === newSpot.id);
      if (!existingspot) {
        state.spots.push({
          id: newSpot.id,
          spotName: newSpot.displayName.text,
          latitude: newSpot.location.latitude, //위도
          longitude: newSpot.location.longitude, //경도
          googleMapsUri: newSpot.googleMapsUri,
        });
      } else {
        alert("이미 추가된 여행지입니다.");
      }
    },
    // 도시 삭제
    removeSpot: (state, action) => {
      const id = action.payload;
      const existingspot = state.spots.find((item) => item.id === id);
      if (!existingspot) {
        alert("리스트에 존재하지 않는 여행지입니다. 다시 시도해주세요.");
        return;
      } else {
        state.spots = state.spots.filter((spot) => spot.id !== id);
        // state.totalQuantity--;
      }
    },
    setStartDate: (state, action) => {
      state.startDate = action.payload;
    },
    setEndDate: (state, action) => {
      state.endDate = action.payload;
    },
    setDateDif: (state) => {
      state.dateDif =
        getDaysDifference(state.startDate, state.endDate) < 0
          ? "-"
          : getDaysDifference(state.startDate, state.endDate);
    },
  },
});

// Action creators are generated for each case reducer function
export const {
  setCountry,
  unsetCountry,
  addCity,
  removeCity,
  addSpot,
  removeSpot,
  setStartDate,
  setEndDate,
  setDateDif,
} = scheduleSlice.actions;

export default scheduleSlice.reducer;
