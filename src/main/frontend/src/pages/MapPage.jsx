import MapContainer from "../components/UI/MapContainer";
import { AdvancedMarker } from "@vis.gl/react-google-maps";
import { useSelector } from "react-redux";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import Button from "../components/UI/Buttons/Button";
import logoIcon from "/logo.png";

import Itinery from "../components/UI/Itinery";
import CustomMarker from "../components/UI/CustomMarker";
function MapPage() {
  const scheduleData = useSelector((state) => state.schedule.selectedSchedule);

  const days =
    scheduleData !== undefined ? Object.keys(scheduleData) : undefined;
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
                console.log("pos", pos);
                let bgColor;
                console.log("day", day);
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
            <Itinery scheduleData={scheduleData} days={days} />
          </BottomSheet>
        </div>
      ) : (
        <div className="flex flex-col items-center justify-center mt-20 gap-4">
          <div className="flex flex-col justify-center items-center">
            <img src={logoIcon} alt="logo icon" width="30%" />
            <span className="font-ShadowsIntoLight text-xl">LetsTravel</span>
          </div>
          <p>지도를 보려면 여행지를 추가해주세요!</p>
          <Button to="/select">바로가기</Button>
          {/* FIXME: 개발편의를 위해 여기 뒀지만 나중에는 MapContainer 아래로 올릴 것 */}
        </div>
      )}
    </>
  );
}

export default MapPage;
