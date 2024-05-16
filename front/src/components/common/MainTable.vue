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
                    <a
                      href="#"
                      @click="showDetail(data.privateDataId)"
                      class="text-blue-600 dark:text-blue-500 hover:underline cursor-pointer"
                      >. . .</a
                    >
                  </td>
                </tr>
              </tbody>
            </table>
            <MainPrivateDataDetail
              :privateDataId="selectedDataId"
              :teamId="props.selectedTeamId"
              @private-deleted="handlePrivateDeleted"
              @private-updated="handlePrivateUpdated"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="flex justify-center mt-2">
    <BaseButton
      v-if="selectedTeamId !== -1"
      buttonText="비밀 추가 +"
      @click="toggleHidden('privateDataCreate')"
    />
  </div>
  <div class="flex justify-center pt-6">
    <BasePagination
      :current-page="currentPage"
      :total-pages="totalPages"
      @change-page="changePage"
    />
  </div>
  <MainPrivateDataCreate
    :teamId="selectedTeamId"
    :roles="teamRoles"
    @private-created="handlePrivateCreated"
  />
</template>

<script setup>
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import MainPrivateDataCreate from './MainPrivateDataCreate.vue'
import MainPrivateDataDetail from './MainPrivateDataDetail.vue'
import { watch, ref, defineProps, nextTick } from 'vue'
import { getPrivateDatas } from '@/api/data'
import { getRole } from '@/api/role'
import BasePagination from '@/components/common/BasePagination.vue'

const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const privateDataList = ref([])
const selectedDataId = ref()
const currentPage = ref(1)
const totalPages = ref(10)
const teamRoles = ref([])

const props = defineProps({
  selectedTeamId: Number
})

const fetchPrivateData = async () => {
  if (props.selectedTeamId !== -1) {
    const roleResponse = await getRole(props.selectedTeamId)
    teamRoles.value = roleResponse.filter(
      (role) => role.name !== 'HEADER' && role.name !== 'LEADER'
    )
    const response = await getPrivateDatas(props.selectedTeamId, currentPage.value)
    privateDataList.value = response.privateDataResponse
    totalPages.value = response.totalPages
  }
}

const showDetail = (privateDataId) => {
  selectedDataId.value = privateDataId
  toggleHidden('privateDataDetail')
}

watch(
  [() => props.selectedTeamId, () => currentPage.value],
  () => {
    if (props.selectedTeamId === -1) {
      privateDataList.value = []
      totalPages.value = 0
    } else {
      fetchPrivateData()
    }
  },
  {
    immediate: true
  }
)

function changePage(page) {
  currentPage.value = page
}

function handlePrivateCreated() {
  fetchPrivateData()
}

function handlePrivateDeleted() {
  fetchPrivateData()
}

function handlePrivateUpdated() {
  fetchPrivateData()
}
</script>

<style scoped></style>
