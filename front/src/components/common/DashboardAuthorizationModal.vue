<template>
  <div class="max-w-2xl mx-auto">
    <!-- Main modal -->
    <div
      id="authorization"
      aria-hidden="true"
      class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center rounded-lg"
    >
      <div class="relative w-60 max-w-md px-4 h-full md:h-auto max-w-full max-h-full overflow-auto">
        <!-- Modal content -->
        <div class="bg-white rounded-lg shadow relative dark:bg-gray-700">
          <div class="flex justify-end p-2">
            <button
              @click="toggleHidden('authorization')"
              type="button"
              class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white"
              data-modal-toggle="modal"
            >
              <svg
                class="w-5 h-5"
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"
                ></path>
              </svg>
            </button>
          </div>
          <div class="px-6">
            <fieldset>
              <legend class="sr-only">Checkbox variants</legend>

              <div class="flex items-center items-start mb-4">
                <input
                  id="checkbox-1"
                  aria-describedby="checkbox-1"
                  type="checkbox"
                  class="bg-gray-50 border-gray-300 focus:ring-3 focus:ring-blue-300 h-4 w-4 rounded"
                  checked=""
                />
                <label for="checkbox-1" class="text-sm ml-3 font-medium text-gray-900"
                  >비밀 읽기</label
                >
              </div>

              <div class="flex items-start items-center mb-4">
                <input
                  id="checkbox-2"
                  aria-describedby="checkbox-2"
                  type="checkbox"
                  class="bg-gray-50 border-gray-300 focus:ring-3 focus:ring-blue-300 h-4 w-4 rounded"
                />
                <label for="checkbox-2" class="text-sm ml-3 font-medium text-gray-900"
                  >비밀 관리</label
                >
              </div>

              <div class="flex items-start items-center mb-4">
                <input
                  id="checkbox-3"
                  aria-describedby="checkbox-3"
                  type="checkbox"
                  class="bg-gray-50 border-gray-300 focus:ring-3 focus:ring-blue-300 h-4 w-4 rounded"
                />
                <label for="checkbox-3" class="text-sm ml-3 font-medium text-gray-900"
                  >역할 관리</label
                >
              </div>

              <div class="flex items-start mb-4">
                <div class="flex items-center h-5">
                  <input
                    id="shipping-2"
                    aria-describedby="shipping-2"
                    type="checkbox"
                    class="bg-gray-50 border-gray-300 focus:ring-3 focus:ring-blue-300 h-4 w-4 rounded"
                  />
                </div>
                <div class="text-sm ml-3">
                  <label for="shipping-2" class="font-medium text-gray-900">팀원관리</label>
                  <div class="text-gray-500"></div>
                </div>
              </div>
            </fieldset>
            <div @click="toggleHidden('authorization')" class="flex justify-center pb-6">
              <button
                type="submit"
                class="text-white bg-samsung-blue hover:bg-samsung-blue focus:ring-4 focus:ring-samsung-blue font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:bg-samsung-blue dark:hover:bg-samsung-blue focus:outline-none dark:focus:ring-samsung-blue"
              >
                확인
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
</script>

<style scoped></style>
