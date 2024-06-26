<template>
  <BaseModal modalId="teamInvitationModal">
    <div class="p-6">
      <h3 class="text-xl text-gray-900 dark:text-white">팀 초대</h3>
      <div class="overflow-auto">
        <!-- Adjusted width of the container -->
        <div class="flex flex-col">
          <div class="shadow-md sm:rounded-lg">
            <!-- Adjusted width -->
            <div class="inline-block min-w-full">
              <div class="overflow-hidden">
                <table class="min-w-full divide-y divide-gray-200 table-fixed dark:divide-gray-700">
                  <thead class="bg-gray-100 dark:bg-gray-700">
                    <tr>
                      <th
                        scope="col"
                        class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400"
                      >
                        닉네임
                      </th>
                      <th
                        scope="col"
                        class="py-3 px-6 text-xs tracking-wider text-left text-gray-700 uppercase dark:text-gray-400"
                      >
                        이메일
                      </th>
                      <th scope="col" class="sr-only"></th>
                      <!-- 체크박스 -->
                      <th scope="col" class="p-4">
                        <div class="flex items-center" style="display: none">
                          <input
                            id="checkbox-all"
                            type="checkbox"
                            class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                          />
                          <label for="checkbox-all" class="sr-only"></label>
                        </div>
                      </th>
                    </tr>
                  </thead>
                  <tbody
                    class="bg-white divide-y divide-gray-200 dark:bg-gray-800 dark:divide-gray-700"
                  >
                    <tr
                      v-for="orgMember in filteredOrgMembers"
                      :key="orgMember.email"
                      class="hover:bg-gray-100 dark:hover:bg-gray-700"
                    >
                      <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                        {{ orgMember.name }}
                      </td>
                      <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                        {{ orgMember.email }}
                      </td>
                      <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                        <!-- <select v-model="selectedRoleIds[index]" class="form-select">
                          <option v-for="role in roles" :key="role.id" :value="role.id">
                            {{ role.name }}
                          </option>
                        </select> -->
                      </td>
                      <!-- 체크박스 -->
                      <td class="p-4">
                        <div class="flex items-center">
                          <input
                            v-model="selectedEmails"
                            :value="orgMember.email"
                            type="checkbox"
                            class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                          />
                          <label for="checkbox-table-1" class="sr-only">checkbox</label>
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
    <div class="flex justify-center pb-6">
      <BaseButton buttonText="초대" @click="inviteMembers" />
    </div>
  </BaseModal>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import BaseButton from './BaseButton.vue'
import BaseModal from './BaseModal.vue'
import { getOrganizationMembers } from '@/api/organization'
import { inviteTeam } from '@/api/team'
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore

const selectedEmails = ref([])
const orgMembers = ref([])
const filteredOrgMembers = ref([])
const emit = defineEmits(['teamMember-invited'])
const props = defineProps({
  teamId: {
    type: Number,
    required: true
  },
  organizationId: {
    type: Number,
    required: true
  },
  roles: {
    type: Array,
    required: true
  },
  teamMembers: {
    type: Array,
    required: true
  }
})
onMounted(async () => {
  orgMembers.value = await fetchOrganizationMembers(props.organizationId)
  filteredOrgMembers.value = orgMembers.value.filter(
    (member) => !props.teamMembers.some((teamMember) => teamMember.id === member.memberId)
  )
})

// 역할 목록 가져오기
const fetchOrganizationMembers = async (organizationId) => {
  try {
    const response = await getOrganizationMembers(organizationId, 1)

    return response.organizationMemberResponse
  } catch (error) {
    console.error('Unexpected error:', error)
  }
}
watch(
  () => props.organizationId,
  async (newVal) => {
    orgMembers.value = await fetchOrganizationMembers(newVal)
    filteredOrgMembers.value = orgMembers.value.filter(
      (member) => !props.teamMembers.some((teamMember) => teamMember.id === member.memberId)
    )
  }
)

watch(
  () => props.teamMembers,
  async (newVal) => {
    filteredOrgMembers.value = orgMembers.value.filter(
      (member) => !newVal.some((teamMember) => teamMember.id === member.memberId)
    )
  }
)

const inviteMembers = async (event) => {
  event.preventDefault()
  const guestRoleArr = props.roles.filter((role) => role.name === 'GUEST')

  if (!guestRoleArr) {
    //base alert 넣기
  }
  const guestRoleId = guestRoleArr[0].roleId
  try {
    for (const email of selectedEmails.value) {
      const body = {
        teamId: props.teamId,
        organizationId: props.organizationId,
        inviteMemberEmail: email,
        roleId: guestRoleId
      }
      await inviteTeam(body)
    }
    toggleHidden('teamInvitationModal')
    emit('teamMember-invited', { status: true, alertText: '팀원을 초대했습니다.' })
  } catch (error) {
    console.log(error.response.data.message)
    emit('teamMember-invited', { status: true, alertText: '팀원을 초대에 실패했습니다.' })
  }
  selectedEmails.value = []
}
</script>

<style scoped>
table {
  width: 100%;
  table-layout: fixed;
}
</style>
