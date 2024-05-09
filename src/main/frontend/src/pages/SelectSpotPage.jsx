import { useParams } from "react-router-dom";

function SelectSpotPage() {
  const params = useParams();

  return (
    <div>
      <h1>여행스팟을 선택하는 페이지입니다.</h1>
      <p>
        {params.city.includes("noCity") &&
          "도시가 선택되지 않았습니다. 도시를 선택해주세요."}
        {!params.city.includes("noCity") && `현재 선택된 장소: ${params.city}`}
      </p>
    </div>
  );
}

export default SelectSpotPage;
