import { Skeleton } from "@mui/material";
import tokyoImage from "../../../assets/images/tokyo.svg";
import { getEndDate } from "../../../util/getEndDate";
import { useNavigate } from "react-router-dom";

export const Card = ({
  planName,
  countryCode,
  startDate,
  planNDays,
  planSeq,
}) => {
  const navigate = useNavigate();
  return (
    <div
      onClick={() => {
        console.log(planSeq);
        navigate(`${planSeq}`);
      }}
    >
      <div className="flex justify-evenly items-center py-3 shadow-md mx-6 rounded-lg my-2">
        <img src={tokyoImage} alt="tokyo image" />
        <div className="flex flex-col">
          <div className="flex items-center gap-2">
            <span className="title text-xl font-semibold">{planName}</span>
            <span className="countryName text-gray-500 text-sm">
              {countryCode}
            </span>
          </div>
          <div className="travelTerm">
            {startDate} ~ {getEndDate(startDate, planNDays)}
          </div>
        </div>
      </div>
    </div>
  );
};
export const CardSkeleton = () => {
  return (
    <div className="flex justify-evenly items-center px-9 py-3 shadow-md mx-6 rounded-lg my-2 gap-8">
      <img src="/imageSkeleton.svg" alt="imageSkeleton" width="20%" />
      <div className="flex flex-col w-3/4">
        <Skeleton width="80%" />
        <Skeleton animation="wave" />
      </div>
    </div>
  );
};
