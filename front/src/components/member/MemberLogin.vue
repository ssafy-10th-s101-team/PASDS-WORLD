<template>
  <!-- This is an example component -->
  <div class="max-w-2xl mx-auto p-16">
    <!-- <div class="max-w-2xl mx-auto"> -->
    <div
      class="bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <form class="space-y-6" action="#">
        <h3 class="text-xl text-gray-900 dark:text-white">로그인</h3>
        <BaseInputTextField inputText="e-mail" placeHolder="name@domain.com" />
        <BaseInputPasswordField />
        <button
          type="submit"
          class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          로그인
        </button>
        <div class="flex justify-between text-sm text-gray-500 dark:text-gray-300">
          <router-link
            to="/member/signup"
            class="text-sm text-samsung-blue hover:underline dark:text-blue-500"
          >
            회원가입</router-link
          >

          <a href="#" class="text-sm text-samsung-blue hover:underline dark:text-blue-500"
            >비밀번호 찾기</a
          >
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import BaseInputPasswordField from '../common/BaseInputPasswordField.vue'
import BaseInputTextField from '../common/BaseInputTextField.vue'
</script>

<style scoped></style>
