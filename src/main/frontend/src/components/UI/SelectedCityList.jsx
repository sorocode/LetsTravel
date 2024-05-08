import { useSelector } from "react-redux";
import CityItem from "./CityItem";

const SelectedCityList = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const fallBackTxt = (
    <span className="font-semibold">선택된 도시가 없습니다.</span>
  );
  return (
    <article>
      <h1 className="text-2xl">선택 도시</h1>
      {cities.length === 0 ? (
        fallBackTxt
      ) : (
        <ul>
          {cities.map((city) => (
            <li key={city.id}>
              <CityItem
                cityName={city.cityName}
                cityCountry={city.countryName}
              />
            </li>
          ))}
        </ul>
      )}
    </article>
  );
};

export default SelectedCityList;
