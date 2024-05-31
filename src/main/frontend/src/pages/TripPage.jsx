import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { fetchPlan } from "../util/http";
import ErrorPage from "../components/UI/Error/ErrorPage";
import { getEndDate } from "../util/getEndDate";
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
    content = (
      <>
        <h1>{data.planInfo.planName ?? "나의 여행"}</h1>
        <p>
          {data.planInfo.planStart}~
          {getEndDate(data.planInfo.planStart, data.planInfo.planNDays)}
        </p>
        <ul>
          {data.schedules.map((item, index) => {
            return <li key={index}>{item.placeName ?? "여행지"}</li>;
          })}
        </ul>
      </>
    );
  }
  return <h1>{content}</h1>;
};

export default TripPage;
