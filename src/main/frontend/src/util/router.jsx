import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../pages/RootLayout";
import SelectCityPage from "../pages/SelectCityPage";
import SelectTermPage from "../pages/SelectTermPage";
import SelectSpotPage from "../pages/SelectSpotPage";
import MapPage from "../pages/MapPage";
import ProfilePage from "../pages/ProfilePage";

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
      //FIXME:나중에 도시별로 스팟 선택할 수 있도록 라우팅 경로 변경하기
      {
        path: "spot/:city",
        element: <SelectSpotPage />, // 여행장소 선택 페이지(params로 도시 명 입력받음)
      },
      {
        path: "map",
        element: <MapPage />, // 지도가 나타나는 페이지입니다.
      },
      {
        path: "profile",
        element: <ProfilePage />,
      },
    ],
  },
]);
