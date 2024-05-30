import { Link } from "react-router-dom";
import logoIcon from "/logo.png";
const Header = () => {
  return (
    <div className="flex items-center mx-8">
      <Link to="/">
        <div className="flex justify-start gap-2 mt-5 mb-2">
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

      <img src="/suitcase.svg" alt="suitcase" width="10%" />
    </div>
  );
};

export default Header;
