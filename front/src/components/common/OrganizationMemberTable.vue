<template>
  <!-- This is an example component -->
  <div class="max-w-2xl mx-auto">
    <div class="flex flex-col">
      <div class="overflow-x-auto shadow-md sm:rounded-lg max-w-full">
        <div class="inline-block min-w-full align-middle">
          <div class="overflow-hidden">
            <table class="min-w-full divide-y divide-gray-200 table-fixed dark:divide-gray-700">
              <thead class="bg-gray-100 dark:bg-gray-700">
                <tr>
                  <!-- 체크박스 -->
                  <!-- <th scope="col" class="p-4 w-1/6">
                      <div class="flex items-center">
                        <input
                          id="checkbox-all"
                          type="checkbox"
                          class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                        />
                        <label for="checkbox-all" class="sr-only"></label>
                      </div>
                    </th> -->
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    닉네임
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    이메일
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-center text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    소속팀
                  </th>
                  <th scope="col" class="sr-only"></th>
                  <th scope="col" class="p-4 w-1/6">
                    <span
                      class="py-3 px-6 text-xs tracking-wider text-right text-gray-700 uppercase dark:text-gray-400 w-1/6"
                      >Edit</span
                    >
                  </th>
                </tr>
              </thead>
              <tbody
                class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
              >
                <!-- 체크박스 -->
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <!-- <td class="p-4 w-4">
                      <div class="flex items-center">
                        <input
                          id="checkbox-table-1"
                          type="checkbox"
                          class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                        />
                        <label for="checkbox-table-1" class="sr-only">checkbox</label>
                      </div>
                    </td> -->
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    쿼카카
                  </td>
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    name@domain.com
                  </td>
                  <div class="flex justify-center">
                    <td class="py-4 px-6 text-sm text-gray-500 whitespace-nowrap dark:text-white">
                      <span
                        class="inline-flex items-center justify-center px-2 ml-3 text-sm font-medium text-white bg-samsung-blue rounded-full dark:bg-gray-700 dark:text-gray-300"
                        >영업</span
                      >
                      <span
                        class="inline-flex items-center justify-center px-2 ml-3 text-sm font-medium text-white bg-samsung-blue rounded-full dark:bg-gray-700 dark:text-gray-300"
                        >전체</span
                      >
                    </td>
                  </div>
                  <td
                    class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                  ></td>
                  <td class="py-4 px-6 text-sm text-center whitespace-nowrap">
                    <a href="#" class="text-samsung-blue dark:text-blue-500 hover:underline"
                      >. . .
                    </a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-5 flex justify-center" @click="toggleHidden('organizationInvitationModal')">
      <BaseButton buttonText="초대하기 +" />
    </div>
  </div>

  <OrganizationInvitationModal />
</template>

<script setup>
import { ref } from 'vue'
import BaseButton from './BaseButton.vue'
import OrganizationInvitationModal from './OrganizationInvitationModal.vue'
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const members = ref([
  {
    nickname: '제비랑',
    email: '1996joon@naver.com',
    teams: [
      {
        teamName: '영업'
      },
      {
        teamName: '전체'
      }
    ]
  },
  {
    nickname: '덕끼끼',
    email: 'deokki@naver.com',
    teams: [
      {
        teamName: '마케팅'
      },
      {
        teamName: '전체'
      }
    ]
  },
  {
    nickname: '진뚱이용',
    email: 'wlsdyd4@naver.com',
    teams: [
      {
        teamName: '개발'
      },
      {
        teamName: '전체'
      }
    ]
  }
])
</script>

<style scoped></style>
