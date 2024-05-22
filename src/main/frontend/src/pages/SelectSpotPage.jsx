import { useParams } from "react-router-dom";
import SearchResults from "../components/UI/SearchResults";
import Button from "../components/UI/Buttons/Button";
import SpotItem from "../components/UI/SpotItem";
import { AnimatePresence, motion } from "framer-motion";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import { useDispatch, useSelector } from "react-redux";
import { addSpot, removeSpot } from "../store/schedule/scheduleSlice";
import { useMutation } from "@tanstack/react-query";
import { fetchSpots, generateCase } from "../util/http";
import ErrorPage from "../components/UI/Error/ErrorPage";
import { useEffect, useState } from "react";
import Spinner from "../assets/icons/spinner.gif";
function SelectSpotPage() {
  const params = useParams();
  const [apiMode, setApiMode] = useState(false);
  const cities = useSelector((state) => state.schedule.cities);
  const spots = useSelector((state) => state.schedule.spots);
  const dates = useSelector((state) => state.schedule.dateDif);
  const dispatch = useDispatch();

  //관광지 불러오기
  const { data, mutate, isPending, isError, error } = useMutation({
    mutationKey: ["recommend"],
    mutationFn: () => fetchSpots("관광지", params.city),
  });
  //TODO: 추천 목록 추가
  useEffect(() => {
    mutate();
  }, [mutate]);

  //관광지 동선 짜기
  const {
    data: gptData,
    mutate: gptMutate,
    isPending: isGptPending,
    isError: isGptError,
    error: gptError,
  } = useMutation({
    mutationKey: ["gpt"],
    mutationFn: () => generateCase(cities[0], spots, dates),
  });
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
  let bsContent;
  if (isGptPending) {
    bsContent = (
      <div>
        <img src={Spinner} alt="spinner" /> <span>동선 생성중...</span>
      </div>
    );
  }
  if (isGptError) {
    bsContent = (
      <ErrorPage
        title="에러 발생!"
        message={
          gptError.info?.message ||
          "여행지를 가져오는 데 실패했습니다. 잠시 후 다시 시도해주십시오."
        }
      />
    );
  }
  if (gptData) {
    bsContent = JSON.parse(gptData);
    console.log("keys", Object.keys(bsContent));
    console.log("bsContent", bsContent);
  }
  return (
    <div>
      {content}
      <BottomSheet title={<h2>일정 고르기</h2>}>
        <div className="flex flex-col gap-2 justify-center items-center mt-2">
          <div className="flex gap-4">
            {spots.length > 3 ? (
              <div className="flex justify-center">
                <Button onClick={gptMutate} color="">
                  {isGptPending ? bsContent : "🧞‍♂️동선추천"}
                </Button>
              </div>
            ) : (
              <p>여행지를 추가해주세요</p>
            )}
            {gptData && (
              <div className="flex justify-center">
                <Button color="#FCD4FF" to=".">
                  OK
                </Button>
              </div>
            )}
          </div>
          <AnimatePresence>
            {isGptPending || gptData
              ? null
              : spots.map((spot, index) => (
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

          {gptData && (
            <ul>
              {Object.keys(bsContent).map((day, index) => {
                return (
                  <li key={index}>
                    <b>{day}</b>
                    <div className="flex flex-col">
                      {bsContent[day].map((item) => {
                        return <p key={item.id}>{item.spotName}</p>;
                      })}
                    </div>
                  </li>
                );
              })}
            </ul>
          )}
        </div>
      </BottomSheet>
    </div>
  );
}

export default SelectSpotPage;
