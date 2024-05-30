import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../pages/RootLayout";
import SelectCityPage from "../pages/SelectCityPage";
import SelectTermPage from "../pages/SelectTermPage";
import SelectSpotPage from "../pages/SelectSpotPage";
import MapPage from "../pages/MapPage";
import ProfilePage from "../pages/ProfilePage";
import IntroPage from "../pages/IntroPage";
import ErrorPage from "../components/UI/Error/ErrorPage";
import MyTripsPage from "../pages/MyTripsPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    errorElement: (
      <ErrorPage
        title="에러 발생"
        message="알 수 없는 에러가 발생했습니다. 다시 시도해주십시오."
      />
    ),
    children: [
      {
        index: "true",
        element: <IntroPage />,
      },
      {
        path: "/select",
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
        ],
      },
      {
        path: "trips",
        element: <MyTripsPage />,
      },
      {
        path: "profile",
        element: <ProfilePage />,
      },
      {
        path: "error",
        element: <ErrorPage title="에러 발생" />,
      },
    ],
  },
]);
