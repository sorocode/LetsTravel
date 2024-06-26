import { Outlet, useLocation } from "react-router-dom";
import NavBar from "../components/UI/NavBar";
import Header from "../components/UI/Header";

const RootLayout = () => {
  const location = useLocation();
  let header;
  if (location.pathname === "/" || location.pathname === "/select/map") {
    header = null;
  } else {
    header = <Header />;
  }

  return (
    <>
      {header}
      <main>
        <Outlet />
      </main>
      {location.pathname !== "/" && (
        <nav className="fixed bottom-0 z-[1000] flex justify-center items-center min-w-full">
          <NavBar />
        </nav>
      )}
    </>
  );
};

export default RootLayout;
