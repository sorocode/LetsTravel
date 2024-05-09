import { useState } from "react";

// 커스텀 훅 정의
const useToggleIcon = (initialState = false) => {
  const [iconFilled, setIconFilled] = useState(initialState);

  const handleClick = () => {
    setIconFilled((prevState) => !prevState);
  };

  return [iconFilled, handleClick];
};

export default useToggleIcon;
