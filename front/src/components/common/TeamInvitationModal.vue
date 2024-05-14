<template>
  <BaseModal modalId="teamInvitationModal" class="max-w-4xl">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">팀 초대</h3>
      <div class="w-full max-w-5xl mx-auto">
        <!-- Adjusted width of the container -->
        <div class="flex flex-col">
          <div class="shadow-md sm:rounded-lg w-full">
            <!-- Adjusted width -->
            <div class="inline-block min-w-full align-middle">
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
                        <div class="flex items-center">
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
                      v-for="orgMember in orgMembers"
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
import { ref, onMounted } from 'vue'
import BaseButton from './BaseButton.vue'
import BaseModal from './BaseModal.vue'
import { getOrganizationMembers } from '@/api/organization'
import { inviteTeam } from '@/api/team'
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore

const selectedEmails = ref([])
const orgMembers = ref([])
const guestRoleId = ref(0)

onMounted(async () => {
  const fetchMembers = await fetchOrganizationMembers(props.organizationId)
  orgMembers.value = fetchMembers
  const guestRole = await props.roles.find((role) => role.name === 'GUEST')
  if (guestRole) {
    guestRoleId.value = guestRole.roleId
  }
})

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
  }
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

const inviteMembers = async (event) => {
  event.preventDefault()
  for (const email of selectedEmails.value) {
    const body = {
      teamId: props.teamId,
      organizationId: props.organizationId,
      inviteMemberEmail: email,
      roleId: guestRoleId.value
    }
    try {
      await inviteTeam(body)
      toggleHidden('teamInvitationModal')
      alert('성공적으로 초대하였습니다.')
    } catch (error) {
      alert(error.response.data.message)
    }
  }
}
</script>

<style scoped></style>
