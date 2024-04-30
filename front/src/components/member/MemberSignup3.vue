<template>
  <div class="flex justify-center items-center h-screen bg-white">
    <div class="text-center">
      <img src="@/assets/images/secret_key_qr_example.png" alt="QR Code" class="mx-auto" />
      <div>
        "MemberSignup.vue에서 response로 받아서 member.js Store에 넣어둔 totpKey QR코드 사진
        들이밀기"
      </div>
      <div>memberStore.tmp 는 지금 -> {{ memberStore.tmp }}</div>
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
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useMemberStore } from '../../stores/member'

const router = useRouter()
const memberStore = useMemberStore()

function goToNextStep() {
  router.push({ name: 'memberSignup4' })
}
</script>

<style scoped></style>
