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
        content: `너는 여행 동선을 분배해주는 인공지능이야. 
              도시와 여행지 목록, 여행 일정을 보고 날짜별로 여행지들을 적절하게 배분해줘.
              나는 ${city}를 ${totalDates + 1}일 동안 여행하는 계획을 짜고 있어
              내가 고른 여행지는 다음과 같아. 
              ${userSpots.map((spot) => {
                return `${spot.spotName}(id:${spot.id},위도:${spot.latitude},경도: ${spot.longitude}),`;
              })}
              모든 쓸데없는 말과 해당 여행지에 대한 정보나 설명을 생략하고 결과값을 json형태로 줘(마크다운 문법은 생략해줘.). 예시는 다음과 같아.
              {
                "day1":[{"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}, {"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}],
              "day2": [{"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}, {"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}]
            }
              공항 입력시에는 첫날 첫 여행지와 마지막날 마지막 여행지는 공항 혹은 항구로 넣어줘. 호텔 등 숙소 입력시에는 마지막날을 제외한 매일의 마지막 일정으로 넣어줘.
              그리고 내가 선택한 여행지 외에는 절대 넣지마. `,
      },
    ],
    model: "gpt-4o",
  });

  // console.log(completion.choices[0]);
  const gptAnswer = completion.choices[0].message.content;
  console.log(gptAnswer);
  return gptAnswer;
};
