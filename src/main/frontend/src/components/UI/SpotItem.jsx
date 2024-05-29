import { motion } from "framer-motion";
import addIcon from "../../assets/icons/add_icon.svg";
import blueCheckIcon from "../../assets/icons/blue_check_icon.svg";
// import infoIcon from "../../assets/icons/info_icon.svg";
import dummyIcon from "../../assets/icons/dummy_image.svg";

function SpotItem({
  spotName,
  country,
  longitude,
  latitude,
  isSelected,
  ...props
}) {
  const formattedspotName = spotName.replace(/\s/g, "");

  return (
    <div className="container flex justify-between my-2">
      <div className="leftSide flex gap-6">
        <img src={dummyIcon} alt={formattedspotName} />
        <div className="spotInfo flex flex-col items-start justify-center">
          <p className="spotName font-bold">{spotName.toUpperCase()}</p>
          <p className="spotLocation text-[#848484]">{country}</p>
        </div>
      </div>
      {
        <div className="rightSide flex items-center justify-center gap-5">
          <motion.button
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
      }
    </div>
  );
}
export default SpotItem;
