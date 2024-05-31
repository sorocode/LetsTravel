import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { fetchPlan } from "../util/http";
import ErrorPage from "../components/UI/Error/ErrorPage";
import { getEndDate } from "../util/getEndDate";
import MapContainer from "../components/UI/MapContainer";
import { AdvancedMarker } from "@vis.gl/react-google-maps";
import CustomMarker from "../components/UI/CustomMarker";
const TripPage = () => {
  const params = useParams();
  console.log(params.planSeq);

  const { data, isPending, isError, error } = useQuery({
    queryKey: ["plan", params.planSeq],
    queryFn: () => fetchPlan(params.planSeq),
  });
  let content;
  if (isPending) {
    content = <p>데이터를 불러오는 중</p>;
  }
  if (isError) {
    content = (
      <ErrorPage
        title="플랜 가져오기 실패"
        message={error.info?.message || "플랜 가져오기 실패"}
      />
    );
  }
  if (data) {
    const days = Object.keys(data.schedules);
    console.log("plan", data.schedules);

    content = (
      <>
        <MapContainer>
          {days.map((day) =>
            data.schedules[day].map((spot) => {
              const spotId = spot.id;
              const pos = {
                lat: spot.location.latitude,
                lng: spot.location.longitude,
              };
              let bgColor;
              switch (parseInt(day)) {
                case 1:
                  bgColor = "#F3B385";
                  break;
                case 2:
                  bgColor = "#c985f3";
                  break;
                case 3:
                  bgColor = "#85F389";
                  break;
                default:
                  bgColor = "#7BC9FF";
                  break;
              }
              return (
                <div key={spotId}>
                  <AdvancedMarker position={pos}>
                    <CustomMarker color={bgColor}>
                      {data.schedules[day].indexOf(spot) + 1}
                    </CustomMarker>
                  </AdvancedMarker>
                </div>
              );
            })
          )}
        </MapContainer>
        {/* <h1>{data.planInfo.planName ?? "나의 여행"}</h1>
        <p>
          {data.planInfo.planStart}~
          {getEndDate(data.planInfo.planStart, data.planInfo.planNDays)}
        </p>
        <ul>
          {data.schedules.map((item, index) => {
            return <li key={index}>{item.placeName ?? "여행지"}</li>;
          })}
        </ul> */}
      </>
    );
  }
  return <>{content}</>;
};

export default TripPage;
