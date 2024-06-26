import calendar_filled from "../../assets/icons/calendar_icon.svg";
import calendar_blank from "../../assets/icons/calendar_blank.svg";
import map_filled from "../../assets/icons/map_filled.svg";
import map__blank from "../../assets/icons/map_blank.svg";
import map_indicator_filled from "../../assets/icons/map_indicator_filled.svg";
import map_indicator_blank from "../../assets/icons/map_indicator_blank.svg";
import earth_filled from "../../assets/icons/earth_filled.svg";
import earth_blank from "../../assets/icons/earth_blank.svg";
import profile_blank from "../../assets/icons/profile_blank.svg";
import profile_filled from "../../assets/icons/profile_filled.svg";
import { NavLink, useLocation, useParams } from "react-router-dom";
import { useSelector } from "react-redux";

const NavBar = () => {
  const location = useLocation();
  const params = useParams();
  const cities = useSelector((state) => state.schedule.cities);
  const firstCityName = cities[0] ? cities[0].cityName : null;

  // console.log(params);
  return (
    <div className="container flex justify-evenly items-center w-screen max-w-screen-2xl h-[50px] bg-white z-[200] py-10">
      <NavLink to="/select">
        <img
          className={
            location.pathname == "/select"
              ? "shadow-lg rounded-xl border-gray-300 border-[0.5px]"
              : undefined
          }
          src={location.pathname == "/select" ? earth_filled : earth_blank}
          alt="earth_filled"
        />
      </NavLink>
      <NavLink to="select/term">
        <img
          className={
            location.pathname == "/select/term"
              ? "shadow-lg rounded-xl border-gray-300 border-[0.5px]"
              : undefined
          }
          src={
            location.pathname == "/select/term"
              ? calendar_filled
              : calendar_blank
          }
          alt="navigator_blank"
        />
      </NavLink>
      {/* <NavLink to={firstCityName && `select/spot/${firstCityName}`}>
        <img
          className={
            location.pathname == `select/spot/${firstCityName}`
              ? "shadow-lg rounded-xl"
              : undefined
          }
          src={
            location.pathname == `select/spot/${params.city}`
              ? map_indicator_filled
              : map_indicator_blank
          }
          alt="home blank"
        />
      </NavLink> */}
      <NavLink to="/select/map">
        <img
          className={
            location.pathname == "/select/map"
              ? "shadow-lg rounded-xl border-gray-300 border-[0.5px]"
              : undefined
          }
          src={location.pathname == "/select/map" ? map_filled : map__blank}
          alt="heart blank"
        />
      </NavLink>
      <NavLink to="/profile">
        <img
          className={
            location.pathname == "/profile"
              ? "shadow-lg rounded-xl border-gray-300 border-[0.5px]"
              : undefined
          }
          src={location.pathname == "/profile" ? profile_filled : profile_blank}
          alt="profile_blank"
        />
      </NavLink>
    </div>
  );
};

export default NavBar;
