<template>
  <div class="flex justify-center items-center h-screen bg-white">
    <div class="text-center">
      <img :src="totpKey" alt="QR Code" class="mx-auto" />
      <h2 class="text-lg font-semibold text-gray-800 mt-4">
        앱에서 QR 코드를 스캔하여 앱을 연동하세요
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
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const totpKey = ref('')

onMounted(() => {
  const savedBase64String = sessionStorage.getItem('totpKey')
  totpKey.value = `data:image/png;base64,${savedBase64String}`
})

function goToNextStep() {
  sessionStorage.removeItem('totpKey')
  router.push({ name: 'memberSignup4' })
}
</script>

<style scoped></style>
