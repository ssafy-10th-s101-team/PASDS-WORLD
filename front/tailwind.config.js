/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        'samsung-blue': 'hsl(231, 78%, 35%)'
      }
    }
  },
  plugins: []
}
