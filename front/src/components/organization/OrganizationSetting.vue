<template>
  <div>팀이름</div>
  <div class="flex flex-wrap min-w-32 mb-4">
    <input
      type="text"
      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      v-model="teamName"
    />
  </div>
  <div class="">팀장(관리자)</div>
  <div class="max-w-lg mx-auto">
    <button
      @click="toggleHidden('dropdownMember')"
      class="border border-gray-300 text-gray-700 bg-gray-50 hover:bg-samsung-blue hover:text-white rounded-lg text-sm px-4 py-2.5 text-center inline-flex items-center block w-full"
      type="button"
      data-dropdown-toggle="dropdown"
    >
      팀장 이름
      <svg
        class="w-4 h-4 ml-2"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M19 9l-7 7-7-7"
        ></path>
      </svg>
    </button>
  </div>
  <br />
  <div>조직삭제</div>
</template>

<script setup></script>

<style scoped></style>
