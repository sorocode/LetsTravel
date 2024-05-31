import { useParams } from "react-router-dom";
import SearchResults from "../components/UI/SearchResults";
import Button from "../components/UI/Buttons/Button";
import SpotItem from "../components/UI/SpotItem";
import { AnimatePresence, motion } from "framer-motion";
import BottomSheet from "../components/UI/Bottomsheet/BottomSheet";
import { useDispatch, useSelector } from "react-redux";
import {
  addSpot,
  removeSpot,
  acceptSchedule,
} from "../store/schedule/scheduleSlice";
import { useMutation } from "@tanstack/react-query";
import { addNewPlace, fetchSpots, generateCase } from "../util/http";
import ErrorPage from "../components/UI/Error/ErrorPage";
import { useEffect, useState } from "react";
import gptIcon from "../assets/icons/gptIcon.svg";
import { LinearProgress } from "@mui/material";
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
  //Place 추가하기
  const {
    data: placeData,
    mutate: placeMutate,
    isPending: isPlacePending,
    isError: isPlaceError,
    error: placeError,
  } = useMutation({
    mutationKey: ["places"],
    mutationFn: addNewPlace,
  });

  //TODO: 추천 목록 추가
  useEffect(() => {
    mutate();
  }, [mutate]);
  useEffect(() => {
    data?.places.map((place) => {
      placeMutate(place);
    });
  }, [data, placeMutate]);

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

  // 제안 승날하기
  const acceptProposal = (schedule) => {
    dispatch(acceptSchedule(schedule));
  };

  let content;
  if (isPending) {
    content = (
      <p className="flex justify-center items-center">여행지를 불러오는 중</p>
    );
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
    console.log("data", data);
    content = (
      <>
        <SearchResults
          items={data.places}
          searchId="spots"
          spotMode
          apiMode={apiMode}
          changeMode={changeMode}
          cityName={params.city}
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
      </>
    );
  }
  let bsContent;
  if (isGptPending) {
    bsContent = (
      <div>
        <span className="text-white">동선 생성중...</span>
        <LinearProgress color="success" />
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
    <>
      {content}
      <BottomSheet title={<h2>일정 고르기</h2>}>
        <div className="flex flex-col gap-2 justify-center items-center mt-2">
          <div className="flex gap-4">
            {spots.length > 3 ? (
              <div className="flex justify-center">
                <Button onClick={gptMutate} color="#7ac9dc">
                  {isGptPending ? (
                    bsContent
                  ) : (
                    <div className="flex flex-col items-center justify-center">
                      <img src={gptIcon} alt="gptIcon" width="20px" />
                      <span className="text-white">AI동선추천</span>
                    </div>
                  )}
                </Button>
              </div>
            ) : (
              <p>여행지를 4개 이상 추가해주세요</p>
            )}
            {gptData && (
              <div className="flex justify-center">
                <Button
                  color="#FCD4FF"
                  onClick={() => acceptProposal(bsContent)}
                  to="/select/map"
                >
                  <span className="text-white">지도보기</span>
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
                    <b>Day {day}</b>
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
    </>
  );
}

export default SelectSpotPage;
