import { Link } from "react-router-dom";
const Header = () => {
  return (
    <Link to="/select">
      <div className="flex justify-center items-center">
        <span className="text-2xl font-semibold font-ShadowsIntoLight">
          LetsTravel
        </span>
      </div>
    </Link>
  );
};

export default Header;
