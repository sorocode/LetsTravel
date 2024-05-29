import { Link } from "react-router-dom";
import logoIcon from "../../../public/logo.png";
const Header = () => {
  return (
    <Link to="/select">
      <div className="flex justify-start mx-11 gap-2">
        <img src={logoIcon} alt="logoIcon" width="10%" height="8%" />
        <span className="text-xl font-semibold font-ShadowsIntoLight">
          LetsTravel
        </span>
      </div>
    </Link>
  );
};

export default Header;
