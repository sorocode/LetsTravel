import { Outlet } from "react-router-dom";
import NavBar from "../components/UI/NavBar";

const RootLayout = () => {
  return (
    <>
      <main>
        <Outlet />
      </main>
      <nav className="fixed bottom-0">
        <NavBar />
      </nav>
    </>
  );
};

export default RootLayout;
