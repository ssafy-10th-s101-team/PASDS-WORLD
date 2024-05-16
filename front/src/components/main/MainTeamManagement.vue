<template>
  <!-- 버튼 -->
  <div class="max-w-2xl mx-auto flex justify-between sm:px-6 lg:px-8">
    <h2>{{ teamName }} 팀 설정 페이지</h2>
    <button type="button" @click="goBack">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        class="w-6 h-6"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M9 15 3 9m0 0 6-6M3 9h12a6 6 0 0 1 0 12h-3"
        />
      </svg>
    </button>
  </div>
  <div class="max-w-2xl mx-auto flex justify-between sm:px-6 lg:px-8">
    <div class="max-w-lg">
      <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
        <button
          @click="moveToTeamRole"
          type="button"
          :class="{ 'bg-samsung-blue text-white': currentTab === 'role' }"
          class="rounded-l-lg border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 dark:bg-gray-800 dark:border-gray-700 dark:placeholder-gray-700 dark:hover:bg-gray-600 dark:hover:text-white dark:text-white"
        >
          역할
        </button>

        <button
          @click="moveToTeamInfo"
          type="button"
          :class="{ 'bg-samsung-blue text-white': currentTab === 'info' }"
          class="rounded-r-md border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 dark:bg-gray-800 dark:border-gray-700 dark:placeholder-gray-700 dark:hover:bg-gray-600 dark:hover:text-white dark:text-white"
        >
          팀 정보
        </button>
        <!-- <div>뒤로가기</div> -->
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
                      class="text-samsung-blue dark:text-blue-500 hover:underline cursor-pointer"
                      @click="showAuthorizationModal(role.roleId)"
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
          <BaseButton buttonText="변경" @click="checkTeamName" />
        </div>
        <div>
          <label for="input_text" class="block mb-2 text-sm text-gray-900 dark:text-gray-300">
            팀장(관리자)
          </label>
          <div
            id="teamLeader"
            class="text-gray-900 text-lg rounded-lg block w-full py-2.5 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
          >
            {{ teamLeader || '없음' }}
          </div>
        </div>

        <div class="inline-block align-baseline">
          <BaseButton buttonText="변경" @click="toggleHidden('changeTeamLeaderModal')" />
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
                  <tr
                    v-for="teamMember in teamMembers"
                    :key="teamMember.id"
                    class="hover:bg-gray-100 dark:hover:bg-gray-700"
                  >
                    <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                      {{ teamMember.memberNickname }}
                    </td>

                    <td
                      class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                    ></td>

                    <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                      {{ teamMember.role }}
                    </td>
                    <td
                      class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                    ></td>
                    <td class="py-4 px-6 text-sm text-center whitespace-nowrap dark:text-white">
                      <div
                        class="text-samsung-blue dark:text-blue-500 hover:underline cursor-pointer"
                        @click="showMemberRoleModal(teamMember.id)"
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
        <div class="flex justify-center pt-6">
          <BasePagination
            :current-page="currentPage"
            :total-pages="totalPages"
            @change-page="changePage"
          />
        </div>
        <div class="flex justify-between pt-6">
          <div class="flex">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="m11.25 11.25.041-.02a.75.75 0 0 1 1.063.852l-.708 2.836a.75.75 0 0 0 1.063.853l.041-.021M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9-3.75h.008v.008H12V8.25Z"
              />
            </svg>
            <div @click="updateKey">보안 강화하기</div>
          </div>
          <div class="text-red-500" @click="toggleHidden('deleteTeamModal')">팀삭제</div>
        </div>
      </div>
    </div>
  </div>
  <BaseModal modalId="deleteTeamModal">
    <div class="space-y-6 px-6 lg:px-8 sm:pb-6 xl:pb-8">
      <div class="text-red-500">삭제한 팀은 복구할 수 없습니다.</div>
      <div class="text-red-500">삭제하시겠습니까?</div>
    </div>
    <div class="flex justify-center pb-6">
      <BaseButton buttonText="삭제" @click="removeTeam" />
      <div class="disabled"></div>
    </div>
  </BaseModal>
  <MainAuthorizationModal :teamId="teamId" :roleId="selectedRoleId" @role-updated="refreshRoles" />
  <MainMemberRoleModal
    :teamId="teamId"
    :roles="roles"
    :memberId="selectedMemberId"
    @memberRole-updated="refreshMembers"
  />
  <ChangeTeamNameModal :teamId="teamId" :teamName="teamName" @teamName-updated="refreshTeam" />
  <ChangeTeamLeaderModal
    :teamId="teamId"
    :teamMembers="teamMembers"
    @teamLeader-updated="refreshLeader"
  />
  <TeamInvitationModal
    :teamId="teamId"
    :organizationId="organizationId"
    :roles="roles"
    @teamMember-invited="refreshMembers"
  />
  <TeamRoleCreationModal :teamId="teamId" @role-created="refreshRoles" />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import MainAuthorizationModal from '../common/MainAuthorizationModal.vue'
