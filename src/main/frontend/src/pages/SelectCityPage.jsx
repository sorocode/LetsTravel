import { useDispatch, useSelector } from "react-redux";
import { dummyCities } from "../dummyCities";
import { addCity } from "../store/schedule/scheduleSlice";
import SearchBar from "../components/UI/SearchBar";
import CityItem from "../components/UI/CityItem";
import SearchResults from "../components/UI/SearchResults";
const SelectCityPage = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const dispatch = useDispatch();
  return (
    <div>
      {/* <SearchBar searchBarId="city" placeHolder="어디로 떠나시나요?" />
      <p className="my-2 w-3/4">✈️요즘 핫한 해외 여행지여행지</p>
      {dummyCities.map((city) => (
        <CityItem
          key={city.id}
          cityName={city.cityName}
          cityCountry={city.countryName}
          onClick={() => dispatch(addCity(city))}
        />
      ))} */}
      <SearchResults items={dummyCities}>
        {(item) => (
          <CityItem
            key={item.id}
            cityName={item.cityName}
            cityCountry={item.countryName}
            onClick={() => dispatch(addCity(item))}
          />
        )}
      </SearchResults>
      <br />
      <h2 className="text-center">
        현재 <b>{cities.length}</b>개 도시 선택중
      </h2>
      <div className="flex gap-2 justify-center">
        {cities.map((city, index) => (
          <span key={index} className="font-bold">
            {city.cityName.toUpperCase()}
          </span>
        ))}
      </div>
    </div>
  );
};

export default SelectCityPage;
