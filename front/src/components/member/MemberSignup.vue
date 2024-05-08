<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      @submit.prevent="submitForm"
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">회원가입</h3>
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
        <div class="flex items-end justify-start basis-1/3">
          <BaseButton @click="sendOtpCode" buttonText="인증번호 받기" />
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
          <div class="flex items-end justify-start basis-1/3">
            <BaseButton buttonText="인증완료" @click="checkOtpCode" />
          </div>
        </div>
        <div id="timer">
          <BaseTimer />
        </div>
      </div>

      <!-- 비밀번호 입력 필드 -->
      <div id="PW" class="hidden grid gap-6 mb-6 lg:grid-cols-2">
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
          <div v-if="password.length === 0 || !isPasswordValid" class="text-red-500 text-sm">
            대소문자, 특수기호, 숫자를 포함한 10자리 이상이어야 합니다
          </div>
          <div v-else class="text-green-500 text-sm">비밀번호가 유효합니다</div>
        </div>

        <!-- 비밀번호 확인 입력 필드 -->
        <div>
          <label for="confirmPassword" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >비밀번호 확인</label
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

        <!-- 닉네임 입력 필드 -->
        <div>
          <label for="nickname" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >닉네임</label
          >
          <input
            type="text"
            id="nickname"
            v-model="nickname"
            @input="validateNickname"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="홍길동"
            required
          />
          <div v-if="!isNicknameValid" class="text-red-500 text-sm">
            2자리 이상 20자리 이하이어야 합니다
          </div>
          <div v-else class="text-green-500 text-sm">닉네임이 유효합니다</div>
        </div>

        <div class="flex items-center justify-start">
          <button
            type="submit"
            class="text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-samsungone700c rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            회원가입
          </button>
        </div>
      </div>
    </form>
    <BaseAlert alertText="메일이 송신되었습니다. 이메일을 확인해주세요." v-if="EmailSuccessAlert" />
    <BaseAlert alertText="인증되었습니다." v-if="OTPSuccessAlert" />
    <BaseAlert :alertText="SignUpErrorMsg" v-if="SignUpErrorAlert" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { localAxios } from '@/utils/http-commons.js'
import BaseButton from '@/components/common/BaseButton.vue'
import { useCommonStore } from '@/stores/common.js'
import BaseAlert from '@/components/common/BaseAlert.vue'
import BaseTimer from '@/components/common/BaseTimer.vue'

const router = useRouter()

const email = ref('')
const otpCode = ref('')
const password = ref('')
const confirmPassword = ref('')
const nickname = ref('')

const isPasswordValid = ref(true)
const isPasswordSame = ref(false)
const isNicknameValid = ref(false)
const commonStore = useCommonStore()
const { toggleHidden, removeHidden, startTimer, stopTimer } = commonStore

// alert toggle
const EmailSuccessAlert = ref(false)
const OTPSuccessAlert = ref(false)
const SignUpErrorAlert = ref(false)

//signUpErrorMsg
const SignUpErrorMsg = ref('')

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
  removeHidden('PW')
}

const showSignUpErrorAlert = (message) => {
  SignUpErrorMsg.value = message
  SignUpErrorAlert.value = true
  setTimeout(() => {
    SignUpErrorAlert.value = false
  }, 3000)
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
      showOTPSuccessAlert()
      stopTimer()
      toggleHidden('timer')
    })
    .catch((error) => {
      console.error(error)
      // otp 인증 실패 alert
      showSignUpErrorAlert(error.response.data.message)
    })
}

// 이메일 인증 요청
const sendOtpCode = async () => {
  const body = {
    email: email.value,
    requestType: 1
  }
  await localAxios
    .post('/totp/email-verification-requests', body)
    .then(() => {
      showEmailSuccessAlert()
      startTimer()
    })
    .catch((error) => {
      console.error(error)
      // 이메일 전송 실패 alert
      showSignUpErrorAlert(error.response.data.message)
    })
}

const validatePassword = () => {
  const regex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?\/~`\-|\\=])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?\/~`\-|\\=]{10,}$/

  isPasswordValid.value = regex.test(password.value)
}

const checkPassword = () => {
  isPasswordSame.value = password.value === confirmPassword.value
}

const validateNickname = () => {
  const regex = /^.{2,20}$/
  isNicknameValid.value = regex.test(nickname.value)
}

const submitForm = async () => {

  const body = {
    email: email.value,
    password: password.value,
    confirmPassword: confirmPassword.value,
    nickname: nickname.value
  }
  await localAxios
    .post(`/member/signup`, body, {
      responseType: 'arraybuffer' // 이미지를 arraybuffer 형태로 받음
    })
    .then((response) => {
      const base64String = btoa(
        new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
      )

      sessionStorage.setItem('totpKey', base64String)
      router.push({ name: 'memberSignup2' })
    })
    .catch((error) => {
      console.log('error발생.\n')
      if (error.response && error.response.data) {
        // 바이너리 데이터를 문자열로 변환
        const errorData = new Uint8Array(error.response.data)
        const decodedString = new TextDecoder().decode(errorData)
        try {
          const errorInfo = JSON.parse(decodedString) // 문자열을 JSON 객체로 파싱
          showSignUpErrorAlert(errorInfo.message)
        } catch (e) {
          console.error('파싱 에러 : ', e)
        }
      } else {
        console.error('error : ', error.message) // 기타 다른 Axios 에러
        showSignUpErrorAlert(error.message)
      }
    })
}
</script>

<style scoped></style>
