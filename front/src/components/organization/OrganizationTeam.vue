<template>
  <!-- This is an example component -->
  <div class="max-w-2xl mx-auto">
    <div class="flex flex-col">
      <div class="overflow-x-auto shadow-md sm:rounded-lg max-w-full">
        <div class="inline-block min-w-full align-middle">
          <div class="overflow-hidden">
            <table class="min-w-full divide-y divide-gray-200 table-fixed dark:divide-gray-700">
              <thead class="bg-gray-100 dark:bg-gray-700">
                <tr>
                  <th scope="col" class="p-4 w-1/6 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-all"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-all" class="sr-only">checkbox</label>
                    </div>
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    팀 명
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    내 역할
                  </th>
                  <th scope="col" class="sr-only"></th>
                  <th scope="col" class="p-4 w-1/6 no-wrap">
                    <span
                      class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                      >비밀 수</span
                    >
                  </th>
                </tr>
              </thead>
              <tbody
                class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
              >
                <tr
                  v-for="team in teams"
                  :key="team.teamId"
                  class="hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        id="checkbox-table-1"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label for="checkbox-table-1" class="sr-only">checkbox</label>
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    {{ team.teamName }}
                  </td>
                  <td class="py-4 px-6 text-sm text-gray-500 whitespace-nowrap dark:text-white">
                    {{ team.role }}
                  </td>
                  <td
                    class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                  ></td>
                  <td class="py-4 px-6 text-sm text-center whitespace-nowrap">
                    <router-link :to="{ name: 'teamManagement' }">
                      <!-- <a href="#" class="text-blue-600 dark:text-blue-500 hover:underline"> -->
                      {{ team.secretCount }}
                      <!-- </a> -->
                    </router-link>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getAdminTeams } from '@/api/team'
const props = defineProps({
  selectedOrganizationId: Number
})

const teams = ref([])

watch(
  () => props.selectedOrganizationId,
  async (newVal, oldVal) => {
    if (newVal !== oldVal) {
      teams.value = await getAdminTeams(newVal)
    }
  },
  { immediate: true }
) // immediate: true로 설정하면 컴포넌트 마운트 시에도 함수가 실행됩니다.

onMounted(async () => {
  teams.value = await getAdminTeams(props.selectedOrganizationId)
})
</script>

<style scoped>
.no-wrap {
  white-space: nowrap;
}
</style>
