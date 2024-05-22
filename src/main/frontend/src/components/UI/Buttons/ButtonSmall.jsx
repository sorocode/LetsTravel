import { Link } from "react-router-dom";
function ButtonSmall({ color, children, ...props }) {
  let cssClass = `flex px-4 py-2 rounded-2xl justify-center items-center w-16 mt-6 shadow-md hover:opacity-80 active:opacity-80`;
  return (
    <Link style={{ backgroundColor: color }} className={cssClass} {...props}>
      {children}
    </Link>
  );
}

export default ButtonSmall;
