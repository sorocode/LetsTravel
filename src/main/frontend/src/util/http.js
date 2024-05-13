import axios from "axios";
import { QueryClient } from "@tanstack/react-query";
export const queryClient = new QueryClient();

const URL = "http://localhost:8080/api";
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
