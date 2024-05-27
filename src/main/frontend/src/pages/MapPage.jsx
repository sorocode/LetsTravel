import MapContainer from "../components/UI/MapContainer";
import { AdvancedMarker } from "@vis.gl/react-google-maps";
import { useSelector } from "react-redux";

function MapPage() {
  const scheduleData = useSelector((state) => state.schedule.selectedSchedule);
  console.log(scheduleData);
  const days = Object.keys(scheduleData);

  return (
    <>
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
                  <div
                    style={{ backgroundColor: bgColor }}
                    className="rounded-[86px] border-solid border-[#000000] border p-2.5 flex flex-col gap-2.5 items-center justify-center shrink-0 w-[30px] h-[30px] relative "
                  >
                    <span>{day}</span>
                  </div>
                </AdvancedMarker>
              </div>
            );
          })
        )}
      </MapContainer>
    </>
  );
}

export default MapPage;
