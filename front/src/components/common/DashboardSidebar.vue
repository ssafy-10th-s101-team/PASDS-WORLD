<template>
  <div class="grid grid-cols-auto-1fr">
    <!-- <div class="fixed z-50"> -->
    <!-- This is an example component -->
    <div class="max-w-2xl mx-auto lg:ml-0">
      <aside class="w-64" aria-label="Sidebar">
        <div class="px-3 py-4 overflow-y-auto rounded bg-gray-50 dark:bg-gray-800">
          <ul class="space-y-2">
            <li>
              <button
                type="button"
                class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700"
                aria-controls="dropdown-example"
                data-collapse-toggle="dropdown-example"
                @click="toggleHidden('dropdown-example')"
              >
                <span class="flex-1 ml-3 text-left whitespace-nowrap" sidebar-toggle-item
                  >모든 조직</span
                >
                <svg
                  sidebar-toggle-item
                  class="w-6 h-6"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                    clip-rule="evenodd"
                  ></path>
                </svg>
              </button>
              <ul id="dropdown-example" class="py-2 space-y-2">
                <li>
                  <a
                    href="#"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >MY GROUP</a
                  >
                </li>
                <li>
                  <a
                    href="#"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >S101</a
                  >
                </li>
                <li>
                  <a
                    href="#"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >삼성 SDS</a
                  >
                </li>
              </ul>
            </li>

            <li>
              <a
                href="#"
                class="flex items-center p-2 text-base text-gray-900 rounded-lg dark:text-white hover:bg-samsung-blue hover:text-white dark:hover:bg-gray-700"
                @click="toggleHidden('organizationCreationModal')"
              >
                <span class="flex-1 ml-3 whitespace-nowrap">조직 추가 +</span>
              </a>
              <OrganizationCreationModal />
            </li>
            <!-- <li>
              <a
                href="#"
                class="flex items-center p-2 text-base text-gray-900 rounded-lg dark:text-white hover:bg-samsung-blue hover:text-white dark:hover:bg-gray-700"
              >
                <svg
                  class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M5 3a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2V5a2 2 0 00-2-2H5zM5 11a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2v-2a2 2 0 00-2-2H5zM11 5a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V5zM11 13a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"
                  ></path>
                </svg>
                <span class="flex-1 ml-3 whitespace-nowrap">Kanban</span>
                <span
                  class="inline-flex items-center justify-center px-2 ml-3 text-sm font-medium text-gray-800 bg-gray-200 rounded-full dark:bg-gray-700 dark:text-gray-300"
                  >Pro</span
                >
              </a>
            </li>
            <li>
              <a
                href="#"
                class="flex items-center p-2 text-base text-gray-900 rounded-lg dark:text-white hover:bg-samsung-blue hover:text-white dark:hover:bg-gray-700"
              >
                <svg
                  class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M8.707 7.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l2-2a1 1 0 00-1.414-1.414L11 7.586V3a1 1 0 10-2 0v4.586l-.293-.293z"
                  ></path>
                  <path
                    d="M3 5a2 2 0 012-2h1a1 1 0 010 2H5v7h2l1 2h4l1-2h2V5h-1a1 1 0 110-2h1a2 2 0 012 2v10a2 2 0 01-2 2H5a2 2 0 01-2-2V5z"
                  ></path>
                </svg>
                <span class="flex-1 ml-3 whitespace-nowrap">Inbox</span>
                <span
                  class="inline-flex items-center justify-center w-3 h-3 p-3 ml-3 text-sm font-medium text-blue-600 bg-blue-200 rounded-full dark:bg-blue-900 dark:text-blue-200"
                  >3</span
                >
              </a>
            </li> -->

            <!-- 아이콘: 쇼핑백 -->
            <!-- <svg
                  class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900 dark:text-gray-400 dark:group-hover:text-white"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M10 2a4 4 0 00-4 4v1H5a1 1 0 00-.994.89l-1 9A1 1 0 004 18h12a1 1 0 00.994-1.11l-1-9A1 1 0 0015 7h-1V6a4 4 0 00-4-4zm2 5V6a2 2 0 10-4 0v1h4zm-6 3a1 1 0 112 0 1 1 0 01-2 0zm7-1a1 1 0 100 2 1 1 0 000-2z"
                    clip-rule="evenodd"
                  ></path>
                </svg> -->
            <!-- 아이콘: 사람모양 -->
            <!-- <svg
                  class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                    clip-rule="evenodd"
                  ></path>
                </svg> -->
          </ul>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { useCommonStore } from '@/stores/common'
import { localAxios } from '@/utils/http-commons'
import OrganizationCreationModal from './OrganizationCreationModal.vue'
import { onMounted } from 'vue'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore

onMounted(() => {
  getOrganization
})

const getOrganization = function () {
  localAxios({
    method: 'GET',
    url: `/organization`,
    data: {
      headers: { 'Access-Token': 'Bearer fjaskghsdkvvjkdalbdfklajghf123r' }
    }
  })
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
      const errmsg = err.response.data.message
      console.log(errmsg)
    })
}
</script>

<style scoped></style>
