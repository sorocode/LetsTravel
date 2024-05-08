import DateInput from "./DateInput";
import Button from "./Buttons/Button";
import { useDispatch, useSelector } from "react-redux";
import {
  setStartDate,
  setEndDate,
  setDateDif,
} from "../../store/schedule/scheduleSlice";
const SelectDateArea = () => {
  const startDate = useSelector((state) => state.schedule.startDate);
  const endDate = useSelector((state) => state.schedule.endDate);
  const dateDif = useSelector((state) => state.schedule.dateDif);
  const dispatch = useDispatch();
  const onChangeStartDate = (e) => {
    dispatch(setStartDate(e.target.value));
    dispatch(setDateDif());
  };
  const onChangeEndDate = (e) => {
    dispatch(setEndDate(e.target.value));
    dispatch(setDateDif());
  };
  return (
    <div className="flex flex-col gap-4 items-center">
      <p className="font-semibold">얼마나 떠날 예정인가요?</p>
      <div className="flex items-center gap-2">
        <span>🛫</span>
        <DateInput isStart dateValue={startDate} onChange={onChangeStartDate} />
      </div>
      <div className="flex items-center gap-2">
        <span>🛬</span>
        <DateInput
          isStart={false}
          dateValue={endDate}
          onChange={onChangeEndDate}
        />
      </div>
      <div className="text-xl font-semibold">
        총 {dateDif !== "-" ? dateDif + 1 : "-"}일
      </div>
      <div className="flex justify-evenly gap-5 items-center">
        <Button color="7BC9FF" to="..">
          이전
        </Button>
        <Button color="FCD4FF">다음</Button>
      </div>
    </div>
  );
};

export default SelectDateArea;
