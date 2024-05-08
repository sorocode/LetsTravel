import { useDispatch, useSelector } from "react-redux";
import { dummyCities } from "../dummyCities";
import { addCity, removeCity } from "../store/schedule/scheduleSlice";
import CityItem from "../components/UI/CityItem";
import SearchResults from "../components/UI/SearchResults";
import { motion, AnimatePresence } from "framer-motion";
import Button from "../components/UI/Buttons/Button";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
const SelectCityPage = () => {
  const cities = useSelector((state) => state.schedule.cities);
  const dispatch = useDispatch();
  const handleAdd = (item) => {
    dispatch(addCity(item));
  };
  const handleRemove = (item) => {
    dispatch(removeCity(item.id));
  };
  return (
    <>
      <SearchResults items={dummyCities}>
        {(item) => {
          const isSelected = JSON.stringify(cities).includes(item.id);
          return (
            <CityItem
              key={item.id}
              cityName={item.cityName}
              cityCountry={item.countryName}
              isSelectMode={true}
              onClick={() => {
                isSelected ? handleRemove(item) : handleAdd(item);
              }}
              isSelected={isSelected}
            />
          );
        }}
      </SearchResults>
      <br />
      <BottomSheet
        title={
          <h2 className="text-center">
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
