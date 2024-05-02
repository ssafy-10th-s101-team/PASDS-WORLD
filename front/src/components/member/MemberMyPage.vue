<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">마이페이지</h3>
      <label for="nickName" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
        >닉네임</label
      >
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <!-- 닉네임 입력 필드 -->
        <div>
          <input
            type="text"
            id="nickName"
            v-model="nickname"
            @input="validateNickname"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            :placeholder="nickname"
          />
          <div v-if="!isNicknameValid" class="text-red-500 text-sm">
            2자리 이상 20자리 이하이어야 합니다
          </div>
          <div v-else class="text-green-500 text-sm">닉네임이 유효합니다</div>
        </div>
        <div class="flex justify-center">
          <BaseButton buttonText="변경" />
        </div>
      </div>

      <div class="text-red-500 text-sm" @click="toggleHidden('changePassword')">비밀번호 변경</div>
      <div class="text-red-500 text-sm">회원탈퇴</div>

      <div class="flex justify-center">
        <button
          type="submit"
          class="text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          확인
        </button>
      </div>
    </div>
  </div>
  <MemberChangePasswordModal />
</template>

<script setup>
import { ref } from 'vue'
import { useCommonStore } from '@/stores/common'
import MemberChangePasswordModal from '../common/MemberChangePasswordModal.vue'
import BaseButton from '../common/BaseButton.vue'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const nickname = ref('hidd')

const isNicknameValid = ref(true)

const validateNickname = () => {
  const regex = /^.{2,20}$/
  isNicknameValid.value = regex.test(nickname.value)
}
</script>

<style scoped></style>
