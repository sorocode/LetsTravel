import { Avatar } from "@mui/material";
import profileImage from "../assets/images/1.jpg";
import goldTier from "../assets/icons/goldtier.svg";
const ProfilePage = () => {
  return (
    //TODO: 로그인 상태에 따라 로그인하지 않았다면 로그인 페이지를, 로그인되어있다면 프로필 페이지를 렌더링
    <div className="mx-10 my-5">
      <div className="flex gap-4">
        <Avatar
          alt="Remy Sharp"
          src={profileImage}
          sx={{ width: 56, height: 56 }}
        />
        <div className="flex flex-col justify-center gap-1">
          <span className="font-bold text-xl">강봉수</span>
          <p className="flex gap-1">
            <img src={goldTier} alt="goldTier" width="20px" />
            <span>GOLD 등급</span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