import MainMemberRoleModal from '../common/MainMemberRoleModal.vue'
import TeamInvitationModal from '../common/TeamInvitationModal.vue'
import TeamRoleCreationModal from '../common/TeamRoleCreationModal.vue'
import ChangeTeamNameModal from '../common/TeamChangeNameModal.vue'
import ChangeTeamLeaderModal from '../common/TeamChangeLeaderModal.vue'
import BaseModal from '../common/BaseModal.vue'
import BaseButton from '../common/BaseButton.vue'
import BasePagination from '../common/BasePagination.vue'
import { useCommonStore } from '@/stores/common'
import { getRole } from '@/api/role'
import { deleteTeam, getLeader, getTeamMembers, regenerateDataKey } from '@/api/team'
import router from '@/router'

const commonStore = useCommonStore()
const route = useRoute()
const organizationId = Number(route.query.organizationId)
const teamId = ref(0)
const teamName = ref('')
const teamLeader = ref('')
const { toggleHidden } = commonStore
const selectedRoleId = ref(null)
const selectedRoleName = ref('')
const selectedMemberId = ref(null)
const currentPage = ref(1)
const totalPages = ref(10)

const currentTab = ref('role')
const roles = ref([])
const teamMembers = ref([])

// onMounted 시 정보들 가져옴
onMounted(async () => {
  teamId.value = Number(route.query.teamId)
  watch(
    () => route.query.teamName,
    (newName) => {
      if (newName) teamName.value = newName
    },
    { immediate: true }
  )
  await fetchRole(teamId.value)

  const members = await fetchTeamMembers(teamId.value)
  teamMembers.value = members.teamMemberResponse
  const leader = await fetchLeader(teamId.value)

  teamLeader.value = leader.nickname
})

// 버튼 토글
const moveToTeamInfo = () => {
  currentTab.value = 'info'
}
const moveToTeamRole = () => {
  currentTab.value = 'role'
}

// 모달창 띄우기
const showAuthorizationModal = (roleId) => {
  const role = roles.value.find((r) => r.roleId === roleId)
  if (role) {
    selectedRoleId.value = roleId
    selectedRoleName.value = role.name
  }
  toggleHidden('teamRoleUpdateModal')
}

const showMemberRoleModal = (memberId) => {
  selectedMemberId.value = memberId
  toggleHidden('memberRole') // 모달 토글 함수, 이름 확인 필요
}

const checkTeamName = () => {
  if (teamName.value.toUpperCase() === 'MY TEAM') {
    alert('MY TEAM은 변경할 수 없습니다.')
  } else {
    toggleHidden('changeTeamNameModal')
  }
}

// 뒤로가기
const goBack = () => {
  router.go(-1)
}

// 역할 목록 가져오기
const fetchRole = async (teamId) => {
  try {
    const response = await getRole(teamId)

    roles.value = response.filter((role) => role.name !== 'HEADER')
    console.log('roles', roles.value)
  } catch (error) {
    console.error('Unexpected error:', error)
  }
}

// 새로고침
const refreshRoles = async () => {
  roles.value = await fetchRole(teamId.value)
}
const refreshMembers = async () => {
  teamMembers.value = await fetchTeamMembers(teamId.value)
}
const refreshTeam = async (newTeamName) => {
  teamName.value = newTeamName
  router.push({ query: { ...route.query, teamName: newTeamName } })
}
const refreshLeader = async () => {
  teamLeader.value = await fetchLeader(teamId.value)
}
// 키회전 요청
const updateKey = async () => {
  try {
    regenerateDataKey(teamId.value)
    window.alert('성공')
  } catch (error) {
    console.error(error.response.data.message)
  }
}
// 팀원 조회

const fetchTeamMembers = async (teamId) => {
  try {
    return await getTeamMembers(teamId, 1)
  } catch (error) {
    console.error('Unexpected error:', error)
  }
}

// 팀장 조회
const fetchLeader = async (teamId) => {
  try {
    return await getLeader(teamId)
  } catch (error) {
    return
  }
}

// 팀 삭제
const removeTeam = async (event) => {
  event.preventDefault()

  try {
    const body = {
      teamId: teamId.value
    }
    await deleteTeam(body)
    alert('삭제 성공')
    router.push({ name: 'mainpage' })
  } catch (error) {
    window.alert(error.response.data.message)
  }
}
</script>

<style scoped></style>
