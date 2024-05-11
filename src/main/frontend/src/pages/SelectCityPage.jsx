import { useDispatch, useSelector } from "react-redux";
import { dummyCities } from "../dummyCities";
import { countryData } from "../countryData";
import {
  addCity,
  removeCity,
  setCountry,
} from "../store/schedule/scheduleSlice";
import CityItem from "../components/UI/CityItem";
import SearchResults from "../components/UI/SearchResults";
import { motion, AnimatePresence } from "framer-motion";
import Button from "../components/UI/Buttons/Button";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { fetchCities } from "../util/http";
const SelectCityPage = () => {
  const { data, isPending, isError, error } = useQuery({
    queryKey: ["cities"],
    queryFn: () => fetchCities(countryState),
  });
  // 나라 선택모드인지 도시 선택 모드인지에 대한 상태, 초기값은 false(나라 선택 모드)
  const [isCityMode, setIsCityMode] = useState(false);
  const selectCountry = () => {
    setIsCityMode(true);
  };
  const countryState = useSelector((state) => state.schedule.country);
  const cities = useSelector((state) => state.schedule.cities);
  const dispatch = useDispatch();
  const handleAdd = (item) => {
    dispatch(addCity(item));
  };
  const handleRemove = (item) => {
    dispatch(removeCity(item.id));
  };
  let content;
  if (isPending) {
    //FIXME: 로딩 인디케이터 만들어서 대체하기
    content = <p>도시들을 불러오는 중입니다...</p>;
  }
  if (isError) {
    //FIXME: 에러 컴포넌트 만들어서 대체하기
    content = <p>{error.info?.message || "데이터 불러오기 실패"}</p>;
  }
  if (data) {
    content = (
      <SearchResults items={data}>
        {(item) => {
          const isSelected = JSON.stringify(cities).includes(item.id);
          return (
            <CityItem
              key={item.id}
              title={item.name}
              subTitle={item.name}
              isSelectMode={true}
              onClick={() => {
                isSelected ? handleRemove(item) : handleAdd(item);
              }}
              isSelected={isSelected}
            />
          );
        }}
      </SearchResults>
    );
  }
  return (
    <>
      <div>
        {/* 나라 선택 모드일 때 */}
        {!isCityMode && (
          <SearchResults items={countryData}>
            {(item, index) => {
              if (index < 10) {
                return (
                  <CityItem
                    key={item.id}
                    title={item.countryName_KR}
                    subTitle={item.countryName}
                    isSelectMode={true}
                    onClick={() => {
                      dispatch(setCountry(item.countryCode));
                      selectCountry();
                    }}
                  />
                );
              }
            }}
          </SearchResults>
        )}
        {/* 도시 선택 모드일때 */}
        {isCityMode && content}
      </div>
      <br />
      <BottomSheet
        title={
          <h2 className="text-center text-xs">
            현재 <b>{cities.length}</b>개 도시 선택중
          </h2>
        }
      >
        <div className="flex flex-col gap-2 justify-center items-center mt-2">
          <AnimatePresence>
            {cities.map((city, index) => (
              <motion.span
                key={index}
                className="font-bold w-1/2"
                variants={{
                  hidden: { opacity: 0, scale: 0.5 },
                  visible: { opacity: 1, scale: 1 },
                }}
                initial="hidden"
                animate="visible"
                exit={{ opacity: 0, scale: 0.5 }}
              >
                {city.cityName.toUpperCase()}
              </motion.span>
            ))}
          </AnimatePresence>
        </div>
        <div className="flex justify-center">
          <Button color="#FCD4FF" to="term">
            다음 단계
          </Button>
        </div>
      </BottomSheet>
    </>
  );
};

export default SelectCityPage;
