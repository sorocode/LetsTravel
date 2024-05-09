import { useParams } from "react-router-dom";
import SearchResults from "../components/UI/SearchResults";
import { dummyCities } from "../dummyCities";
import Button from "../components/UI/Buttons/Button";
import SpotItem from "../components/UI/SpotItem";
import { AnimatePresence, motion } from "framer-motion";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
function SelectSpotPage() {
  const params = useParams();
  const selectedCity = dummyCities.find(
    (city) => city.cityName === params.city
  );

  return (
    <div>
      <SearchResults items={selectedCity.spots} searchId="spots">
        {(item) => {
          const isSelected = JSON.stringify(selectedCity).includes(item.id);
          return (
            <SpotItem
              key={item.id}
              spotName={item.spotName}
              spotCountry={item.spotName}
              latitude={item.latitude}
              longitude={item.longitude}
              isSelectMode={true}
              // onClick={() => {
              //   isSelected ? handleRemove(item) : handleAdd(item);
              // }}
              isSelected={false}
            />
          );
        }}
      </SearchResults>
      <BottomSheet title={<h2>바텀시트</h2>}>
        <div className="flex flex-col gap-2 justify-center items-center mt-2">
          <AnimatePresence>
            TODO: 아래 주석 참고해서 선택 아이템 표시
            {/* {cities.map((city, index) => (
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
            ))} */}
          </AnimatePresence>
        </div>
        <div className="flex justify-center">
          <Button color="#FCD4FF" to=".">
            다음 단계
          </Button>
        </div>
      </BottomSheet>
    </div>
  );
}

export default SelectSpotPage;
