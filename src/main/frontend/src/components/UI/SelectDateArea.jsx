import DateInput from "./DateInput";
import Button from "./Buttons/Button";
import { useDispatch, useSelector } from "react-redux";
import { setStartDate, setEndDate } from "../../store/schedule/scheduleSlice";
const SelectDateArea = () => {
  const startDate = useSelector((state) => state.schedule.startDate);
  const endDate = useSelector((state) => state.schedule.endDate);
  const dispatch = useDispatch();
  const onChangeStartDate = (e) => {
    //TODO: 날짜 바꾸는 dispatch
    dispatch(setStartDate(e.target.value));
  };
  const onChangeEndDate = (e) => {
    //TODO: 날짜 바꾸는 dispatch
    dispatch(setEndDate(e.target.value));
  };
  return (
    <div className="flex flex-col gap-4 items-center">
      <div className="flex items-center gap-2">
        <span>🛫 출발</span>
        <DateInput isStart dateValue={startDate} onChange={onChangeStartDate} />
      </div>
      <div className="flex items-center gap-2">
        <span>🛬 도착</span>
        <DateInput
          isStart={false}
          dateValue={endDate}
          onChange={onChangeEndDate}
        />
      </div>
      <div>
        <Button color="FCD4FF">다음 단계</Button>
      </div>
    </div>
  );
};

export default SelectDateArea;
