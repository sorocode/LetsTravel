import { useDispatch, useSelector } from "react-redux";
import { dummyCities } from "../dummyCities";
import { addCity } from "../store/schedule/scheduleSlice";
import SearchBar from "../components/UI/SearchBar";
const SelectCityPage = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const dispatch = useDispatch();
  return (
    <div className="flex flex-col items-center">
      <SearchBar searchBarId="city" placeHolder="어디로 떠나시나요?" />
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
