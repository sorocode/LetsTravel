import { useQuery } from "@tanstack/react-query";
import { fetchAllPlans } from "../util/http";
import { Card, CardSkeleton } from "../components/UI/Card/Card";
import ErrorPage from "../components/UI/Error/ErrorPage";

const MyTripsPage = () => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ["plans"],
    queryFn: () => fetchAllPlans(-1),
  });
  let content;
  if (isPending) {
    content = (
      <>
        <CardSkeleton />
        <CardSkeleton />
        <CardSkeleton />
        <CardSkeleton />
        <CardSkeleton />
        <CardSkeleton />
      </>
    );
  } else if (isError) {
    content = (
      <ErrorPage
        title="여행 가져오기 실패"
        message={error.info?.message || "나중에 다시 시도해주세요."}
      />
    );
  } else if (data) {
    content = (
      <ul>
        {data.map((item, index) => {
          return (
            <li key={index}>
              <Card
                planName={item.planName ?? "나의 여행"}
                countryCode={item.countryCode}
                startDate={item.planStart}
                planNDays={item.planNDays}
                planSeq={item.planSeq}
              />
            </li>
          );
        })}
      </ul>
    );
  }
  return (
    <div className="overflow-y-scroll">
      <p className="mx-6 mt-2">나의 여행</p>
      {content}
    </div>
  );
};

export default MyTripsPage;
