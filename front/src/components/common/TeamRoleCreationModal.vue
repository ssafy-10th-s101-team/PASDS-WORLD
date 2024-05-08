<template>
  <BaseModal modalId="teamRoleCreationModal">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">역할 추가</h3>
      <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
        <div>
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >역할 이름</label
          >
          <input
            type="text"
            id="roleName"
            v-model="roleName"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder=""
            required
          />
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
        </div>

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
    </div>
  </BaseModal>
</template>

<script setup>
import BaseModal from './BaseModal.vue'
</script>

<style scoped></style>
