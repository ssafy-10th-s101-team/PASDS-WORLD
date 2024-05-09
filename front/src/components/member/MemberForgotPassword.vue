<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      @submit.prevent="resetPassword"
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">비밀번호 재설정</h3>
      <div class="gap-6 mb-6 flex flex-row">
        <!-- 이메일 입력 필드 -->
        <div class="basis-2/3">
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >이메일</label
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
        <div class="flex items-end justify-start basis-1/8">
          <BaseSpinner :loading="loading" />
        </div>
        <div class="flex items-end justify-start basis-1/3">
          <BaseButton @click="sendOtpCode" buttonText="인증번호 받기" :loading="loading" />
        </div>
      </div>
      <div id="OTP" class="hidden gap-6 mb-6">
        <div class="flex flex-row gap-6 mb-2">
          <!-- otp 입력 필드 -->
          <div class="basis-2/3">
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
          <div class="flex items-end justify-start basis-1/8"></div>
          <div class="flex items-end justify-start basis-1/3">
            <BaseButton buttonText="인증완료" @click="checkOtpCode" />
          </div>
        </div>
        <div id="timer">
          <BaseTimer />
        </div>
      </div>

      <div id="password" class="hidden grid gap-6 mb-6 lg:grid-cols-1">
        <div>
          <label for="password" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >새 비밀번호</label
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
          <div v-if="password.length === 0 || !isPasswordValid" class="text-red-500 text-sm">
            대소문자, 특수기호, 숫자를 포함한 10자리 이상이어야 합니다
          </div>
          <div v-else class="text-green-500 text-sm">비밀번호가 유효합니다</div>
        </div>

        <div>
          <label for="password2" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >새 비밀번호 확인</label
          >
          <input
            type="password"
            id="confirmPassword"
            v-model="confirmPassword"
            @input="checkPassword"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="•••••••••"
            required
          />
          <div v-if="!isPasswordSame && confirmPassword" class="text-red-500 text-sm">
            비밀번호가 일치하지 않습니다
          </div>
          <div v-else-if="isPasswordSame" class="text-green-500 text-sm">비밀번호가 일치합니다</div>
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
    <BaseAlert :alertText="ErrorMsg" v-if="ErrorAlert" />
  </div>
</template>

<script setup>
import BaseAlert from '../common/BaseAlert.vue'
import BaseButton from '../common/BaseButton.vue'
import { ref } from 'vue'
import { useCommonStore } from '@/stores/common'
import { localAxios } from '@/utils/http-commons.js'
import BaseTimer from '@/components/common/BaseTimer.vue'
import { useRouter } from 'vue-router'
import BaseSpinner from '@/components/common/BaseSpinner.vue'

const router = useRouter()

const commonStore = useCommonStore()
const { toggleHidden, removeHidden, startTimer, stopTimer } = commonStore

// alert toggle
const EmailSuccessAlert = ref(false)
const OTPSuccessAlert = ref(false)
const ErrorAlert = ref(false)

// ErrorMsg
const ErrorMsg = ref('')

// loading spinner
const loading = ref(false)

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
const showErrorAlert = (message) => {
  ErrorMsg.value = message
  ErrorAlert.value = true
  setTimeout(() => {
    ErrorAlert.value = false
  }, 3000)
}

// password check
const password = ref('')
const confirmPassword = ref('')
const isPasswordValid = ref(true)
const isPasswordSame = ref(false)

const validatePassword = () => {
  const regex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?\/~`\-|\\=])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?\/~`\-|\\=]{10,}$/
  isPasswordValid.value = regex.test(password.value)
}

const checkPassword = () => {
  isPasswordSame.value = password.value === confirmPassword.value
}

// 이메일 인증 요청
const sendOtpCode = async () => {
  loading.value = true
  const body = {
    email: email.value,
    requestType: 2
  }
  await localAxios
    .post('/totp/email-verification-requests', body)
    .then(() => {
      loading.value = false
      showEmailSuccessAlert()
      startTimer()
    })
    .catch((error) => {
      console.error(error)
      loading.value = false
      showErrorAlert(error.response.data.message)
    })
}

// otp 코드 검증
const checkOtpCode = async () => {
  const body = {
    email: email.value,
    otpCode: otpCode.value
  }
  await localAxios
    .post('/totp/verification-email-code', body)
    .then(() => {
      emailVerified.value = true
      showOTPSuccessAlert()
      stopTimer()
      toggleHidden('timer')
    })
    .catch((error) => {
      console.error(error)
      showErrorAlert(error.response.data.message)
    })
}

const resetPassword = async () => {
  const body = {
    password: password.value,
    confirmPassword: confirmPassword.value
  }
  await localAxios
    .post('/member/reset-password', body)
    .then(() => {
      alert('변경되었습니다')
      router.push({ name: 'memberLogin' })
    })
    .catch((error) => {
      console.error(error)
      showErrorAlert(error.response.data.message)
    })
}
</script>

<style scoped></style>
