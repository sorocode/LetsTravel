import { useEffect } from "react";
import { Link } from "react-router-dom";
function Button({ color, children, ...props }) {
  let cssClass = `flex px-4 py-2 rounded-2xl justify-center items-center w-32 mt-6 font-semibold shadow-md hover:opacity-80 active:opacity-80`;

  return (
    <Link
      style={{ backgroundColor: `#${color}` }}
      className={cssClass}
      {...props}
    >
      {children}
    </Link>
  );
}
Button.defaultProps = {
  color: "fcd4ff",
};
export default Button;
