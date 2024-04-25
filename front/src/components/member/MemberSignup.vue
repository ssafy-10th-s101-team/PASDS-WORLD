<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <form
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">회원가입</h3>
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <BaseInputTextField inputText="e-mail" placeHolder="name@domain.com" />
        <div class="flex items-end justify-start">
          <button>
            <BaseButton buttonText="이메일 인증" />
          </button>
        </div>

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

        <BaseInputTextField inputText="닉네임" placeHolder="홍길동" />
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
import BaseInputPasswordField from '../common/BaseInputPasswordField.vue'
import BaseInputTextField from '../common/BaseInputTextField.vue'
import BaseButton from '../common/BaseButton.vue'
import { ref } from 'vue'

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
</script>

<style scoped></style>
