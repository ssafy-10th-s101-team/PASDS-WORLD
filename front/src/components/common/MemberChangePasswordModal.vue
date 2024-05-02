<template>
  <BaseModal modalId="changePassword">
    <div
      class="max-w-2xl mx-auto bg-white p-16 space-y-6 bg-white shadow-md rounded-lg p-4 sm:px-6 lg:px-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <form class="">
        <h3 class="text-xl text-gray-900 dark:text-white pb-6">비밀번호 변경</h3>
        <div class="grid gap-6 mb-6 lg:grid-">
          <!-- 이메일 입력 필드 -->
          <div>
            <label for="password" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >현재 비밀번호</label
            >
            <input
              type="password"
              id="nowPassword"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder=""
              required
            />
          </div>

          <!-- 새 비밀번호 입력 필드 -->
          <div>
            <label for="newPassword" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >변경할 비밀번호</label
            >
            <input
              type="password"
              id="newPassword"
              v-model="newPassword"
              @input="validatePassword"
              :class="{ 'border-red-500': !isPasswordValid }"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="•••••••••"
              required
            />
            <div v-if="newPassword.length == 0 || !isPasswordValid" class="text-red-500 text-sm">
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
            <div v-else-if="isPasswordSame" class="text-green-500 text-sm">
              비밀번호가 일치합니다
            </div>
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
      </form>
    </div>
  </BaseModal>
</template>

<script setup>
import { ref } from 'vue'
import BaseModal from './BaseModal.vue'
const password = ref('')
const confirmPassword = ref('')
const newPassword = ref('')
const isPasswordValid = ref(true)
const isPasswordSame = ref(false)

const validatePassword = () => {
  const regex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?\/~`\-|\\=])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?\/~`\-|\\=]{10,}$/

  isPasswordValid.value = regex.test(newPassword.value)
}

const checkPassword = () => {
  isPasswordSame.value = newPassword.value === confirmPassword.value
}
</script>

<style scoped></style>
