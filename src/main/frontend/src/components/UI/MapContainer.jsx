import {
  APIProvider,
  AdvancedMarker,
  Map,
  Pin,
} from "@vis.gl/react-google-maps";

const MapContainer = () => {
  const position = {
    lat: 35.6812362,
    lng: 139.76649,
  };
  const subposition = {
    lat: 35.12,
    lng: 139.76649,
  };
  return (
    <>
      <APIProvider apiKey={import.meta.env.VITE_GOOGLE_KEY}>
        <div style={{ height: "100vh", width: "100%" }}>
          <Map
            minZoom={13}
            center={position}
            mapId={import.meta.env.VITE_GOOGLE_MAP_ID}
          >
            <AdvancedMarker position={position}>
              {/* <img src={reactIcon} alt="react Icon" /> */}
            </AdvancedMarker>
            <AdvancedMarker position={subposition}></AdvancedMarker>
          </Map>
        </div>
      </APIProvider>
    </>
  );
};

export default MapContainer;
