import DateInput from "./DateInput";
import Button from "./Buttons/Button";

const SelectDateArea = () => {
  return (
    <div className="flex flex-col gap-4 items-center">
      <div className="flex items-center gap-2">
        <span>🛫 출발</span>
        <DateInput isStart />
      </div>
      <div className="flex items-center gap-2">
        <span>🛬 도착</span>
        <DateInput isStart={false} />
      </div>
      <div>
        <Button color="FCD4FF">다음 단계</Button>
      </div>
    </div>
  );
};

export default SelectDateArea;
