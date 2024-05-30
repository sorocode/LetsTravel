import { Link } from "react-router-dom";

import logoIcon from "/logo.png";

import SuitCase from "/suitcase.svg";

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

      <Link to="/trips">
        <div className="w-8">
          <img src={SuitCase} alt="suitcase" />
        </div>
      </Link>
    </div>
  );
};

export default Header;
