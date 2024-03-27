import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { fileURLToPath, URL } from "url";
import {deleteSync} from "del";

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: { "@": fileURLToPath(new URL("./src", import.meta.url)) },
  },
  build: {
    outDir: '../main/resources/static',
    rollupOptions: {
      plugins: [
        {
          name: 'cleanup',
          buildStart() {
            deleteSync('../main/resources/static/*', { force: true });
          }
        }
      ]
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080/',
        changeOrigin: true
      }
    }
  }
});