<template>
  <!-- TOTP 2FA 입력 컴포넌트 -->
  <div class="max-w-2xl mx-auto p-16">
    <div
      class="bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <form class="space-y-6" @submit.prevent="validateTOTP">
        <h3 class="text-xl text-gray-900 dark:text-white">TOTP 2FA 코드 입력</h3>

        <!-- TOTP 코드 입력 필드 -->
        <div>
          <label for="totp" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >앱에서 발급받은 인증코드를 입력하세요</label
          >
          <input
            type="text"
            id="totp"
            v-model="totpCode"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="TOTP 코드 입력"
            required
          />
        </div>

        <!-- 확인 버튼 -->
        <button
          type="submit"
          class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          확인
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { localAxios } from '@/utils/http-commons.js'

const router = useRouter()

const totpCode = ref('')

async function validateTOTP() {
  try {
    const body = {
      totpCode: totpCode.value
    }
    await localAxios.post(`/member/second-login`, body)

    sessionStorage.setItem('nickname', sessionStorage.getItem('tmpNickname'))
    sessionStorage.removeItem('tmpNickname')

    router.push({ name: 'home' }).then(() => {
      nextTick(() => {
        window.location.reload()
      })
    })
  } catch (error) {
    console.log(error)
    alert(error.response.data.message)

    if (error.response.data.exceptionCode === 'TEMPORARY_TOKEN_EXPIRED') {
      sessionStorage.removeItem('tmpNickname')
      router.push({ name: 'memberLogin' })
    }
  }
}
</script>

<style scoped>
/* 추가적인 스타일링 */
</style>
