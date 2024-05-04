import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../pages/RootLayout";
import SelectCountryPage from "../pages/SelectCountryPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      {
        index: true,
        element: <SelectCountryPage />,
      },
    ],
  },
]);
