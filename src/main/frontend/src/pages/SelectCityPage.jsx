import { useDispatch, useSelector } from "react-redux";

import { countryData } from "../countryData";
import {
  addCity,
  removeCity,
  setCountry,
  unsetCountry,
} from "../store/schedule/scheduleSlice";
import { chagneMode } from "../store/navigation/navigationSlice";
import CityItem from "../components/UI/CityItem";
import SearchResults from "../components/UI/SearchResults";
import { motion, AnimatePresence } from "framer-motion";
import Button from "../components/UI/Buttons/Button";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import { useQuery } from "@tanstack/react-query";
import { fetchCities } from "../util/http";
import ErrorPage from "../components/UI/Error/ErrorPage";
import { useNavigate } from "react-router-dom";

const SelectCityPage = () => {
  const navigate = useNavigate();
  const countryState = useSelector((state) => state.schedule.country); //나라 상태
  const mode = useSelector((state) => state.navigation.mode);
  const { data, isPending, isError, error } = useQuery({
    queryKey: ["cities", { country: countryState }],
    queryFn: () => fetchCities(countryState.countryCode),
    enabled: countryState.countryCode !== undefined, // 작동 조건 추가
  });

  const changeMode = () => {
    // console.log("모드를 변경합니다.");
    dispatch(chagneMode());
  };
  const cities = useSelector((state) => state.schedule.cities);
  const dispatch = useDispatch();
  const handleAdd = (item) => {
    dispatch(addCity(item));
  };
  const handleRemove = (item) => {
    dispatch(removeCity(item.id));
  };

  //도시 불러오기 오류 시 인트로 화면으로 돌아갈 수 있게 하는 기능
  const handleBack = () => {
    dispatch(unsetCountry());
    dispatch(chagneMode());
    navigate("..");
  };
  // 나라 목록 컨텐츠
  let countryContent = (
    <SearchResults items={countryData} apiMode={false}>
      {(item, index) => {
        if (index < 10) {
          return (
            <CityItem
              key={item.id}
              title={item.countryName_KR}
              subTitle={item.countryName}
              isSelectMode={true}
              onClick={() => {
                const data = {
                  countryCode: item.countryCode,
                  countryName: item.countryName_KR,
                };
                dispatch(setCountry(data));
                changeMode();
              }}
            />
          );
        }
      }}
    </SearchResults>
  );
  // 도시 목록 컨텐츠
  let cityContent;
  if (isPending) {
    cityContent = (
      <motion.div
        initial={{ opacity: 0, x: "8%", y: 100 }}
        animate={{ opacity: 1, x: "8%", y: 50 }}
        transition={{ duration: 0.8 }}
        className="flex justify-start"
      >
        <CityItem
          key={countryState.countryName}
          title={countryState.countryName}
          subTitle="의 도시를 불러오는 중입니다..."
          isSelectMode={false}
        />
      </motion.div>
    );
  }
  if (isError) {
    //FIXME: 에러 컴포넌트 만들어서 대체하기
    cityContent = (
      <ErrorPage
        title="⚠️에러 발생"
        message={error.info?.message || "데이터 불러오기 실패"}
        onClick={handleBack}
      />
    );
  }
  if (data) {
    // console.log("data", data);
    // console.log("cities", cities);
    cityContent = (
      <>
        <motion.div
          className="flex flex-col items-start my-10"
          initial={{ opacity: 0, x: "30%", y: 50 }}
          animate={{ opacity: 1, x: "12%", y: 50 }}
          exit={{ opacity: 0, x: "30%", y: 50 }}
          transition={{ duration: 0.8 }}
        >
          <CityItem
            key={countryState.countryName}
            title={countryState.countryName}
            subTitle="의 도시 선택 중"
            isSelectMode={false}
          />
          <button onClick={changeMode}>재선택</button>
        </motion.div>
        <SearchResults items={data}>
          {(item) => {
            const isSelected = JSON.stringify(cities).includes(item.id);
            return (
              <CityItem
                btnKey={item.id}
                key={item.cityName}
                title={item.cityNameTranslated}
                subTitle={item.cityName}
                isSelectMode={true}
                onClick={() => {
                  isSelected ? handleRemove(item) : handleAdd(item);
                }}
                isSelected={isSelected}
              />
            );
          }}
        </SearchResults>
      </>
    );
  }
  return (
    <>
      <article>
        {/* 나라 선택 모드일 때 */}
        {mode === "country" && countryContent}
        {/* 도시 선택 모드일때 */}
        {mode === "city" && cityContent}
      </article>
      <br />
      {mode === "city" && (
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
                  {city.cityNameTranslated.toUpperCase()}
                </motion.span>
              ))}
            </AnimatePresence>
          </div>
          <div className="flex justify-center">
            <Button color="#7ac9dc" to="term">
              <span className="text-white">다음 단계</span>
            </Button>
          </div>
        </BottomSheet>
      )}
    </>
  );
};

export default SelectCityPage;
