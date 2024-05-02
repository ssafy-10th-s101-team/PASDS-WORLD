<template>
  <BaseModal modalId="organizationInvitationModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">조직 초대</h3>
      <BaseInputTextField inputText="email" placeHolder="name@domain.com" />

      <button
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        초대
      </button>
      <!-- 추후 구현해야할 내용 
        1. v-if Member exists: 멤버 창을 띄워주기
    2. v-else: 초대 메일 보냈다는 alert
3. 모달 창 적당한 크기로 조정
-->
    </form>
  </BaseModal>
</template>

<script setup>
import BaseModal from './BaseModal.vue'
import BaseInputTextField from './BaseInputTextField.vue'
</script>

<style scoped></style>
