import { useSelector } from "react-redux";
import CityItem from "./CityItem";

const SelectedCityList = () => {
  const cities = useSelector((state) => state.schedule.cities);
  console.log(cities);
  return (
    <article>
      <h1 className="text-xl">선택 도시</h1>
      <ul>
        {cities.map((city) => (
          <li key={city.id}>
            <CityItem cityName={city.cityName} cityCountry={city.countryName} />
          </li>
        ))}
      </ul>
    </article>
  );
};

export default SelectedCityList;
