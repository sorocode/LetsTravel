import { Link } from "react-router-dom";

const ErrorPage = ({ title, message, onClick }) => {
  return (
    <div className="flex flex-col items-center mt-10 gap-20">
      <div className="flex flex-col items-center gap-8">
        <h1 className="text-xl">{title}</h1>
        <h2 className="font-semibold">{message}</h2>
      </div>
      <Link onClick={onClick} to=".." className="text-blue-700">
        돌아가기
      </Link>
    </div>
  );
};

export default ErrorPage;
