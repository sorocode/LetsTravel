import axios from "axios";
import OpenAI from "openai";

import { QueryClient } from "@tanstack/react-query";
export const queryClient = new QueryClient();

const URL = "http://localhost:8080/api";
const PLACE_TEXT_URL = "https://places.googleapis.com/v1/places:searchText";
const GOOGLE_API_KEY = import.meta.env.VITE_GOOGLE_KEY;
const GPT_KEY = import.meta.env.VITE_GPT_KEY;
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
            "places.id,places.displayName,places.location,places.types,places.photos,places.googleMapsUri,places.addressComponents",
        },
      }
    );
    return req.data;
  } catch (err) {
    const fetchError = new Error("여행지 불러오기 실패");
    throw fetchError;
  }
};

//Open AI API
const openai = new OpenAI({ apiKey: GPT_KEY, dangerouslyAllowBrowser: true });

export const generateCase = async (city, userSpots, totalDates) => {
  const completion = await openai.chat.completions.create({
    messages: [
      {
        role: "system",
        content: `너는 여행 동선을 분배해주는 인공지능이야. ${city}를 ${
          totalDates + 1
        }일 동안 여행하는 계획을 짜고 있어
    내가 고른 여행지는 다음과 같아. 모든 쓸데없는 말과 해당 여행지에 대한 정보나 설명을 생략하고 결과값을 json형태로 줘(마크다운 문법은 생략해줘.). 예시는 다음과 같아.
    {'day1':['나리타공항', '도쿄역','신주쿠 XX식당', '시부야 스크램블 교차로','XX호텔'],'day2':['요코하마 XX식당', '요코하마 차이나타운', '나리타 공항']}
    ${userSpots.map((spot) => {
      return `${spot.spotName}(위도:${spot.latitude},경도: ${spot.longitude}),`;
    })}`,
      },
    ],
    model: "gpt-4o",
  });

  console.log(completion.choices[0]);
};
