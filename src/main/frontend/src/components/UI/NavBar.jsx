import heart_blank from "../../assets/icons/heart_blank.svg";
import calendar_filled from "../../assets/icons/calendar_icon.svg";
import calendar_blank from "../../assets/icons/calendar_blank.svg";
import map_filled from "../../assets/icons/map_filled.svg";
import map__blank from "../../assets/icons/map_blank.svg";
import map_indicator_filled from "../../assets/icons/map_indicator_filled.svg";
import map_indicator_blank from "../../assets/icons/map_indicator_blank.svg";
import earth_filled from "../../assets/icons/earth_filled.svg";
import earth_blank from "../../assets/icons/earth_blank.svg";
import navigator_filled from "../../assets/icons/navigator_filled.svg";
import navigator_blank from "../../assets/icons/navigator_blank.svg";
import profile_blank from "../../assets/icons/profile_blank.svg";
import profile_filled from "../../assets/icons/profile_filled.svg";
import home_blank from "../../assets/icons/home_blank.svg";
import home_filled from "../../assets/icons/home_filled.svg";
import { NavLink, useLocation, useParams } from "react-router-dom";
import { useEffect } from "react";

const NavBar = () => {
  const location = useLocation();
  const params = useParams();
  console.log(params);
  useEffect(() => {
    console.log(location.pathname);
  }, [location]);
  return (
    <div className="container flex justify-evenly items-center w-screen max-w-screen-2xl h-[50px] bg-white z-[200]">
      <NavLink to="/">
        <img
          className={
            location.pathname == "/" ? "shadow-lg rounded-xl p-2" : undefined
          }
          src={location.pathname == "/" ? earth_filled : earth_blank}
          alt="earth_filled"
        />
      </NavLink>
      <NavLink to="/term">
        <img
          className={
            location.pathname == "/term"
              ? "shadow-lg rounded-xl p-2"
              : undefined
          }
          src={location.pathname == "/term" ? calendar_filled : calendar_blank}
          alt="navigator_blank"
        />
      </NavLink>
      <NavLink to="/">
        <img
          className={
            location.pathname == `/spot/${params.city}`
              ? "shadow-lg rounded-xl p-2"
              : undefined
          }
          src={
            location.pathname == `/spot/${params.city}`
              ? map_indicator_filled
              : map_indicator_blank
          }
          alt="home blank"
        />
      </NavLink>
      <NavLink to="/">
        <img
          className={
            location.pathname == "map" ? "shadow-lg rounded-xl p-2" : undefined
          }
          src={location.pathname == "map" ? map_filled : map__blank}
          alt="heart blank"
        />
      </NavLink>
      <NavLink to="/">
        <img src={profile_blank} alt="profile_blank" />
      </NavLink>
    </div>
  );
};

export default NavBar;
