import { useParams } from "react-router-dom";
import SearchResults from "../components/UI/SearchResults";
import { dummyCities } from "../dummyCities";
import Button from "../components/UI/Buttons/Button";
import SpotItem from "../components/UI/SpotItem";
import { AnimatePresence, motion } from "framer-motion";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import { useDispatch, useSelector } from "react-redux";
import { addSpot, removeSpot } from "../store/schedule/scheduleSlice";
import { useMutation, useQuery } from "@tanstack/react-query";
import { fetchSpots } from "../util/http";
import ErrorPage from "../components/UI/Error/ErrorPage";
import { useEffect, useState } from "react";
function SelectSpotPage() {
  const params = useParams();
  const [apiMode, setApiMode] = useState(false);
  const spots = useSelector((state) => state.schedule.spots);
  const dispatch = useDispatch();
  const { data, mutate, isPending, isError, error } = useMutation({
    mutationKey: ["recommend"],
    mutationFn: () => fetchSpots("관광지", params.city),
  });
  //TODO: 추천 목록 추가
  useEffect(() => {
    mutate();
  }, [mutate]);

  const handleAddSpot = (item) => {
    dispatch(addSpot(item));
  };
  const handleRemoveSpot = (item) => {
    dispatch(removeSpot(item.id));
  };
  const changeMode = () => {
    setApiMode(true);
  };
  let content;
  if (isPending) {
    content = <p>여행지를 불러오는 중</p>;
  }
  if (isError) {
    content = (
      <ErrorPage
        title="에러 발생!"
        message={
          error.info?.message ||
          "여행지를 가져오는 데 실패했습니다. 잠시 후 다시 시도해주십시오."
        }
      />
    );
  }
  if (data) {
    content = (
      <div>
        <SearchResults
          items={data.places}
          searchId="spots"
          apiMode={apiMode}
          changeMode={changeMode}
        >
          {(item) => {
            const isSelected = JSON.stringify(spots).includes(item.id);

            const countryComponent = item.addressComponents.find(
              (component) => {
                return component.types.includes("country");
              }
            );
            const country = countryComponent.longText;
            return (
              <SpotItem
                key={item.id}
                spotName={item.displayName.text}
                latitude={item.location.latitude}
                longitude={item.location.longitude}
                country={country}
                onClick={() => {
                  isSelected ? handleRemoveSpot(item) : handleAddSpot(item);
                }}
                isSelected={isSelected}
              />
            );
          }}
        </SearchResults>
      </div>
    );
  }
  return (
    <div>
      {content}
      <BottomSheet title={<h2>일정 고르기</h2>}>
        <div className="flex flex-col gap-2 justify-center items-center mt-2">
          <AnimatePresence>
            TODO: 아래 주석 참고해서 선택 아이템 표시
            {spots.map((spot, index) => (
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
                {spot.spotName.toUpperCase()}
              </motion.span>
            ))}
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
