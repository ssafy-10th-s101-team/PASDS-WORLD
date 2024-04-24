<template>
  <div>
    <label for="input_text" class="block mb-2 text-sm text-gray-900 dark:text-gray-300">
      {{ inputText }}
    </label>
    <input
      type="text"
      id="input_text"
      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      :placeholder="placeHolder"
      required
    />
  </div>
</template>

<script setup>
const props = defineProps({
  inputText: {
    type: String,
    required: true
  },
  placeHolder: {
    type: String,
    required: true
  }
})
const inputText = props.inputText
const placeHolder = props.placeHolder
</script>

<style scoped></style>
