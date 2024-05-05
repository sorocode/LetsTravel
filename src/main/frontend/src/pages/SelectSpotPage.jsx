import { useParams } from "react-router-dom";

function SelectSpotPage() {
  const params = useParams();
  return (
    <div>
      <h1>여행스팟을 선택하는 페이지입니다.</h1>
      <p>
        현재 선택된 장소: <b>{params.city}</b>
      </p>
    </div>
  );
}

export default SelectSpotPage;
