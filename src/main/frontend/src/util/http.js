import axios from "axios";
import OpenAI from "openai";

import { QueryClient } from "@tanstack/react-query";
import { transformPlaceData } from "./transformPlaceData";
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
      {
        textQuery: `${searchTerm} in ${city}`,
        languageCode: "ko",
      },
      {
        headers: {
          "Content-Type": "application/json",
          "X-Goog-Api-Key": GOOGLE_API_KEY,
          "X-Goog-FieldMask":
            "places.id,places.displayName,places.location,places.types,places.googleMapsUri,places.addressComponents,places.primaryType,places.primaryTypeDisplayName,places.formattedAddress",
        },
      }
    );
    return req.data;
  } catch (err) {
    const fetchError = new Error("여행지 불러오기 실패");
    throw fetchError;
  }
};
// 기존 데이터 형식
// {
//     "id": "ChIJCewJkL2LGGAR3Qmk0vCTGkg",
//     "types": [
//         "landmark",
//         "art_gallery",
//         "tourist_attraction",
//         "shopping_mall",
//         "point_of_interest",
//         "establishment"
//     ],
//     "addressComponents": [
//         {
//             "longText": "8",
//             "shortText": "8",
//             "types": [
//                 "premise"
//             ],
//             "languageCode": "ko"
//         },
//         {
//             "longText": "2",
//             "shortText": "2",
//             "types": [
//                 "sublocality_level_4",
//                 "sublocality",
//                 "political"
//             ],
//             "languageCode": "en"
//         },
//         {
//             "longText": "4-chōme",
//             "shortText": "4-chōme",
//             "types": [
//                 "sublocality_level_3",
//                 "sublocality",
//                 "political"
//             ],
//             "languageCode": "ja-Latn"
//         },
//         {
//             "longText": "Shibakōen",
//             "shortText": "Shibakōen",
//             "types": [
//                 "sublocality_level_2",
//                 "sublocality",
//                 "political"
//             ],
//             "languageCode": "ja-Latn"
//         },
//         {
//             "longText": "Minato City",
//             "shortText": "Minato City",
//             "types": [
//                 "locality",
//                 "political"
//             ],
//             "languageCode": "en"
//         },
//         {
//             "longText": "Tokyo",
//             "shortText": "Tokyo",
//             "types": [
//                 "administrative_area_level_1",
//                 "political"
//             ],
//             "languageCode": "en"
//         },
//         {
//             "longText": "일본",
//             "shortText": "JP",
//             "types": [
//                 "country",
//                 "political"
//             ],
//             "languageCode": "ko"
//         },
//         {
//             "longText": "105-0011",
//             "shortText": "105-0011",
//             "types": [
//                 "postal_code"
//             ],
//             "languageCode": "ko"
//         }
//     ],
//     "location": {
//         "latitude": 35.6585805,
//         "longitude": 139.7454329
//     },
//     "googleMapsUri": "https://maps.google.com/?cid=5195627782660688349",
//     "displayName": {
//         "text": "도쿄 타워",
//         "languageCode": "ko"
//     }
// }

//Place 추가 API
export const addNewPlace = async (placeData) => {
  // API 요청 방식에 맞게 데이터 변경
  const transFormedPlaceData = transformPlaceData(placeData);
  try {
    const response = await axios.post(
      URL + "/place",
      JSON.stringify(transFormedPlaceData),
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (err) {
    throw new Error(err);
  }
};
// Plan 추가 API
export const addNewPlan = async (planName, schedules) => {
  const selectedSchedule = schedules.selectedSchedule;
  try {
    const schedulesArray = [];
    // 각 날짜와 그에 해당하는 장소 리스트를 순회
    Object.keys(selectedSchedule).forEach((dateSeq) => {
      selectedSchedule[dateSeq].forEach((spot, index) => {
        schedulesArray.push({
          placeId: spot.id,
          dateSeq: parseInt(dateSeq), // 날짜 시퀀스
          visitSeq: index + 1, // 방문 시퀀스 (인덱스는 0부터 시작하므로 1을 더함)
          visitTime: "00:00:00", // 방문 시간 (적당한 값으로 설정)
        });
      });
    });
    // 서버로 보낼 데이터
    let newPlan = {
      plan: {
        memSeq: -1,
        planName: planName || `나의 ${schedules.country} 여행`,
        countryCode: schedules.country.countryCode,
        planStart: schedules.startDate,
        planNDays: schedules.dateDif + 1,
      },
      schedules: schedulesArray,
    };
    const data = JSON.stringify(newPlan);
    const req = await axios.post(URL + "/plan", data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return req.data;
  } catch (err) {
    const postError = new Error("저장 실패");
    throw postError;
  }
};

//모든 플랜 가져오기 API
export const fetchAllPlans = async (memSeq) => {
  try {
    const res = await axios(URL + "/member/" + memSeq + "/plan");
    return res.data;
  } catch {
    const fetchError = new Error("플랜 가져오기 실패");
    throw fetchError;
  }
};
// 특정 플랜 가져오기 API
export const fetchPlan = async (planSeq) => {
  try {
    const res = await axios(`${URL}/plan/${planSeq}`);
    return res.data;
  } catch {
    const fetchError = new Error("플랜 가져오기 실패");
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
                "1":[{"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}, {"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}],
              "2": [{"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}, {"id": "ChIJVze90XnzImARoRp3YqEpbtU", "spotName": "나리타공항", "latitude":35.770178, "longitude":140.3843215}]
            }
              key값 1,2,3..은 1일차 2일차 3일차를 뜻해.공항 입력시에는 첫날 첫 여행지와 마지막날 마지막 여행지는 공항 혹은 항구로 넣어줘. 호텔 등 숙소 입력시에는 마지막날을 제외한 매일의 마지막 일정으로 넣어줘.
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
