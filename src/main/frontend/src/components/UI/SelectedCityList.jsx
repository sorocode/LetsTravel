import { useSelector } from "react-redux";
import CityItem from "./CityItem";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";

const SelectedCityList = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const fallBackTxt = (
    <div className="flex flex-col items-center">
      <p className="font-semibold">선택된 도시가 없습니다.</p>
      <Link to="/select">
        <Button>선택하기</Button>
      </Link>
    </div>
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
                <CityItem
                  title={city.cityNameTranslated}
                  subTitle={city.cityName}
                />
              </li>
            ))}
          </ul>
        )}
      </div>
    </article>
  );
};

export default SelectedCityList;
