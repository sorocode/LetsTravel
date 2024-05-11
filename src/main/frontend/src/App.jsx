import { RouterProvider } from "react-router-dom";
import { router } from "./util/router";
import { QueryClientProvider } from "@tanstack/react-query";
import { queryClient } from "./util/http";
function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}

export default App;
