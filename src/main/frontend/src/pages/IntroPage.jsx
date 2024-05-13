import Button from "../components/UI/Buttons/Button";

const IntroPage = () => {
  return (
    <div className="flex justify-center items-center mt-30">
      <Button to="select" color="#B7E2FF">
        ✈️여행 일정 만들러가기
      </Button>
    </div>
  );
};

export default IntroPage;
