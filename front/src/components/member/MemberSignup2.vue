<template>
  <div class="flex justify-center items-center h-screen bg-white">
    <div class="text-center">
      <img src="@/assets/images/pasdsworld_v2_qr.png" alt="QR Code" class="mx-auto" />
      <h2 class="text-lg font-semibold text-gray-800 mt-4">
        QR 코드를 스캔하여 본인 인증 앱을 다운로드하세요
      </h2>
      <button
        @click="goToNextStep"
        class="mt-6 bg-samsung-blue hover:bg-blue-800 text-white font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none focus:ring-4 focus:ring-blue-300 transition-colors"
      >
        다음으로
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
