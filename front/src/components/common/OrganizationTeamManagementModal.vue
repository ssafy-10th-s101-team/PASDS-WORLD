`
<template>
  <BaseModal modalId="organizationTeamManagementModal">
    <!-- 버튼 -->
    <div class="max-w-2xl mx-auto flex justify-start pl-6">
      <div class="max-w-lg">
        <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
          <button
            type="button"
            class="rounded-l-lg border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
          >
            역할
          </button>

          <button
            type="button"
            class="rounded-r-md border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
          >
            팀 정보
          </button>
        </div>
      </div>
    </div>
    <!-- 버튼 끝 -->
    <form class="flex flex-wrap space-y-6 px-6 lg:px-8 py-4 sm:pb-6 xl:pb-8" action="#">
      <!-- 역할 -->
      <div>
        <div class="flex flew-wrap items-center min-w-32 mb-4">
          <div class="mr-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 16 16"
              class="text-samsung-blue w-3 h-3"
              fill="currentColor"
            >
              <path
                fill-rule="evenodd"
                d="M11.89 4.111a5.5 5.5 0 1 0 0 7.778.75.75 0 1 1 1.06 1.061A7 7 0 1 1 15 8a2.5 2.5 0 0 1-4.083 1.935A3.5 3.5 0 1 1 11.5 8a1 1 0 0 0 2 0 5.48 5.48 0 0 0-1.61-3.889ZM10 8a2 2 0 1 0-4 0 2 2 0 0 0 4 0Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>
          <div>팀원</div>
        </div>
        <!-- 역할 끝 -->
        <!-- 역할 -->
        <div class="flex flew-wrap items-center min-w-32 mb-4">
          <div class="mr-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 16 16"
              class="text-samsung-blue w-3 h-3"
              fill="currentColor"
            >
              <path
                fill-rule="evenodd"
                d="M11.89 4.111a5.5 5.5 0 1 0 0 7.778.75.75 0 1 1 1.06 1.061A7 7 0 1 1 15 8a2.5 2.5 0 0 1-4.083 1.935A3.5 3.5 0 1 1 11.5 8a1 1 0 0 0 2 0 5.48 5.48 0 0 0-1.61-3.889ZM10 8a2 2 0 1 0-4 0 2 2 0 0 0 4 0Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>
          <div>팀장</div>
        </div>
        <!-- 역할 끝 -->
      </div>
      <button
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        확인
      </button>

      <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
    Not registered?
    <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
</div> -->
    </form>
  </BaseModal>
  <!-- + 기호 -->
  <!-- <div>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 16 16"
    fill="currentColor"
    class="w-3 h-3"
  >
    <path
      d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z"
    />
  </svg>
</div> -->
</template>
`

<script setup>
import BaseModal from './BaseModal.vue'
</script>

<style scoped></style>
