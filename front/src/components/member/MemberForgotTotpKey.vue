<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">앱 재연동하기</h3>
      <div class="gap-6 mb-6 flex flex-row">
        <!-- 이메일 입력 필드 -->
        <div class="basis-2/3">
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >e-mail</label
          >
          <input
            type="email"
            id="email"
            v-model="email"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="name@domain.com"
            required
          />
        </div>
        <div class="flex items-end justify-start basis-1/3">
          <BaseButton @click="sendOtpCode" buttonText="이메일 인증" />
        </div>
      </div>
      <div id="OTP" class="hidden grid gap-6 mb-6 lg:grid-cols-2">
        <!-- otp 입력 필드 -->
        <div>
          <label for="otpCode" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >OTP 인증</label
          >
          <input
            type="text"
            id="otpCode"
            v-model="otpCode"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="이메일로 받은 코드를 입력하세요"
            required
          />
        </div>
        <div class="flex items-end justify-start">
          <BaseButton buttonText="확인" @click="checkOtpCode" />
        </div>
      </div>
      <div id="TOTP" class="hidden">
        <img :src="totpKey" alt="QR Code" class="mx-auto" />
        <h2 class="text-lg font-semibold text-gray-800 mt-4">
          앱에서 QR 코드를 스캔하여 앱을 연동하세요
        </h2>
        <button
          @click="goToNextStep"
          class="mt-6 bg-samsung-blue hover:bg-blue-800 text-white font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none focus:ring-4 focus:ring-blue-300 transition-colors"
        >
          로그인
        </button>
      </div>
    </form>
    <BaseAlert alertText="메일이 송신되었습니다. 이메일을 확인해주세요." v-if="EmailSuccessAlert" />
    <BaseAlert alertText="인증되었습니다." v-if="OTPSuccessAlert" />
    <BaseAlert alertText="메일 전송에 실패했습니다. 다시 시도해주세요." v-if="EmailFailAlert" />
    <BaseAlert alertText="인증에 실패했습니다. 다시 시도해주세요." v-if="OTPFailAlert" />
  </div>
</template>

<script setup>
import BaseAlert from '../common/BaseAlert.vue'
import BaseButton from '../common/BaseButton.vue'
import { ref } from 'vue'
import { useCommonStore } from '@/stores/common'
import { localAxios } from '@/utils/http-commons.js'
import router from '@/router/index.js'

const commonStore = useCommonStore()
const { removeHidden } = commonStore

// alert toggle
const EmailSuccessAlert = ref(false)
const OTPSuccessAlert = ref(false)
const EmailFailAlert = ref(false)
const OTPFailAlert = ref(false)

const email = ref('')
const otpCode = ref('')
const emailVerified = ref(false)

const totpKey = ref(null)

const showEmailSuccessAlert = () => {
  EmailSuccessAlert.value = true
  setTimeout(() => {
    EmailSuccessAlert.value = false
  }, 3000)
  removeHidden('OTP')
}
const showOTPSuccessAlert = () => {
  OTPSuccessAlert.value = true
  setTimeout(() => {
    OTPSuccessAlert.value = false
  }, 3000)
}
const showEmailFailAlert = () => {
  EmailFailAlert.value = true
  setTimeout(() => {
    EmailFailAlert.value = false
  }, 3000)
}
const showOTPFailAlert = () => {
  OTPFailAlert.value = true
  setTimeout(() => {
    OTPFailAlert.value = false
  }, 3000)
}

// 이메일 인증 요청
const sendOtpCode = async () => {
  const body = {
    email: email.value
  }
  // showEmailAlert();    테스트
  await localAxios.post('/totp/email-verification-requests', body)
    .then(() => {
      showEmailSuccessAlert()
    })
    .catch((error) => {
      console.error(error)
      // 이메일 전송 실패 alert
      showEmailFailAlert()
    })
}

// otp 코드 검증
const checkOtpCode = async () => {
  const body = {
    email: email.value,
    otpCode: otpCode.value
  }
  // showOTPAlert();    테스트
  await localAxios.post('/totp/verification-email-code', body)
    .then(() => {
      emailVerified.value = true
      showOTPSuccessAlert()
      localAxios.post('/totp/re-share-key', body)
        .then((response) => {
          totpKey.value = response.data
          console.log(response.data)
          removeHidden('TOTP')
        })
    })
    .catch((error) => {
      console.error(error)
      // otp 인증 실패 alert
      showOTPFailAlert()
    })
}

const goToNextStep = () => {
  router.push({ name: 'memberLogin' })
}
</script>

<style scoped></style>
