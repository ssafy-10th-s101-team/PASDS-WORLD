import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import fs from 'fs'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    // https: {
    //   key: fs.readFileSync('./privatekey.pem'), // 개인 키 파일 경로
    //   cert: fs.readFileSync('./certificate.pem') // 인증서 파일 경로
    // }
  }
})
