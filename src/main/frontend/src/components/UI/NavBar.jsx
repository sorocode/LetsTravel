import heart_blank from "../../assets/heart_blank.svg";
import map_blank from "../../assets/map_blank.svg";
import navigator_filled from "../../assets/navigator_filled.svg";
import profile_blank from "../../assets/profile_blank.svg";
import home_blank from "../../assets/home_blank.svg";
import { NavLink } from "react-router-dom";
const NavBar = () => {
  return (
    <div className="container fixed bottom-0 left-0 flex justify-evenly items-center bg-gray-400 w-screen h-[50px]">
      <NavLink>
        <img src={map_blank} alt="map_blank" />
      </NavLink>
      <NavLink>
        <img src={navigator_filled} alt="navigator_filled" />
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
