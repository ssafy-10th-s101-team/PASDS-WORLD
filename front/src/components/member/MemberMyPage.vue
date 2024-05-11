<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">마이페이지</h3>
      <label for="nickName" class="block mb-2 text-md text-gray-900 dark:text-gray-300"
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
      <!-- 초대 받은 조직 목록 -->
      <label for="invited" class="block mb-2 text-md text-gray-900 dark:text-gray-300"
        >초대 목록</label
      >
      <div class="max-w-2xl mx-auto">
        <div class="flex flex-col">
          <div class="overflow-x-auto shadow-md sm:rounded-lg max-w-full">
            <div class="inline-block min-w-full align-middle">
              <div class="overflow-hidden">
                <table class="min-w-full divide-y divide-gray-200 table-fixed dark:divide-gray-700">
                  <thead class="bg-gray-100 dark:bg-gray-700">
                    <tr>
                      <th
                        scope="col"
                        class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                      >
                        조직명
                      </th>
                      <th
                        scope="col"
                        class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                      >
                        조직장
                      </th>
                      <th
                        scope="col"
                        class="sr-only py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                      >
                        Actions
                      </th>
                      <th scope="col" class="p-4 w-1/6 sr-only">
                        <div class="flex items-center">
                          <input
                            id="checkbox-all"
                            type="checkbox"
                            class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                          />
                          <label for="checkbox-all" class="sr-only">checkbox</label>
                        </div>
                      </th>
                      <th scope="col" class="p-4 w-1/6">
                        <span class="sr-only">수락 여부</span>
                      </th>
                    </tr>
                  </thead>
                  <tbody
                    class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
                  >
                    <tr v-for="(organization, index) in organizations" :key="index">
                      <td
                        class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                      >
                        {{ organization.organizationName }}
                      </td>
                      <td
                        class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                      >
                        {{ organization.organizationLeader }}
                      </td>
                      <td
                        class="invisible py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                      ></td>
                      <td class="p-4 w-4 sr-only">
                        <div class="flex items-center">
                          <input
                            id="checkbox-table-1"
                            type="checkbox"
                            class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                          />
                          <label for="checkbox-table-1" class="sr-only"></label>
                        </div>
                      </td>
                      <!-- 수락 거절 -->
                      <td class="py-4 pr-4 flex justify-between">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke-width="1.5"
                          stroke="green"
                          class="w-6 h-6 hover:bg-gray-100 dark:hover:bg-gray-700"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            d="m4.5 12.75 6 6 9-13.5"
                          />
                        </svg>
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke-width="1.5"
                          stroke="red"
                          class="w-6 h-6 hover:bg-gray-100 dark:hover:bg-gray-700"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            d="M6 18 18 6M6 6l12 12"
                          />
                        </svg>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="text-red-500 text-sm move-item" @click="toggleHidden('changePassword')">
        비밀번호 변경
      </div>
      <div class="text-red-500 text-sm move-item">회원탈퇴</div>

      <div class="flex justify-center"></div>
    </div>
  </div>
  <MemberChangePasswordModal />
</template>

<script setup>
import { ref } from 'vue'
import { useCommonStore } from '@/stores/common'
import MemberChangePasswordModal from '../common/MemberChangePasswordModal.vue'
import BaseButton from '../common/BaseButton.vue'
import cookieHelper from '@/utils/cookie'

const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const nickname = ref(sessionStorage.getItem('nickname'))
// const nickname = ref(cookieHelper.get('nickname'))
const isNicknameValid = ref(true)

const organizations = ref([
  {
    organizationName: 'S101',
    organizationLeader: '신우섭'
  },
  {
    organizationName: 'S102',
    organizationLeader: '김현수'
  },
  {
    organizationName: 'S103',
    organizationLeader: '이지은'
  }
])

const validateNickname = () => {
  const regex = /^.{2,20}$/
  isNicknameValid.value = regex.test(nickname.value)
}
</script>

<style scoped>
.move-item {
  cursor: pointer;
}
</style>
