import { Avatar } from "@mui/material";
import profileImage from "../../assets/images/1.jpg";
import goldTier from "../../assets/icons/goldtier.svg";
const ProfileSection = () => {
  return (
    <div className="flex gap-4 py-4 border-b-2 border-gray-200">
      <Avatar
        alt="Remy Sharp"
        src={profileImage}
        sx={{ width: 56, height: 56 }}
      />
      <div className="flex gap-16">
        <div className="flex flex-col justify-center gap-1">
          <span className="font-bold text-xl">강봉수</span>
          <p className="flex gap-1">
            <img src={goldTier} alt="goldTier" width="20px" />
            <span>GOLD 등급</span>
          </p>
        </div>
        <button>로그아웃</button>
      </div>
    </div>
  );
};

export default ProfileSection;
