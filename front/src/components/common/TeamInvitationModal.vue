<template>
  <BaseModal modalId="teamInvitationModal">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">팀 초대</h3>
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

                      <th scope="col" class="sr-only"></th>
                      <!-- 체크박스 -->
                      <th scope="col" class="p-4 w-1/6">
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

                      <td
                        class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                      ></td>
                      <!-- 체크박스 -->
                      <td class="p-4 w-4">
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

const selectedEmails = ref([])
const orgMembers = ref([])

onMounted(async () => {
  console.log('조직번호:', props.organizationId)
  const fetchMembers = await fetchOrganizationMembers(props.organizationId)
  orgMembers.value = fetchMembers
})

const props = defineProps({
  teamId: {
    type: Number,
    required: true
  },
  organizationId: {
    type: Number,
    required: true
  }
})

// 역할 목록 가져오기
const fetchOrganizationMembers = async (organizationId) => {
  try {
    const response = await getOrganizationMembers(organizationId, 0)
    console.log('멤버들이 잘 오나요', response)
    return response
  } catch (error) {
    console.error('Unexpected error:', error)
  }
}

const inviteMembers = async (event) => {
  event.preventDefault()
  for (const email of selectedEmails.value) {
    const body = {
      teamId: props.teamId,
      inviteMemberEmail: email
    }
    try {
      const response = await inviteTeam(body)
      return response
    } catch (error) {
      return
    }
  }
}
</script>

<style scoped></style>
