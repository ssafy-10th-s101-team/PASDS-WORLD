<template>
  <!-- 버튼 -->
  <div class="max-w-2xl mx-auto flex justify-between sm:p-6 lg:p-8">
    <div class="max-w-lg">
      <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
        <button
          @click="moveToTeamRole"
          type="button"
          class="rounded-l-lg border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:placeholder-gray-700 dark:hover:bg-gray-600 dark:hover:text-white dark:text-white"
        >
          역할
        </button>

        <button
          @click="moveToTeamInfo"
          type="button"
          class="rounded-r-md border border-gray-200 bg-white text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:placeholder-gray-700 dark:hover:bg-gray-600 dark:hover:text-white dark:text-white"
        >
          팀 정보
        </button>
      </div>
    </div>
    <div v-if="currentTab === 'info'" class="mt-5" @click="toggleHidden('teamInvitationModal')">
      <BaseButton buttonText="초대" />
    </div>
  </div>
  <!-- 버튼 끝 -->
  <!-- This is an example component -->

  <div
    v-if="currentTab === 'role'"
    class="max-w-2xl mx-auto max-w-2xl mx-auto p-16 bg-white rounded-lg sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
  >
    <div class="flex flex-col w-full sm:p-6 lg:p-8">
      <div class="overflow-x-auto shadow-md sm:rounded-lg max-w-full">
        <div class="inline-block min-w-full align-middle">
          <div class="overflow-hidden">
            <table class="min-w-full divide-y divide-gray-200 table-fixed dark:divide-gray-700">
              <thead class="bg-gray-100 dark:bg-gray-700">
                <tr>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    역할
                  </th>

                  <th scope="col" class="sr-only"></th>
                  <th scope="col" class="p-4 w-1/6">
                    <span
                      class="py-3 px-6 text-xs tracking-wider text-right text-gray-700 uppercase dark:text-gray-400 w-1/6"
                      >Edit</span
                    >
                  </th>
                </tr>
              </thead>
              <tbody
                class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
              >
                <!-- 체크박스 -->
                <tr
                  v-for="role in roles"
                  :key="role.roleId"
                  class="hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                  <!-- <td class="p-4 w-4">
                      <div class="flex items-center">
                        <input
                        id="checkbox-table-1"
                        type="checkbox"
                        class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                        />
                        <label for="checkbox-table-1" class="sr-only">checkbox</label>
                      </div>
                    </td> -->
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    {{ role.name }}
                  </td>

                  <td
                    class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                  ></td>
                  <td class="py-4 px-6 text-sm text-center whitespace-nowrap">
                    <div
                      class="text-samsung-blue dark:text-blue-500 hover:underline"
                      @click="toggleHidden('authorization')"
                    >
                      . . .
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="flex justify-center">
      <BaseButton buttonText="역할 추가" @click="toggleHidden('teamRoleCreationModal')" />
    </div>
  </div>

  <!-- 팀 정보 -->
  <div
    v-if="currentTab === 'info'"
    class="grid gap-6 items-baseline mb-6 max-w-2xl mx-auto p-16 bg-white rounded-lg sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
  >
    <div class="">
      <div class="flex justify-between items-center">
        <div>
          <label for="input_text" class="block mb-2 text-sm text-gray-900 dark:text-gray-300">
            팀 이름
          </label>
          <div
            id="teamName"
            class="text-gray-900 text-lg rounded-lg block w-full py-2.5 dark:border-gray-700 dark:placeholder-gray-700 dark:text-white"
          >
            {{ teamName }}
          </div>
        </div>
        <div class="inline-block align-baseline">
          <BaseButton buttonText="변경" />
        </div>
        <div>
          <label for="input_text" class="block mb-2 text-sm text-gray-900 dark:text-gray-300">
            팀장(관리자)
          </label>
          <div
            id="teamLeader"
            class="text-gray-900 text-lg rounded-lg block w-full py-2.5 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
          >
            {{ teamLeader }}
          </div>
        </div>

        <div class="inline-block align-baseline">
          <BaseButton buttonText="변경" />
        </div>
      </div>
      <div class="flex flex-col">
        <div class="overflow-x-auto shadow-md sm:rounded-lg max-w-full">
          <div class="inline-block min-w-full align-middle">
            <div class="overflow-hidden">
              <table class="min-w-full divide-y divide-gray-200 table-fixed dark:divide-gray-700">
                <thead class="bg-gray-100 dark:bg-gray-700">
                  <tr>
                    <th
                      scope="col"
                      class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                    >
                      닉네임
                    </th>

                    <th scope="col" class="sr-only"></th>
                    <th
                      scope="col"
                      class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                    >
                      역할
                    </th>
                    <th scope="col" class="sr-only"></th>
                    <th scope="col" class="p-4 w-2/6">
                      <span
                        class="py-3 px-6 text-xs tracking-wider text-right text-gray-700 uppercase dark:text-gray-400 w-1/6"
                        >Edit</span
                      >
                    </th>
                  </tr>
                </thead>
                <tbody
                  class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
                >
                  <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                    <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                      진뚱이용
                    </td>

                    <td
                      class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                    ></td>

                    <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                      사원
                    </td>
                    <td
                      class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                    ></td>
                    <td class="py-4 px-6 text-sm text-center whitespace-nowrap dark:text-white">
                      <div
                        class="text-samsung-blue dark:text-blue-500 hover:underline"
                        @click="toggleHidden('memberRole')"
                      >
                        . . .
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <DashboardAuthorizationModal />
  <DashboardMemberRoleModal />
  <TeamInvitationModal />
  <TeamRoleCreationModal :teamId="teamId" />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import DashboardAuthorizationModal from '../common/DashboardAuthorizationModal.vue'
import DashboardMemberRoleModal from '../common/DashboardMemberRoleModal.vue'
import TeamInvitationModal from '../common/TeamInvitationModal.vue'
import TeamRoleCreationModal from '../common/TeamRoleCreationModal.vue'
import BaseButton from '../common/BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import { getAuthority, getRole } from '@/api/role'
const commonStore = useCommonStore()
const route = useRoute()
const teamId = ref(null)
const teamName = ref('현재 팀 이름')
const teamLeader = ref('현재 팀장')
const { toggleHidden } = commonStore

const currentTab = ref('role')
const moveToTeamInfo = () => {
  currentTab.value = 'info'
}
const moveToTeamRole = () => {
  currentTab.value = 'role'
}

const roles = ref([])

onMounted(async () => {
  teamId.value = route.query.teamId
  console.log('현재 팀의 id:', teamId.value)
  const fetchrole = await fetchRole(teamId.value)
  roles.value = fetchrole
  console.log('내 팀의 역할들', roles.value)
})

// 역할 목록 가져오기
const fetchRole = async (teamId) => {
  try {
    return await getRole(teamId)
  } catch (error) {
    console.error('Unexpected error:', error)
  }
}
</script>

<style scoped></style>
