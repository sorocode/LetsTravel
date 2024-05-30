import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import logoIcon from "/logo.png";
import { fetchAllPlans } from "../../util/http";
import { useState } from "react";

const Header = () => {
  const [isClicked, setIsClicked] = useState(false);
  const { data, isPending, isError, error } = useQuery({
    queryKey: ["trips"],
    queryFn: () => fetchAllPlans(-1),
    enabled: isClicked,
  });
  const onClickIconHandler = () => {
    setIsClicked(true);
  };
  if (data) {
    console.log(data);
  }
  return (
    <div className="flex items-center mx-8">
      <Link to="/">
        <div className="flex justify-start gap-2 mt-5 mb-2">
          <img
            src={logoIcon}
            alt="logoIcon"
            width="10%"
            height="8%"
            className="rounded-md"
          />
          <span className="text-xl font-semibold font-ShadowsIntoLight">
            LetsTravel
          </span>
        </div>
      </Link>

      <Link onClick={onClickIconHandler} to="/trips">
        <div className="w-8">
          <img src="/suitcase.svg" alt="suitcase" />
        </div>
      </Link>
    </div>
  );
};

export default Header;
