<template>
  <!-- This is an example component -->
  <div class="max-w-2xl mx-auto">
    <!-- Main modal -->
    <div
      :id="modalId"
      aria-hidden="true"
      class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center rounded-lg z-10"
    >
      <div
        class="relative w-full max-w-md px-4 h-full md:h-auto max-w-full max-h-full overflow-auto"
      >
        <!-- Modal content -->
        <div class="bg-white rounded-lg shadow relative dark:bg-gray-700">
          <div class="flex justify-end p-2">
            <button
              @click="toggleHidden(modalId)"
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
          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore

const props = defineProps({
  modalId: {
    type: String,
    required: true
  }
})
const modalId = props.modalId
</script>

<style scoped></style>
