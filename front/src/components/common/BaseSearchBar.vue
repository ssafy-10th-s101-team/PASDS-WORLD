<template>
  <div>
    <!-- This is an example component -->
    <div class="relative max-w-2xl mx-auto w-full search-component">
      <form class="flex items-center" @submit.prevent="searchQuery">
        <label for="simple-search" class="sr-only">Search</label>
        <div class="relative w-full">
          <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
            <svg
              class="w-5 h-5 text-gray-500 dark:text-gray-400"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                clip-rule="evenodd"
              ></path>
            </svg>
          </div>
          <input
            type="text"
            id="simple-search"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full pl-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="검색"
            required
            v-model="searchText"
            @input="fetchResults"
            @focus="handleFocus"
          />
        </div>
        <button
          type="submit"
          class="p-2.5 ml-2 text-sm font-medium text-white bg-samsung-blue rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          <svg
            class="w-5 h-5"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
            ></path>
          </svg>
        </button>
      </form>
      <!-- 검색 결과 리스트 -->
      <ul
        v-if="searchResults.length > 0"
        class="search-results absoulute w-full z-10 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full pl-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      >
        <li
          v-for="result in searchResults"
          :key="result.privateDataId"
          @click="goToResult(result)"
          class="cursor-pointer hover:bg-gray-100"
        >
          {{ result.title }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, defineEmits } from 'vue'
import { searchPrivateData } from '@/api/data'

const emit = defineEmits([
  'organization-search-selected',
  'team-search-selected',
  'privateData-search-selected'
])
const searchText = ref('')
const searchResults = ref([])
const lastSearch = ref('')

const fetchResults = async () => {
  if (searchText.value.length >= 1) {
    try {
      const response = await searchPrivateData(14, searchText.value)
      searchResults.value = response
      lastSearch.value = searchText.value
    } catch (error) {
      searchResults.value = []
    }
  } else {
    searchResults.value = []
  }
}

function searchQuery() {
  fetchResults()
}
function goToResult(result) {
  emit('organization-search-selected', result.organizationId)
  emit('team-search-selected', result.teamId)
  emit('privateData-search-selected', result.privateDataId)
}

function handleFocus() {
  if (lastSearch.value) {
    searchText.value = lastSearch.value
    fetchResults()
  }
}

function handleClickOutside(event) {
  const searchComponent = document.querySelector('.search-component') // 검색 컴포넌트의 클래스 이름을 지정합니다.
  if (!searchComponent.contains(event.target)) {
    searchResults.value = []
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.search-results {
  list-style-type: none;
  padding-left: 0;
  border: 1px solid #ccc;
  max-height: 196px;
  position: absolute;
  overflow-y: auto;
  top: 100%;
  width: 100%;
  z-index: 1000;
}
.search-results li {
  padding: 8px;
  border-bottom: 1px solid #eee;
}
.search-results li:last-child {
  border-bottom: none;
}
</style>
