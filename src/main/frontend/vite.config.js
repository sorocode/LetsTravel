import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

const serverUrl = import.meta.env.VITE_GCP_URL;
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: serverUrl,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
