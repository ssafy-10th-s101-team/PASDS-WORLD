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
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    Private Data
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    종류
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    소유자
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
                    <span class="sr-only">Edit</span>
                  </th>
                </tr>
              </thead>
              <tbody
                class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
              >
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    Youtube
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                  >
                    Login
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    신우섭
                  </td>
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-table-1"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-table-1" class="sr-only">checkbox</label>
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm font-medium text-center whitespace-nowrap">
                    <a href="#" class="text-blue-600 dark:text-blue-500 hover:underline">. . .</a>
                  </td>
                </tr>
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    pasdsworld pem
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                  >
                    Text
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    이준범
                  </td>
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-table-2"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-table-2" class="sr-only">checkbox</label>
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm font-medium text-center whitespace-nowrap">
                    <a href="#" class="text-blue-600 dark:text-blue-500 hover:underline">. . .</a>
                  </td>
                </tr>
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    MySQL PW
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                  >
                    Text
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    이하은
                  </td>
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-table-3"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-table-3" class="sr-only">checkbox</label>
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm font-medium text-center whitespace-nowrap">
                    <a href="#" class="text-blue-600 dark:text-blue-500 hover:underline">. . .</a>
                  </td>
                </tr>
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    Naver
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                  >
                    Login
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    김진용
                  </td>
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-table-4"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-table-4" class="sr-only">checkbox</label>
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm font-medium text-center whitespace-nowrap">
                    <a href="#" class="text-blue-600 dark:text-blue-500 hover:underline">. . .</a>
                  </td>
                </tr>
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    우리집 비밀번호
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                  >
                    Text
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    유광우
                  </td>
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-table-5"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-table-5" class="sr-only">checkbox</label>
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm font-medium text-center whitespace-nowrap">
                    <a href="#" class="text-blue-600 dark:text-blue-500 hover:underline">. . .</a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="flex justify-center">
    <BaseButton buttonText="추가 +" @click="toggleHidden('privateInfoDetail')" />
  </div>
  <DashboardPrivateInfoDetail />
</template>

<script setup>
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import DashboardPrivateInfoDetail from './DashboardPrivateInfoDetail.vue'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
</script>

<style scoped></style>
