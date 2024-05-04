import heart_blank from "../../assets/heart_blank.svg";
import map_filled from "../../assets/map_filled.svg";
import map_blank from "../../assets/map_blank.svg";
import navigator_filled from "../../assets/navigator_filled.svg";
import navigator_blank from "../../assets/navigator_blank.svg";
import profile_blank from "../../assets/profile_blank.svg";
import home_blank from "../../assets/home_blank.svg";
import { NavLink } from "react-router-dom";
const NavBar = () => {
  const navItemList = ["map", "navigator", "home", "heart", "profile"];
  return (
    <div className="container flex justify-evenly items-center bg-gray-400 w-screen max-w-screen-2xl h-[50px]">
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
