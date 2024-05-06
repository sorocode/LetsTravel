import { motion } from "framer-motion";
import addIcon from "../../assets/icons/add_icon.svg";
import checkIcon from "../../assets/icons/check_icon.svg";
import blueCheckIcon from "../../assets/icons/blue_check_icon.svg";
import infoIcon from "../../assets/icons/info_icon.svg";
import tokyo from "../../assets/images/tokyo.svg";
import bangkok from "../../assets/images/bangkok.svg";
import osaka from "../../assets/images/osaka.svg";
import london from "../../assets/images/london.svg";
import beijing from "../../assets/images/beijing.svg";
import hanoi from "../../assets/images/hanoi.svg";
import newyork from "../../assets/images/newyork.svg";

function CityItem({ cityName, cityCountry, isSelected, ...props }) {
  const formattedCityName = cityName.replace(/\s/g, "");
  let cityImage;
  switch (formattedCityName) {
    case "tokyo":
      cityImage = tokyo;
      break;
    case "bangkok":
      cityImage = bangkok;
      break;
    case "osaka":
      cityImage = osaka;
      break;
    case "london":
      cityImage = london;
      break;
    case "hanoi":
      cityImage = hanoi;
      break;
    case "newyork":
      cityImage = newyork;
      break;
    case "beijing":
      cityImage = beijing;
      break;
    default:
      cityImage = null; // 기본값 설정
      break;
  }
  return (
    <div className="container flex justify-between my-2">
      <div className="leftSide flex gap-6">
        <img src={cityImage} alt={formattedCityName} />
        <div className="cityInfo flex flex-col items-start justify-center">
          <p className="cityName font-bold">{cityName.toUpperCase()}</p>
          <p className="cityLocation text-[#848484]">
            {cityCountry.toUpperCase()}
          </p>
        </div>
      </div>
      <div className="rightSide flex items-center justify-center gap-5">
        <motion.button
          variants={{
            default: { scale: 1 },
            clicked: { scale: 1.5 },
          }}
          animate={isSelected ? "clicked" : "default"}
          {...props}
        >
          <img
            src={isSelected ? blueCheckIcon : addIcon}
            alt={isSelected ? blueCheckIcon : addIcon}
          />
        </motion.button>

        <button>
          <img src={infoIcon} alt={infoIcon} />
        </button>
      </div>
    </div>
  );
}

export default CityItem;
