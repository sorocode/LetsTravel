import Button from "../components/UI/Buttons/Button";

const IntroPage = () => {
  return (
    <div className="flex flex-col justify-center items-center mt-36">
      <h1 className="text-2xl font-bold">LetsTravel</h1>
      <Button to="select" color="#B7E2FF">
        ✈️여행 일정 만들러가기
      </Button>
    </div>
  );
};

export default IntroPage;
