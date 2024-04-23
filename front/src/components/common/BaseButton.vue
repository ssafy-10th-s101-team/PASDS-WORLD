<template>
  <div>
    <button
      href="#"
      class="text-white bg-samsung-blue hover:bg-samsung-blue focus:ring-4 focus:ring-samsung-blue font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:bg-samsung-blue dark:hover:bg-samsung-blue focus:outline-none dark:focus:ring-samsung-blue"
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
  }
})
const buttonText = props.buttonText
</script>

<style scoped></style>
