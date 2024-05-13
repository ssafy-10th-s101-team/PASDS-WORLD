<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <p class="text-gray-900 dark:text-white">
        <img :src="totpKey" alt="QR Code" class="mx-auto" />
        PasdsWorld Authentication 앱을 실행하여 패스키를 스캔하세요
      </p>
      <button
        @click="goToNextStep"
        class="w-full bg-samsung-blue hover:bg-blue-800 text-white font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none focus:ring-4 focus:ring-blue-300 transition-colors"
      >
        다음으로
      </button>
    </div>
  </div>
</template>

<script setup>
import cookieHelper from '@/utils/cookie'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const totpKey = ref('')

onMounted(() => {
  // const savedBase64String = sessionStorage.getItem('totpKey')
  const savedBase64String = cookieHelper.get('totpKey')
  totpKey.value = `data:image/png;base64,${savedBase64String}`
})

function goToNextStep() {
  // sessionStorage.removeItem('totpKey')
  cookieHelper.delete('totpKey')
  router.push({ name: 'memberSignup4' })
}
</script>

<style scoped></style>
