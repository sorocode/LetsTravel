import { RouterProvider } from "react-router-dom";
import { router } from "./util/router";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";

function App() {
  const queryClient = new QueryClient();
  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}

export default App;
