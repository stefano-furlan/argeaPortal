// Plugins
import vue from '@vitejs/plugin-vue'
import vuetify from 'vite-plugin-vuetify'

// Utilities
import { defineConfig } from 'vite'
import { fileURLToPath, URL } from 'node:url'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vite-plugin
    vuetify({
      autoImport: true,
    }),
  ],
  define: { 'process.env': {} },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
    extensions: [
      '.js',
      '.json',
      '.jsx',
      '.mjs',
      '.ts',
      '.tsx',
      '.vue',
    ],
  },
  server: {
    port: 3000,
        proxy: {
      // string shorthand
      /* '/foo': 'http://localhost:4567',*/
      // with options
      /*      '/api': {
              target: 'http://jsonplaceholder.typicode.com',
              changeOrigin: true,
              rewrite: (path) => path.replace(/^\/api/, '')
            },*/
      // with RegEx
      /*      '^/fallback/.*': {
              target: 'http://jsonplaceholder.typicode.com',
              changeOrigin: true,
              rewrite: (path) => path.replace(/^\/fallback/, '')
            },*/
      // Using the proxy instance
      '/rs': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        configure: (proxy, options) => {
          // proxy will be an instance of 'http-proxy'
        }
      },
      // Proxying websockets or socket.io
      /*      '/socket.io': {
              target: 'ws://localhost:5173',
              ws: true
            }*/
    }
  },
})
