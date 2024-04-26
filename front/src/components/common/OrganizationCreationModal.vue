<template>
  <BaseModal>
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">그룹 생성</h3>
      <BaseInputTextField inputText="그룹 이름" placeHolder="그룹 이름은 20자 이내로" />

      <button
        type="submit"
        class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        그룹 생성
      </button>
      <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
    Not registered? <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
  </div> -->
    </form>
  </BaseModal>
</template>

<script setup>
import BaseModal from './BaseModal.vue'
import BaseInputTextField from './BaseInputTextField.vue'
</script>

<style scoped></style>
