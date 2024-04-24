<template>
  <div class="max-w-2xl mx-auto flex justify-between">
    <!-- This is an example component -->
    <div class="max-w-lg">
      <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
        <button
          type="button"
          class="rounded-l-lg border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
        >
          개발
        </button>
        <button
          type="button"
          class="border-t border-b border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
        >
          영업
        </button>
        <button
          type="button"
          class="rounded-r-md border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
        >
          마케팅
        </button>
      </div>
    </div>
    <div class="mt-5">
      <BaseButton buttonText="팀 추가" />
    </div>
  </div>
</template>

<script setup>
import BaseButton from './BaseButton.vue'
</script>

<style scoped></style>
