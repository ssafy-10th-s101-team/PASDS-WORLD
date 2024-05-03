<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">앱 재연동하기</h3>
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <!-- 이메일 입력 필드 -->
        <div>
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
        <div class="flex items-end justify-start">
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
      <div id="password" class="hidden grid gap-6 mb-6 lg:grid-cols-1">
        <div>
          <label for="password" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >비밀번호</label
          >
          <input
            type="password"
            id="password"
            v-model="password"
            @input="validatePassword"
            :class="{ 'border-red-500': !isPasswordValid }"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="•••••••••"
            required
          />
          <div v-if="!isPasswordValid" class="text-red-500 text-sm">
            비밀번호는 대문자, 소문자, 특수문자(@$!%*?&), 숫자를 포함하고 10자리 이상이어야 합니다.
          </div>
        </div>

        <div>
          <label for="password" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >비밀번호 확인</label
          >
          <input
            type="password"
            id="password2"
            v-model="password2"
            @input="checkPassword"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="•••••••••"
            required
          />
          <div v-if="!isPasswordSame && password2" class="text-red-500 text-sm">
            비밀번호가 일치하지 않습니다.
          </div>
          <div v-else-if="isPasswordSame" class="text-green-500 text-sm">
            비밀번호가 일치합니다.
          </div>
        </div>
        <div class="flex justify-center">
          <button
            type="submit"
            class="text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            비밀번호 변경
          </button>
        </div>
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
  removeHidden('password')
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

// password check
const password = ref('')
const password2 = ref('')
const isPasswordValid = ref(true)
const isPasswordSame = ref(false)
const validatePassword = (event) => {
  event.preventDefault()
  const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{10,}$/
  isPasswordValid.value = regex.test(password.value)
}
const checkPassword = (event) => {
  event.preventDefault()
  isPasswordSame.value = password.value === password2.value
}

// 이메일 인증 요청
const sendOtpCode = async () => {
  const body = {
    email: email.value,
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
    otpCode: otpCode.value,
  }
  // showOTPAlert();    테스트
  await localAxios.post('/totp/verification-email-code', body)
    .then(() => {
      emailVerified.value = true
      showOTPSuccessAlert()
    })
    .catch((error) => {
      console.error(error)
      // otp 인증 실패 alert
      showOTPFailAlert()
    })
}
</script>

<style scoped></style>