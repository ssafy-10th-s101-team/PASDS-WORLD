<template>
  <!-- TOTP 2FA 입력 컴포넌트 -->
  <div class="max-w-2xl mx-auto p-16">
    <div
      class="bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white mb-6">2단계 인증</h3>
      <div class="gap-6 mb-2 flex flex-row">
        <!-- TOTP 코드 입력 필드 -->
        <div class="basis-2/3">
          <label for="totp" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >패스키 인증코드</label
          >
          <input
            type="text"
            id="totp"
            v-model="totpCode"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="앱으로 받은 인증코드를 입력하세요"
            required
          />
        </div>
        <!-- 확인 버튼 -->
        <div class="flex items-end justify-start basis-1/3">
          <BaseButton @click="sendTotpCode" buttonText="인증하기" />
        </div>
      </div>

      <!-- 링크 영역 -->
      <div class="flex justify-between text-sm text-gray-500 dark:text-gray-300">
        <router-link
          :to="{ name: 'memberForgotTotpKey' }"
          class="text-sm text-samsung-blue hover:underline dark:text-blue-500"
          >패스키 재발급
        </router-link>
      </div>
    </div>
    <BaseAlert alertText="인증되었습니다." v-if="TOTPSuccessAlert" />
    <BaseAlert :alertText="SignUpErrorMsg" v-if="SignUpErrorAlert" />
  </div>
</template>

<script setup>
import { nextTick, ref } from 'vue'
import { useRouter } from 'vue-router'
import { localAxios } from '@/utils/http-commons.js'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseAlert from '@/components/common/BaseAlert.vue'
import cookieHelper from '@/utils/cookie.js'

const router = useRouter()

const totpCode = ref('')

// alert toggle
const TOTPSuccessAlert = ref(false)
const SignUpErrorAlert = ref(false)

//signUpErrorMsg
const SignUpErrorMsg = ref('')

const showTOTPSuccessAlert = () => {
  TOTPSuccessAlert.value = true
  setTimeout(() => {
    TOTPSuccessAlert.value = false
  }, 3000)
}

const showSignUpErrorAlert = (message) => {
  SignUpErrorMsg.value = message
  SignUpErrorAlert.value = true
  setTimeout(() => {
    SignUpErrorAlert.value = false
  }, 3000)
}

const sendTotpCode = async () => {
  const body = {
    totpCode: totpCode.value
  }
  await localAxios
    .post(`/member/second-login`, body)
    .then(() => {
      sessionStorage.setItem('nickname', sessionStorage.getItem('tmpNickname'))
      sessionStorage.removeItem('tmpNickname')
      sessionStorage.removeItem('tmpEmail')
      // cookieHelper.generate('nickname', cookieHelper.get('tmpNickname'))
      // cookieHelper.delete('tmpNickname')
      // cookieHelper.delete('tmpEmail')

      showTOTPSuccessAlert()

      router.push({ name: 'home' }).then(() => {
        nextTick(() => {
          window.location.reload()
        })
      })
    })
    .catch((error) => {
      console.log(error)
      showSignUpErrorAlert(error.response.data.message)
    })
}
</script>

<style scoped>
/* 추가적인 스타일링 */
</style>
