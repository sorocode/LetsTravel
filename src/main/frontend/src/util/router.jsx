import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../pages/RootLayout";
import SelectCityPage from "../pages/SelectCityPage";
import SelectTermPage from "../pages/SelectTermPage";
import SelectSpotPage from "../pages/SelectSpotPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      {
        index: true,
        element: <SelectCityPage />, // 도시 선택 페이지
      },
      {
        path: "term",
        element: <SelectTermPage />, // 날짜 선택 페이지
      },
      {
        path: ":city",
        element: <SelectSpotPage />, // 여행장소 선택 페이지(params로 도시 명 입력받음)
      },
    ],
  },
]);
