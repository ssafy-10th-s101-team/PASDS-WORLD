<template>
  <BaseModal modalId="TeamManagementModal">
    <!-- 버튼 -->
    <div class="max-w-2xl mx-auto flex justify-start justify-between px-6">
      <div class="max-w-lg">
        <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
          <button
            @click="moveToTeamRole"
            type="button"
            class="rounded-l-lg border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
          >
            역할
          </button>

          <button
            @click="moveToTeamInfo"
            type="button"
            class="rounded-r-md border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
          >
            팀 정보
          </button>
        </div>
      </div>
      <div v-if="currentTab === 'info'" class="mt-5" @click="openInviteModal">
        <BaseButton buttonText="초대" />
      </div>
    </div>
    <!-- 버튼 끝 -->
    <form class="flex flex-wrap space-y-6 px-6 lg:px-8 py-4 sm:pb-6 xl:pb-8" action="#">
      <!-- 팀 역할 -->
      <div v-if="currentTab === 'role'">
        <div class="flex flex-wrap items-center min-w-32 mb-4">
          <div class="mr-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 16 16"
              class="text-samsung-blue w-3 h-3"
              fill="currentColor"
            >
              <path
                fill-rule="evenodd"
                d="M11.89 4.111a5.5 5.5 0 1 0 0 7.778.75.75 0 1 1 1.06 1.061A7 7 0 1 1 15 8a2.5 2.5 0 0 1-4.083 1.935A3.5 3.5 0 1 1 11.5 8a1 1 0 0 0 2 0 5.48 5.48 0 0 0-1.61-3.889ZM10 8a2 2 0 1 0-4 0 2 2 0 0 0 4 0Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>
          <div>팀원</div>
        </div>
        <div class="flex flew-wrap items-center min-w-32 mb-4">
          <div class="mr-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 16 16"
              class="text-samsung-blue w-3 h-3"
              fill="currentColor"
            >
              <path
                fill-rule="evenodd"
                d="M11.89 4.111a5.5 5.5 0 1 0 0 7.778.75.75 0 1 1 1.06 1.061A7 7 0 1 1 15 8a2.5 2.5 0 0 1-4.083 1.935A3.5 3.5 0 1 1 11.5 8a1 1 0 0 0 2 0 5.48 5.48 0 0 0-1.61-3.889ZM10 8a2 2 0 1 0-4 0 2 2 0 0 0 4 0Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>
          <div>팀장</div>
        </div>
      </div>
      <!-- 역할 끝 -->
      <!-- 팀 정보 -->
      <div v-if="currentTab === 'info'" class="grid gap-6 items-baseline mb-6 lg:grid-cols-2">
        <div>팀이름</div>
        <div class="flex flex-wrap min-w-32 mb-4">
          <input
            type="text"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            v-model="teamName"
          />
        </div>
        <div class="">팀장(관리자)</div>
        <div class="max-w-lg mx-auto">
          <button
            @click="toggleHidden('dropdownMember')"
            class="border border-gray-300 text-gray-700 bg-gray-50 hover:bg-samsung-blue hover:text-white rounded-lg text-sm px-4 py-2.5 text-center inline-flex items-center block w-full"
            type="button"
            data-dropdown-toggle="dropdown"
          >
            팀장 이름
            <svg
              class="w-4 h-4 ml-2"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M19 9l-7 7-7-7"
              ></path>
            </svg>
          </button>

          <!-- Dropdown menu -->
          <div
            class="absolute hidden bg-white text-base z-50 list-none divide-y divide-gray-100 rounded shadow my-4"
            id="dropdownMember"
          >
            <!-- <div class="px-4 py-3">
                <span class="block text-sm">Bonnie Green</span>
                <span class="block text-sm font-medium text-gray-900 truncate"
                  >name@flowbite.com</span
                >
              </div> -->
            <ul class="py-1" aria-labelledby="dropdown">
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >팀장이 가능한 사람들</a
                >
              </li>
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >리스트를 보여줍니다</a
                >
              </li>
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >추후 스크롤바로 구현합니다</a
                >
              </li>
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >팀장 이름이 바뀝니다</a
                >
              </li>
            </ul>
          </div>
        </div>
        <div>팀원</div>
        <span></span>

        <div>팀원리스트</div>
        <div class="max-w-lg mx-auto">
          <button
            @click="toggleHidden('dropdownRole')"
            class="border border-gray-300 text-gray-700 bg-gray-50 hover:bg-samsung-blue hover:text-white rounded-lg text-sm px-4 py-2.5 text-center inline-flex items-center block w-full"
            type="button"
            data-dropdown-toggle="dropdown"
          >
            역할 이름(ex 사원)
            <svg
              class="w-4 h-4 ml-2"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M19 9l-7 7-7-7"
              ></path>
            </svg>
          </button>

          <!-- Dropdown menu -->
          <div
            class="absolute hidden bg-white text-base z-50 list-none divide-y divide-gray-100 rounded shadow my-4"
            id="dropdownRole"
          >
            <!-- <div class="px-4 py-3">
                <span class="block text-sm">Bonnie Green</span>
                <span class="block text-sm font-medium text-gray-900 truncate"
                  >name@flowbite.com</span
                >
              </div> -->
            <ul class="py-1" aria-labelledby="dropdown">
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2">역할</a>
              </li>
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >리스트를 보여줍니다</a
                >
              </li>
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >추후 스크롤바로 구현합니다</a
                >
              </li>
              <li>
                <a href="#" class="text-sm hover:bg-gray-100 text-gray-700 block px-4 py-2"
                  >역할 이름이 바뀝니다</a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>

      <button
        type="submit"
        class="w-full text-white bg-samsung-blue focus:ring-4 focus:ring-blue-300 rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        확인
      </button>

      <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
      Not registered?
      <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
  </div> -->
    </form>
  </BaseModal>

  <!-- + 기호 -->
  <!-- <div>
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 16 16"
      fill="currentColor"
      class="w-3 h-3"
    >
      <path
        d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z"
      />
    </svg>
  </div> -->
</template>

<script setup>
import { ref } from 'vue'
import BaseModal from './BaseModal.vue'
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const teamName = ref('현재 팀 이름')
const { toggleHidden } = commonStore

const currentTab = ref('role')
const moveToTeamInfo = () => {
  currentTab.value = 'info'
}
const moveToTeamRole = () => {
  currentTab.value = 'role'
}
//
</script>

<style scoped></style>
