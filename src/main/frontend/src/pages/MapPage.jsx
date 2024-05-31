import MapContainer from "../components/UI/MapContainer";
import { AdvancedMarker } from "@vis.gl/react-google-maps";
import { useSelector } from "react-redux";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import Button from "../components/UI/Buttons/Button";

import Itinery from "../components/UI/Itinery";
import CustomMarker from "../components/UI/CustomMarker";
import Logo from "../components/UI/Logo";
import { useMutation } from "@tanstack/react-query";
import { addNewPlan } from "../util/http";
import { useState } from "react";

function MapPage() {
  const scheduleSlice = useSelector((state) => state.schedule);
  const scheduleData = scheduleSlice.selectedSchedule;

  console.log("scheduleData", scheduleData);
  const days =
    scheduleData !== undefined ? Object.keys(scheduleData) : undefined;
  const [planName, setPlanName] = useState("");
  // 일정 확정 mutate
  const { data, mutate, isPending, isError, error } = useMutation({
    mutationKey: ["newPlan"],
    mutationFn: () => addNewPlan(planName, scheduleSlice),
  });
  // TODO:pending, error 관련 UI 추가하기
  const addPlanHandler = () => {
    const planNameInput = prompt("여행명을 입력해주세요(예: 도쿄여행)");
    if (planNameInput !== null) {
      setPlanName(planNameInput);
      mutate();
    }
  };
  let msg = "일정 저장";
  if (isPending) {
    msg = "일정 저장 중...";
  }
  if (data) {
    msg = "일정 저장 성공!";
  }
  return (
    <>
      {days !== undefined ? (
        <div>
          <MapContainer>
            {days.map((day) =>
              scheduleData[day].map((spot) => {
                const spotId = spot.id;
                const pos = {
                  lat: spot.latitude,
                  lng: spot.longitude,
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
                        {scheduleData[day].indexOf(spot) + 1}
                      </CustomMarker>
                    </AdvancedMarker>
                  </div>
                );
              })
            )}
          </MapContainer>
          <BottomSheet>
            <div className="flex justify-end mr-5 mt-1">
              <button onClick={addPlanHandler}>{msg}</button>
            </div>
            <Itinery scheduleData={scheduleData} days={days} />
          </BottomSheet>
        </div>
      ) : (
        <div className="flex flex-col items-center justify-center mt-20 gap-4">
          <Logo />
          <p>지도를 보려면 여행지를 추가해주세요!</p>
          <Button to="/select">바로가기</Button>
        </div>
      )}
    </>
  );
}

export default MapPage;
