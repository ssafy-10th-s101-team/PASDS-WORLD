<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">조직 설정</h3>
      <label for="organizationName" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
        >조직명</label
      >
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <!-- 조직명 입력 필드 -->
        <div>
          <input
            type="text"
            id="organizationName"
            v-model="organizationName"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="orgnizationName"
            required
          />
        </div>
        <div class="flex justify-center">
          <BaseButton buttonText="변경" />
        </div>
      </div>

      <!-- 조직장 변경 -->
      <label for="organizationName" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
        >조직장</label
      >
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <div
          class="text-black text-lg rounded-lg block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        >
          {{ organizationLeader }}
        </div>
        <div class="flex justify-center">
          <BaseButton buttonText="변경" />
        </div>
      </div>

      <div class="text-red-700 text-sm">조직삭제</div>

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
</template>

<script setup>
import { ref } from 'vue'
import BaseButton from '../common/BaseButton.vue'

const organizationName = ref('hi')
const organizationLeader = ref('신우섭')
</script>

<style scoped></style>
