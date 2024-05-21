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
                    {{ member.organizationRole }}
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
                      v-if="member.organizationRole !== 'HEADER'"
                      class="text-samsung-blue dark:text-blue-500 hover:underline"
                      @click="openMemberModal(member)"
                    >
                      . . .
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
            <OrganizationMemberRoleModal
              @organizationRoleChanged="changeOrganizationRole"
              @organizationMemberRemoved="removeOrganizationRole"
              :selectedOrganizedId="selectedOrganizationId"
              :selectedMember="selectedMember"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="mt-5 flex justify-center">
      <BaseButton buttonText="초대하기 +" @click="toggleHidden('organizationInvitationModal')" />
    </div>
  </div>

  <OrganizationInvitationModal :selectedOrganizationId="selectedOrganizationId" />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import BaseButton from '@/components/common/BaseButton.vue'
import OrganizationInvitationModal from '@/components/common/OrganizationInvitationModal.vue'
import OrganizationMemberRoleModal from '@/components/organization/OrganizationMemberRoleModal.vue'
import { useCommonStore } from '@/stores/common'
import { getOrganizationMembers } from '@/api/organization'
const commonStore = useCommonStore()
const totalPages = ref(1)
const { toggleHidden } = commonStore
const props = defineProps({
  selectedOrganizationId: Number,
  selectedOrganizationName: String
})
// const members = ref([])
const members = ref([])
const selectedMember = ref({})
function changeOrganizationRole(data) {
  const { memberId, role } = data
  const member = members.value.find((m) => m.memberId === memberId)
  if (member) {
    member.organizationRole = role // 역할 업데이트, 역할이 `member` 객체 내에 있다고 가정
  }
}
function openMemberModal(member) {
  toggleHidden('organizationRole')
  selectedMember.value = member
}

function removeOrganizationRole(data) {
  const memberId = data.memberId

  // members 배열에서 memberId가 일치하는 멤버의 인덱스를 찾음
  const index = members.value.findIndex((m) => m.memberId === memberId)

  if (index !== -1) {
    // 인덱스가 존재하면 해당 위치의 멤버를 배열에서 제거
    members.value.splice(index, 1)
  } else {
    // 멤버를 찾지 못한 경우
    console.log('Member not found')
  }
}

watch(
  () => props.selectedOrganizationId,
  async (newVal, oldVal) => {
    if (newVal !== oldVal) {
      const response = await getOrganizationMembers(newVal, totalPages.value)
      members.value = response.organizationMemberResponse
    }
  }
)
onMounted(async () => {
  const response = await getOrganizationMembers(props.selectedOrganizationId, totalPages.value)
  members.value = response.organizationMemberResponse
})
</script>

<style scoped></style>
