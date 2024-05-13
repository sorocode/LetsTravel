import { useSelector } from "react-redux";
import CityItem from "./CityItem";

const SelectedCityList = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const fallBackTxt = (
    <span className="font-semibold">선택된 도시가 없습니다.</span>
  );
  return (
    <article>
      <h1 className="text-2xl">선택 도시(총 {cities.length}개)</h1>
      <br />
      <div className="h-[90px] overflow-scroll bg-gray-100 border-[1px] rounded-md p-2 shadow-inner">
        {cities.length === 0 ? (
          fallBackTxt
        ) : (
          <ul>
            {cities.map((city) => (
              <li key={city.id} className="border-[1px]">
                <CityItem title={city.cityName} subTitle={city.cityName} />
              </li>
            ))}
          </ul>
        )}
      </div>
    </article>
  );
};

export default SelectedCityList;
