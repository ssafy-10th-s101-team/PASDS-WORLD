<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <p class="text-gray-900 dark:text-white">
        <img src="@/assets/images/pasdsworld_v3_qr.png" alt="QR Code" class="mx-auto" />
        QR 코드를 스캔하여 본인 인증 앱을 다운로드하세요.
      </p>
      <button
        @click="goToNextStep"
        class="w-full bg-samsung-blue hover:bg-blue-800 text-white font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none focus:ring-4 focus:ring-blue-300 transition-colors"
      >
        앱 연동하기
      </button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function goToNextStep() {
  router.push({ name: 'memberSignup3' })
}
</script>

<style scoped></style>
