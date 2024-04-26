<template>
  <BaseModal modalId="organizationCreationModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">조직 추가</h3>
      <BaseInputTextField inputText="조직 이름" placeHolder="20자 이내" />

      <button
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        생성
      </button>
      <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
        Not registered?
        <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
      </div> -->
    </form>
  </BaseModal>
</template>

<script setup>
import BaseModal from './BaseModal.vue'
import BaseInputTextField from './BaseInputTextField.vue'
</script>

<style scoped></style>
