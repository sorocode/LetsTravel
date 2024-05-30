import { Link } from "react-router-dom";
import logoIcon from "/logo.png";
const Header = () => {
  return (
    <Link to="/">
      <div className="flex justify-start mx-8 gap-2 mt-5 mb-2">
        <img
          src={logoIcon}
          alt="logoIcon"
          width="10%"
          height="8%"
          className="rounded-md"
        />
        <span className="text-xl font-semibold font-ShadowsIntoLight">
          LetsTravel
        </span>
      </div>
    </Link>
  );
};

export default Header;
