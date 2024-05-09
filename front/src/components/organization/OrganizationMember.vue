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
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    닉네임
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    이메일
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400 w-2/6"
                  >
                    조직 내 역할
                  </th>
                  <th
                    scope="col"
                    class="py-3 px-6 text-xs tracking-wider text-center text-gray-700 uppercase dark:text-gray-400 w-1/6"
                  >
                    소속팀
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
                v-for="member in members"
                :key="member.memberId"
                class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
              >
                <tr class="hover:bg-gray-100 dark:hover:bg-gray-700">
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    {{ member.name }}
                  </td>
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    {{ member.email }}
                  </td>
                  <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                    Header
                  </td>
                  <td class="py-4 px-6 text-sm text-gray-500 whitespace-nowrap dark:text-white">
                    <div
                      v-for="team in member.teams"
                      :key="team.teamId"
                      class="flex justify-center"
                    >
                      <span
                        class="inline-flex items-center justify-center px-2 ml-3 text-sm font-medium text-white bg-samsung-blue rounded-full dark:bg-gray-700 dark:text-gray-300"
                        >{{ team.teamName }}</span
                      >
                    </div>
                  </td>
                  <td
                    class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                  ></td>

                  <td class="py-4 px-6 text-sm text-center whitespace-nowrap">
                    <div
                      class="text-samsung-blue dark:text-blue-500 hover:underline"
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
    <div class="mt-5 flex justify-center" @click="toggleHidden('organizationInvitationModal')">
      <BaseButton buttonText="초대하기 +" />
    </div>
    <MainMemberRoleModal />
  </div>

  <OrganizationInvitationModal />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import BaseButton from '@/components/common/BaseButton.vue'
import OrganizationInvitationModal from '@/components/common/OrganizationInvitationModal.vue'
import { useCommonStore } from '@/stores/common'
import { getOrganizationMembers } from '@/api/organization'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const props = defineProps({
  selectedOrganizationId: Number
})
// const members = ref([])
const members = ref([
  {
    memberId: 1,
    name: '제비랑',
    email: '1996joon@naver.com',
    teams: [
      {
        teamId: 1,
        teamName: '영업'
      }
    ]
  },
  {
    memberId: 2,
    name: '덕끼끼',
    email: 'deokki@naver.com',
    teams: [
      {
        teamId: 1,
        teamName: '마케팅'
      },
      {
        teamId: 2,
        teamName: '전체'
      }
    ]
  },
  {
    memberId: 3,
    name: '진뚱이용',
    email: 'wlsdyd4@naver.com',
    teams: [
      {
        teamName: '개발'
      },
      {
        teamName: '전체'
      }
    ]
  }
])
const showAuthorizationModal = (roleId) => {
  selectedRoleId.value = roleId
  toggleHidden('teamRoleUpdateModal')
}
onMounted(async () => {
  members.value = await getOrganizationMembers(props.selectedOrganizationId, 0)
})
</script>

<style scoped></style>
