import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { fileURLToPath, URL } from "url";
// import { promises as fsPromises } from 'fs';

// ---------------------------------- Truncate static folder
// Delete or truncate the output directory before building
// const cleanOutputDir = async () => {
//   const outputDir = fileURLToPath(new URL('../main/resources/static', import.meta.url));
//   await fsPromises.rm(outputDir, { recursive: true, force: true });
// };
//
// // Clean the output directory before building
// cleanOutputDir().then(r => {
//   console.log("Execute cleanOutPut");
// });
// ----------------------------------

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: { "@": fileURLToPath(new URL("./src", import.meta.url)) },
  },
  build: {
    outDir: '../main/resources/static'
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