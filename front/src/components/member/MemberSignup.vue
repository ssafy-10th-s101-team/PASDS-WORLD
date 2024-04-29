<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      @submit.prevent="submitForm"
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">회원가입</h3>
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <!-- 이메일 입력 필드 -->
        <div>
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >이메일</label
          >
          <input
            type="email"
            id="email"
            v-model="email"
            :disabled="emailVerified"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="name@domain.com"
            required
          />
        </div>
        <button
          type="button"
          @click="handleEmailVerification"
          class="text-white bg-samsung-blue hover:bg-samsung-blue focus:ring-4 focus:ring-samsung-blue font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:bg-samsung-blue dark:hover:bg-samsung-blue focus:outline-none dark:focus:ring-samsung-blue"
        >
          이메일 인증
        </button>

        <!-- 비밀번호 입력 필드 -->
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
          <div v-if="!password || !isPasswordValid" class="text-red-500 text-sm">
            대소문자, 특수기호, 숫자를 포함한 10자리 이상이어야 합니다.
          </div>
          <div v-else class="text-green-500 text-sm">비밀번호가 정상입니다</div>
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
            비밀번호가 일치하지 않습니다.
          </div>
          <div v-else-if="isPasswordSame" class="text-green-500 text-sm">
            비밀번호가 일치합니다.
          </div>
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
          <div v-if="!nickname || !isNicknameValid" class="text-red-500 text-sm">
            2자리 이상 20자리 이하이어야 합니다.
          </div>
          <div v-else class="text-green-500 text-sm">올바른 닉네임입니다</div>
        </div>
      </div>
      <div class="flex justify-center">
        <button
          type="submit"
          class="text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          회원가입
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useMemberStore } from '../../stores/member'
import { useRoute, useRouter } from 'vue-router'
import { localAxios } from '../../utils/http-commons.js'

const email = ref('')
const emailVerified = ref(false)

const password = ref('')
const isPasswordValid = ref(true)

const confirmPassword = ref('')
const isPasswordSame = ref(false)

const nickname = ref('')
const isNicknameValid = ref(false)

const memberStore = useMemberStore()
const router = useRouter()

const handleEmailVerification = () => {
  console.log('Email for verification:', email.value)
  emailVerified.value = true // 이메일 인증 성공 시 필드 비활성화
}

const validatePassword = () => {
  const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{10,}$/
  isPasswordValid.value = regex.test(password.value)
}

const checkPassword = () => {
  isPasswordSame.value = password.value === confirmPassword.value
}

const validateNickname = () => {
  const regex = /^.{2,20}$/
  isNicknameValid.value = regex.test(nickname.value)
}

const submitForm = async function () {
  try {
    const body = {
      email: email.value,
      password: password.value,
      confirmPassword: confirmPassword.value,
      nickname: nickname.value
    }

    const response = await localAxios.post(`/member/signup`, body)

    console.log(response)
    console.log(response.data)
    console.log(response.data.tmp)

    // Save tmp to the store and navigate
    memberStore.tmp = response.data.tmp

    console.log(memberStore.tmp)

    router.push({ name: 'memberSignup2' })
  } catch (error) {
    alert(error.response.data.message)
  }
}
</script>

<style scoped></style>
