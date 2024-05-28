import Button from "../components/UI/Buttons/Button";
import IntroImage from "../assets/images/plainview.jpg";
import { Link } from "react-router-dom";
const IntroPage = () => {
  return (
    <div
      className="flex flex-col justify-center items-center h-screen"
      style={{
        background: `url(${IntroImage}) center`,
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
      }}
    >
      <Link to="select">
        <div className="flex flex-col justify-center items-center">
          <span className="text-2xl font-semibold font-ShadowsIntoLight">
            LetsTravel
          </span>
          <span>여행도 스마트하게</span>
        </div>
      </Link>
    </div>
  );
};

export default IntroPage;
