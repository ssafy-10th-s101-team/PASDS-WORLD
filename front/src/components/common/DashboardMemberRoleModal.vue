<template>
  <div class="max-w-2xl mx-auto">
    <!-- Main modal -->
    <div
      id="memberRole"
      aria-hidden="true"
      class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center rounded-lg"
    >
      <div class="relative w-60 max-w-md px-4 h-full md:h-auto max-w-full max-h-full overflow-auto">
        <!-- Modal content -->
        <div class="bg-white rounded-lg shadow relative dark:bg-gray-700">
          <div class="flex justify-end p-2">
            <button
              @click="toggleHidden('memberRole')"
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
          <!-- 라디오 -->

          <div class="max-w-lg mx-auto px-6">
            <fieldset class="mb-5">
              <legend class="sr-only">Roles</legend>

              <div class="flex items-center mb-4">
                <input
                  id="role-option-1"
                  type="radio"
                  name="roles"
                  value="USA"
                  class="h-4 w-4 border-gray-300 focus:ring-2 focus:ring-blue-300"
                  aria-labelledby="role-option-1"
                  aria-describedby="role-option-1"
                  checked=""
                />
                <label for="role-option-1" class="text-sm font-medium text-gray-900 ml-2 block">
                  손님
                </label>
              </div>

              <div class="flex items-center mb-4">
                <input
                  id="role-option-2"
                  type="radio"
                  name="roles"
                  value="Germany"
                  class="h-4 w-4 border-gray-300 focus:ring-2 focus:ring-blue-300"
                  aria-labelledby="role-option-2"
                  aria-describedby="role-option-2"
                />
                <label for="role-option-2" class="text-sm font-medium text-gray-900 ml-2 block">
                  팀원
                </label>
              </div>

              <div class="flex items-center mb-4">
                <input
                  id="role-option-3"
                  type="radio"
                  name="roles"
                  value="Spain"
                  class="h-4 w-4 border-gray-300 focus:ring-2 focus:ring-blue-300"
                  aria-labelledby="role-option-3"
                  aria-describedby="role-option-3"
                />
                <label for="role-option-3" class="text-sm font-medium text-gray-900 ml-2 block">
                  진뚱
                </label>
              </div>

              <div class="flex items-center mb-4">
                <input
                  id="role-option-4"
                  type="radio"
                  name="roles"
                  value="United Kingdom"
                  class="h-4 w-4 border-gray-300 focus:ring-2 focus:ring-blue-300"
                  aria-labelledby="role-option-4"
                  aria-describedby="role-option-4"
                />
                <label for="role-option-4" class="text-sm font-medium text-gray-900 ml-2 block">
                  과장
                </label>
              </div>
            </fieldset>
          </div>

          <!-- 체크박스 -->

          <div @click="toggleHidden('memberRole')" class="flex justify-center pb-6">
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
</template>

<script setup>
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
</script>

<style scoped></style>
