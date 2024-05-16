<template>
  <BaseModal modalId="changeTeamLeaderModal">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">리더 변경</h3>
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
                        역할
                      </th>

                      <th scope="col" class="sr-only"></th>
                      <!-- 체크박스 -->
                      <th scope="col" class="">
                        <div class="flex items-center">
                          <label for="checkbox-all" class="sr-only"></label>
                        </div>
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
                      <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                        {{ teamMember.role }}
                      </td>

                      <td
                        class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                      ></td>
                      <!-- 라디오 -->
                      <td class="p-4 w-4 flex items-center">
                        <input
                          :id="`role-option-${teamMember.id}`"
                          type="radio"
                          name="memberId"
                          :value="teamMember.id"
                          class="h-4 w-4 border-gray-300 focus:ring-2 focus:ring-blue-300"
                          aria-labelledby="`role-option-${teamMember.id}`"
                          aria-describedby="`role-option-${teamMember.id}`"
                          v-model="selectedMemberId"
                        />
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
      <BaseButton buttonText="변경" @click="updateLeader" />
    </div>
  </BaseModal>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import BaseButton from './BaseButton.vue'
import BaseModal from './BaseModal.vue'
import { assignLeader } from '@/api/team'
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore

const selectedMemberId = ref('')
onMounted(() => {
  console.log('팀멤버', props.teamMembers)
})
const emit = defineEmits(['teamLeader-updated'])
const props = defineProps({
  teamId: {
    type: Number,
    required: true
  },
  teamMembers: {
    type: Array,
    required: true
  }
})

const updateLeader = async (event) => {
  event.preventDefault()
  const body = {
    teamId: props.teamId,
    newLeaderId: selectedMemberId.value
  }
  try {
    await assignLeader(body)
    alert('팀장이 변경되었습니다')
    emit('teamLeader-updated')
    toggleHidden('changeTeamLeaderModal')
  } catch (error) {
    return
  }
}
</script>

<style scoped></style>
