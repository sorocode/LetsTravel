import logoIcon from "/logo.png";

const Logo = () => {
  return (
    <div className="flex flex-col justify-center items-center">
      <img src={logoIcon} alt="logo icon" width="30%" />
      <span className="font-ShadowsIntoLight text-xl">LetsTravel</span>
    </div>
  );
};

export default Logo;
