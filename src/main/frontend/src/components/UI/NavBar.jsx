import heart_blank from "../../assets/icons/heart_blank.svg";
import map_filled from "../../assets/icons/map_filled.svg";
import map_blank from "../../assets/icons/map_blank.svg";
import navigator_filled from "../../assets/icons/navigator_filled.svg";
import navigator_blank from "../../assets/icons/navigator_blank.svg";
import profile_blank from "../../assets/icons/profile_blank.svg";
import home_blank from "../../assets/icons/home_blank.svg";
import { NavLink } from "react-router-dom";
const NavBar = () => {
  // const navItemList = ["map", "navigator", "home", "heart", "profile"];
  return (
    <div className="container flex justify-evenly items-center w-screen max-w-screen-2xl h-[50px] bg-white">
      {/* {navItemList.map((item, index) => (
        <NavLink key={index}>
          <img src={`${item}_blank`} alt={`${item}_blank`} />
        </NavLink>
      ))} */}
      <NavLink>
        <img src={map_filled} alt="map_filled" />
      </NavLink>
      <NavLink>
        <img src={navigator_blank} alt="navigator_blank" />
      </NavLink>
      <NavLink>
        <img src={home_blank} alt="home blank" />
      </NavLink>
      <NavLink>
        <img src={heart_blank} alt="heart blank" />
      </NavLink>
      <NavLink>
        <img src={profile_blank} alt="profile_blank" />
      </NavLink>
    </div>
  );
};

export default NavBar;
