import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { useSelector } from "react-redux";

const MapContainer = ({ children }) => {
  const scheduleData = useSelector((state) => state.schedule.selectedSchedule);
  console.log(scheduleData);
  //FIXME: 하드코딩한 거 수정하기
  const position = { lat: 35.659404200000004, lng: 139.6997233 };

  return (
    <>
      <APIProvider apiKey={import.meta.env.VITE_GOOGLE_KEY}>
        <div style={{ height: "100vh", width: "100%" }}>
          {/* TODO:지도 드래그해서 이동할 수 있도록 만들기 */}
          <Map
            defaultZoom={10}
            center={position}
            mapId={import.meta.env.VITE_GOOGLE_MAP_ID}
          >
            {children}
          </Map>
        </div>
      </APIProvider>
    </>
  );
};

export default MapContainer;
