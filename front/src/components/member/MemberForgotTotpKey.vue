<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">패스키 재발급</h3>
      <div v-show="showEmailDiv" class="gap-6 mb-6 flex flex-row">
        <!-- 이메일 입력 필드 -->
        <div class="basis-2/3">
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >이메일</label
          >
          <input
            type="email"
            id="email"
            v-model="tmpEmail"
            @keyup.enter="sendOtpCode"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            :placeholder="tmpEmail"
            readonly
            required
          />
        </div>
        <div class="flex items-end justify-start basis-1/8">
          <BaseSpinner :loading="loading" />
        </div>
        <div class="flex items-end justify-start basis-1/3">
          <BaseButton @click="sendOtpCode" buttonText="인증코드 받기" :loading="loading" />
        </div>
      </div>
      <div v-show="showOTPDiv" id="OTP" class="gap-6 mb-6">
        <div class="flex flex-row gap-6 mb-2">
          <!-- otp 입력 필드 -->
          <div class="basis-2/3">
            <label for="otpCode" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >이메일 인증코드</label
            >
            <input
              type="text"
              id="otpCode"
              v-model="otpCode"
              @keyup.enter="getTotpKey"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="이메일로 받은 인증코드를 입력하세요"
              required
            />
          </div>
          <div class="flex items-end justify-start basis-1/8"></div>
          <div class="flex items-end justify-start basis-1/3">
            <BaseButton buttonText="인증하기" @click="getTotpKey" />
          </div>
        </div>
        <div id="timer">
          <BaseTimer />
        </div>
      </div>
      <div id="TOTP" class="hidden">
        <img :src="totpKey" alt="QR Code" class="mx-auto" />
        <h2 class="text-lg font-semibold text-gray-900 dark:text-gray-300 mt-4">
          PasdsWorld Authentication 앱을 실행하여 <br />패스키를 스캔하세요
        </h2>
        <button
          @click="goToLogin"
          type="button"
          class="mt-6 bg-samsung-blue hover:bg-blue-800 text-white font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none focus:ring-4 focus:ring-blue-300 transition-colors"
        >
          2단계 인증 페이지로 이동
        </button>
      </div>
    </form>
    <BaseAlert alertText="메일이 송신되었습니다. 이메일을 확인해주세요." v-if="EmailSuccessAlert" />
    <BaseAlert alertText="인증되었습니다." v-if="OTPSuccessAlert" />
    <BaseFailAlert :alertText="ErrorMsg" v-if="ErrorAlert" />
  </div>
</template>

<script setup>
import BaseAlert from '../common/BaseAlert.vue'
import BaseFailAlert from '../common/BaseFailAlert.vue'
import BaseButton from '../common/BaseButton.vue'
import { onMounted, ref, toRefs } from 'vue'
import { useCommonStore } from '@/stores/common'
import { localAxios } from '@/utils/http-commons.js'
// import router from '@/router/index.js'
// import router from ''
import { useRouter } from 'vue-router'
import BaseTimer from '@/components/common/BaseTimer.vue'
import BaseSpinner from '@/components/common/BaseSpinner.vue'
import cookieHelper from '@/utils/cookie.js'
const router = useRouter()

const commonStore = useCommonStore()
const { toggleHidden, removeHidden, startTimer, stopTimer } = commonStore
const { inputTime } = toRefs(commonStore)

// alert toggle
const EmailSuccessAlert = ref(false)
const OTPSuccessAlert = ref(false)
const ErrorAlert = ref(false)

//ErrorMsg
const ErrorMsg = ref('')

// loading spinner
const loading = ref(false)

const email = ref('')
const tmpEmail = ref('')
const otpCode = ref('')

const totpKey = ref(null)

const showEmailDiv = ref(true)
const showOTPDiv = ref(true)

onMounted(() => {
  // tmpEmail.value = sessionStorage.getItem('tmpEmail')
  tmpEmail.value = cookieHelper.get('tmpEmail')
  sendOtpCode()
})

const showEmailSuccessAlert = () => {
  EmailSuccessAlert.value = true
  setTimeout(() => {
    EmailSuccessAlert.value = false
  }, 3000)
}
const showOTPSuccessAlert = () => {
  OTPSuccessAlert.value = true
  setTimeout(() => {
    OTPSuccessAlert.value = false
  }, 3000)
}

const showErrorAlert = (message) => {
  ErrorMsg.value = message
  ErrorAlert.value = true
  setTimeout(() => {
    ErrorAlert.value = false
  }, 3000)
}
// 이메일 인증 요청
const sendOtpCode = async () => {
  loading.value = true
  await localAxios
    .get('/email/totp-key-verification-requests')
    .then(() => {
      loading.value = false
      showEmailSuccessAlert()
      inputTime.value = 180 // 3분
      startTimer()
    })
    .catch((error) => {
      console.error(error)
      // 이메일 전송 실패 alert
      loading.value = false
      showErrorAlert(error.response.data.message)
    })
}

// totpKey 다시 받기
const getTotpKey = async () => {
  const body = {
    email: email.value,
    otpCode: otpCode.value
  }
  await localAxios
    .post('/totp/re-share-key', body, {
      responseType: 'arraybuffer' // 이미지를 arraybuffer 형태로 받음
    })
    .then((response) => {
      showOTPSuccessAlert()
      stopTimer()
      toggleHidden('timer')
      showEmailDiv.value = false
      showOTPDiv.value = false
      totpKey.value = URL.createObjectURL(new Blob([response.data], { type: 'image/png' }))
      removeHidden('TOTP')
    })
    .catch((error) => {
      console.error(error)
      // otp 인증 실패 alert
      showErrorAlert(error.response.data.message)
    })
}

const goToLogin = () => {
  router.push({ name: 'memberLogin2' })
}
</script>

<style scoped></style>
