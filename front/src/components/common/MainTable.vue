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
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    이름
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    종류
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    생성자
                  </th>
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
                  <th scope="col" class="p-4 w-1/6">
                    <span class="sr-only">Edit</span>
                  </th>
                </tr>
              </thead>
              <tbody
                class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
              >
                <tr
                  v-for="data in privateDataList"
                  :key="data.privateDataId"
                  class="hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    {{ data.title }}
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-500 whitespace-nowrap dark:text-white"
                  >
                    {{ data.type }}
                  </td>
                  <td
                    class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    {{ data.createdBy }}
                  </td>
                  <td class="p-4 w-4 sr-only">
                    <div class="flex items-center">
                      <input
                        type="checkbox"
                        :id="`checkbox-table-${data.privateDataId}`"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      />
                      <label :for="`checkbox-table-${data.privateDataId}`" class="sr-only"
                        >checkbox</label
                      >
                    </div>
                  </td>
                  <td class="py-4 px-6 text-sm font-medium text-center whitespace-nowrap">
                    <MainPrivateDataDetail :data="data" />
                    <a
                      href="#"
                      @click="toggleHidden('privateDataDetail')"
                      class="text-blue-600 dark:text-blue-500 hover:underline"
                      >. . .</a
                    >
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="flex justify-center">
    <BaseButton buttonText="추가 +" @click="toggleHidden('privateDataCreate')" />
  </div>
  <MainPrivateDataCreate :teamId="selectedTeamId" />
</template>

<script setup>
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import MainPrivateDataCreate from './MainPrivateDataCreate.vue'
import MainPrivateDataDetail from './MainPrivateDataDetail.vue'
import { watch, ref, defineProps } from 'vue'
import { getPrivateDatas } from '@/api/data'

const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const privateDataList = ref([])
const showDetail = ref(false)
const selectedDataId = ref(null)
const props = defineProps({
  selectedTeamId: Number
})

const fetchprivateDatas = async (teamId) => {
  return await getPrivateDatas(teamId)
}

watch(
  () => props.selectedTeamId,
  async (newVal) => {
    if (newVal) {
      const response = await fetchprivateDatas(newVal)
      privateDataList.value = response
    }
  },
  { immediate: true }
)
</script>

<style scoped></style>
