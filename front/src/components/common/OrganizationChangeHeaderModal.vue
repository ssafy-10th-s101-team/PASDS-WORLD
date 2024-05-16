<template>
  <BaseModal modalId="changeOrganizationHeaderModal">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">조직장 변경</h3>
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
                      v-for="organizationMember in organizationMembers"
                      :key="organizationMember.memberId"
                      class="hover:bg-gray-100 dark:hover:bg-gray-700"
                    >
                      <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                        {{ organizationMember.name }}
                      </td>
                      <td class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white">
                        {{ organizationMember.organizationRole }}
                      </td>

                      <td
                        class="py-4 px-6 text-sm text-gray-900 whitespace-nowrap dark:text-white"
                      ></td>
                      <!-- 라디오 -->
                      <td class="p-4 w-4 flex items-center">
                        <input
                          :id="`role-option-${organizationMember.memberId}`"
                          type="radio"
                          name="memberId"
                          :value="organizationMember.memberId"
                          class="h-4 w-4 border-gray-300 focus:ring-2 focus:ring-blue-300"
                          aria-labelledby="`role-option-${organizationMember.id}`"
                          aria-describedby="`role-option-${organizationMember.id}`"
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
      <BaseButton buttonText="변경" @click="updateHeader" />
    </div>
  </BaseModal>
  <BaseAlert
    alertText="조직장이 성공적으로 변경되었습니다."
    v-if="changeOrganizationHeaderSuccessAlert"
  />
  <BaseFailAlert :alertText="errorMsg" v-if="changeOrganizationHeaderFailAlert" />
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue'
import BaseButton from './BaseButton.vue'
import BaseModal from './BaseModal.vue'
import BaseAlert from '@/components/common/BaseAlert.vue'
import BaseFailAlert from '@/components/common/BaseFailAlert.vue'
import { assignHeader } from '@/api/organization'
import { useCommonStore } from '@/stores/common'

const selectedMemberId = ref(0)
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const changeOrganizationHeaderSuccessAlert = ref(false)
const changeOrganizationHeaderFailAlert = ref(false)
const errorMsg = ref('')

const emit = defineEmits(['organization-change-header'])
const props = defineProps({
  organizationId: {
    type: Number,
    required: true
  },
  organizationMembers: {
    type: Array,
    required: true
  }
})

const updateHeader = async () => {
  const body = {
    organizationId: props.organizationId,
    newHeaderId: selectedMemberId.value
  }
  try {
    const response = await assignHeader(body)
    changeOrganizationHeaderSuccessAlert.value = true
    setTimeout(() => {
      changeOrganizationHeaderSuccessAlert.value = false
    }, 3000)
    emit('organization-change-header', true)
    toggleHidden('changeOrganizationHeaderModal')
    return response
  } catch (error) {
    const errmsg = error.response ? error.response.data.message : 'Error fetching data'
    errorMsg.value = errmsg
    changeOrganizationHeaderFailAlert.value = true
    setTimeout(() => {
      changeOrganizationHeaderFailAlert.value = false
    }, 3000)
    toggleHidden('changeOrganizationHeaderModal')
    return
  }
}
</script>

<style scoped></style>
