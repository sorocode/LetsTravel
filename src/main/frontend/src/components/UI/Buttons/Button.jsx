import { Link } from "react-router-dom";
function Button({ color, children, ...props }) {
  let cssClass = `flex px-4 py-2 rounded-2xl justify-center items-center bg-[#${color}] w-32 mt-6 font-semibold`;

  return (
    <Link className={cssClass} {...props}>
      {children}
    </Link>
  );
}

export default Button;
