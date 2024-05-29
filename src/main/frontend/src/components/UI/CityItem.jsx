import { motion } from "framer-motion";
import addIcon from "../../assets/icons/add_icon.svg";
import blueCheckIcon from "../../assets/icons/blue_check_icon.svg";
// import infoIcon from "../../assets/icons/info_icon.svg";
import tokyo from "../../assets/images/tokyo.svg";
import bangkok from "../../assets/images/bangkok.svg";
import osaka from "../../assets/images/osaka.svg";
import london from "../../assets/images/london.svg";
import beijing from "../../assets/images/beijing.svg";
import hanoi from "../../assets/images/hanoi.svg";
import newyork from "../../assets/images/newyork.svg";
import dummyImage from "../../assets/icons/dummy_image.svg";
function CityItem({
  btnKey,
  title,
  subTitle,
  isSelectMode,
  isSelected,
  ...props
}) {
  const formattedCityName = subTitle.replace(/\s/g, "");
  let cssClass = "cityInfo flex justify-center items-center";
  if (isSelectMode) {
    cssClass -= "items-center";
    cssClass += " flex-col items-start";
  }
  let thumbnail;
  switch (formattedCityName) {
    case "tokyo":
    case "Japan":
      thumbnail = tokyo;
      break;
    case "Thailand":
    case "bangkok":
      thumbnail = bangkok;
      break;
    case "osaka":
      thumbnail = osaka;
      break;
    case "london":
      thumbnail = london;
      break;
    case "Vietnam":
    case "hanoi":
      thumbnail = hanoi;
      break;
    case "newyork":
      thumbnail = newyork;
      break;
    case "beijing":
      thumbnail = beijing;
      break;
    default:
      thumbnail = dummyImage; // 기본값 설정
      break;
  }

  return (
    <div className="container flex justify-between my-2 py-2">
      <div className="leftSide flex gap-6">
        <img src={thumbnail} alt={formattedCityName} />
        <div className={cssClass}>
          <p className="title font-bold">{title.toUpperCase()}</p>
          <p className="subtitle text-[#848484]">{subTitle.toUpperCase()}</p>
        </div>
      </div>
      {isSelectMode && (
        <div className="rightSide flex items-center justify-center gap-5">
          <motion.button
            key={btnKey}
            variants={{
              default: { scale: 1 },
              clicked: { scale: 1.5 },
            }}
            animate={isSelected ? "clicked" : "default"}
            transition={{ duration: 0.2 }}
            {...props}
          >
            <img
              src={isSelected ? blueCheckIcon : addIcon}
              alt={isSelected ? blueCheckIcon : addIcon}
            />
          </motion.button>

          {/* <button>
            <img src={infoIcon} alt={infoIcon} />
          </button> */}
        </div>
      )}
    </div>
  );
}
export default CityItem;
