<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">회원가입 완료</h3>
      <p class="text-gray-900 dark:text-white">회원가입이 성공적으로 완료되었습니다.</p>
      <button
        @click="goToLogin"
        class="w-full bg-samsung-blue hover:bg-blue-800 text-white font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none focus:ring-4 focus:ring-blue-300 transition-colors"
      >
        로그인 페이지로 이동
      </button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function goToLogin() {
  router.push({ name: 'memberLogin' })
}
</script>

<style scoped></style>
