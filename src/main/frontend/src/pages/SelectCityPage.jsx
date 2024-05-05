import { useDispatch, useSelector } from "react-redux";
import { dummyCities } from "../dummyCities";
import { addCity } from "../store/schedule/scheduleSlice";
const SelectCityPage = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const dispatch = useDispatch();
  return (
    <div>
      <h1>도시 선택</h1>
      {dummyCities.map((city) => (
        <p key={city.id}>
          <button onClick={() => dispatch(addCity(city))}>
            {city.cityName}
          </button>
        </p>
      ))}
      <br />
      <h2>현재 {cities.length}개 도시 선택중</h2>
      {cities.map((city, index) => (
        <p key={index}>{city.cityName}</p>
      ))}
    </div>
  );
};

export default SelectCityPage;
