<template>
  <div>
    <button
      type="button"
      class="font-samsungone700c text-white bg-samsung-blue hover:bg-samsung-blue focus:ring-4 focus:ring-samsung-blue rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:text-white dark:border dark:border-gray-600 dark:bg-black dark:hover:bg-gray-600 focus:outline-none dark:focus:ring-samsung-blue"
      :disabled="loading"
    >
      {{ buttonText }}
    </button>
  </div>
</template>

<script setup>
const props = defineProps({
  buttonText: {
    type: String,
    required: true
  },
  loading: {  // loading props 가 전달되지 않으면 vue.js 가 undefined 로 처리 -> js 는 false 로 처리
    required: false
  }
})
const buttonText = props.buttonText
</script>

<style scoped></style>
