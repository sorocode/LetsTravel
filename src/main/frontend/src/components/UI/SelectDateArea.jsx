import DateInput from "./DateInput";
import Button from "./Buttons/Button";
import { useSelector } from "react-redux";
const SelectDateArea = () => {
  const startDate = useSelector((state) => state.schedule.startDate);
  const endDate = useSelector((state) => state.schedule.endDate);
  return (
    <div className="flex flex-col gap-4 items-center">
      <div className="flex items-center gap-2">
        <span>🛫 출발</span>
        <DateInput isStart dateValue={startDate} />
      </div>
      <div className="flex items-center gap-2">
        <span>🛬 도착</span>
        <DateInput isStart={false} dateValue={endDate} />
      </div>
      <div>
        <Button color="FCD4FF">다음 단계</Button>
      </div>
    </div>
  );
};

export default SelectDateArea;
