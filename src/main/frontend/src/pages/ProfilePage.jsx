import { Button } from "@mui/material";
import ProfileSection from "../components/UI/ProfileSection";
import { Link } from "react-router-dom";

const ProfilePage = () => {
  return (
    //TODO: 로그인 상태에 따라 로그인하지 않았다면 로그인 페이지를, 로그인되어있다면 프로필 페이지를 렌더링
    <div className="mx-10 my-5">
      <ProfileSection />
      <div className="my-4">
        <Link to="/select/map">
          <Button>
            <span className="text-xl">나의 여행</span>
          </Button>
        </Link>
      </div>
    </div>
  );
};

export default ProfilePage;
