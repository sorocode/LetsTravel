import { Link } from "react-router-dom";
function Button({ color, children, ...props }) {
  let cssClass = `flex px-2 py-1 justify-center items-center bg-${color}`;

  return (
    <Link className={cssClass} {...props}>
      {children}
    </Link>
  );
}

export default Button;
