import axios from "axios";
import { QueryClient } from "@tanstack/react-query";
export const queryClient = new QueryClient();

const URL = "http://localhost:8080/api";
const PLACE_TEXT_URL = "https://places.googleapis.com/v1/places:searchText";
const GOOGLE_API_KEY = import.meta.env.VITE_GOOGLE_KEY;
// countryCode 보내면 도시 불러옴
export const fetchCities = async (countryCode) => {
  try {
    const req = await axios.get(URL + `/city/${countryCode}`);
    // console.log(req);
    return req.data;
  } catch (err) {
    const fetchError = new Error("도시 불러오기 실패");
    throw fetchError;
  }
};

//Place API 텍스트 검색 관련 로직
export const fetchSpots = async (searchTerm, city) => {
  try {
    const req = await axios.post(
      PLACE_TEXT_URL,
      { textQuery: `${city}의 ${searchTerm}`, languageCode: "ko" },
      {
        headers: {
          "Content-Type": "application/json",
          "X-Goog-Api-Key": GOOGLE_API_KEY,
          "X-Goog-FieldMask":
            "places.id,places.displayName,places.location,places.types,places.photos",
        },
      }
    );
    // console.log(req);
    return req.data;
  } catch (err) {
    const fetchError = new Error("여행지 불러오기 실패");
    throw fetchError;
  }
};
