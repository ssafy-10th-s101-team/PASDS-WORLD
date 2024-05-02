<template>
  <!-- 로그인 컴포넌트 -->
  <div class="max-w-2xl mx-auto p-16">
    <div
      class="bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <form class="space-y-6" @submit.prevent="validateForm">
        <h3 class="text-xl text-gray-900 dark:text-white">로그인</h3>

        <!-- 이메일 입력 필드 -->
        <div>
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >이메일</label
          >
          <input
            type="email"
            id="email"
            v-model="email"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="name@domain.com"
            required
          />
        </div>

        <!-- 비밀번호 입력 필드 -->
        <div class="relative mb-6">
          <label for="password" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >비밀번호</label
          >
          <input
            :type="showPassword ? 'text' : 'password'"
            id="password"
            v-model="password"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="•••••••••"
            required
          />
          <button
            type="button"
            @click="togglePasswordVisibility"
            class="absolute inset-y-0 right-0 flex items-center px-2 text-gray-500 dark:text-gray-400"
          >
            <!-- 아이콘 토글 -->
            <svg
              v-if="!showPassword"
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M15 12a3 3 0 11-6 0 3 3 0 016 0zm-3 7a7 7 0 100-14 7 7 0 000 14z"
              />
            </svg>
            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5.121 5.121a1 1 0 000 1.415L19.799 21.213a1 1 0 101.415-1.414L6.536 4.707a1 1 0 00-1.415 0z"
              />
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M13 10a1 1 0 011 1v.01M21 12a9 9 0 01-18 0 9 9 0 0118 0z"
              />
            </svg>
          </button>
        </div>

        <!-- 로그인 버튼 -->
        <button
          type="submit"
          class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          로그인
        </button>

        <!-- 링크 영역 -->
        <div class="flex justify-between text-sm text-gray-500 dark:text-gray-300">
          <router-link
            :to="{ name: 'memberSignup' }"
            class="text-sm text-samsung-blue hover:underline dark:text-blue-500"
            >회원가입</router-link
          >
          <router-link
            :to="{ name: 'memberForgotPassword' }"
            class="text-sm text-samsung-blue hover:underline dark:text-blue-500"
            >비밀번호 찾기</router-link
          >
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { localAxios } from '@/utils/http-commons.js'

const router = useRouter()

const email = ref('')
const password = ref('')
const showPassword = ref(false)

function togglePasswordVisibility() {
  showPassword.value = !showPassword.value
}

async function validateForm() {
  try {
    const body = {
      email: email.value,
      password: password.value
    }
    const response = await localAxios.post(`/member/first-login`, body)
    console.log(response)

    sessionStorage.setItem('tmpNickname', response.data.nickname)

    // 다음 페이지로 라우팅
    router.push({ name: 'memberLogin2' })
  } catch (error) {
    console.log(error)
    alert(error.response.data.message)
  }
}
</script>

<style scoped>
/* 여기에 커스텀 스타일 추가 */
</style>
