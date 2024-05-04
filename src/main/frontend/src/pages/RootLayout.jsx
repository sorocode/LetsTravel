import { Outlet } from "react-router-dom";
import NavBar from "../components/UI/NavBar";

const RootLayout = () => {
  return (
    <>
      <main>
        <Outlet />
      </main>
      <footer>
        <NavBar />
      </footer>
    </>
  );
};

export default RootLayout;
