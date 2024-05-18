<template>
  <BaseModal modalId="organizationInvitationModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">조직 초대</h3>
      <div>
        <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >이메일</label
        >
        <input
          type="email"
          id="email"
          v-model="email"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="name@domain.com"
          required
        />
      </div>
      <div>
        <label for="role" class="block mb-2 text-sm text-gray-900 dark:text-gray-300">역할</label>
        <select
          id="role"
          v-model="role"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
        >
          <option value="MEMBER">MEMBER</option>
          <option value="ADMIN">ADMIN</option>
        </select>
      </div>
      <button
        type="button"
        @click="sendInvitation"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        초대
      </button>
      <!-- 추후 구현해야할 내용 
        1. v-if Member exists: 멤버 창을 띄워주기
    2. v-else: 초대 메일 보냈다는 alert
3. 모달 창 적당한 크기로 조정
-->
    </form>
  </BaseModal>
  <BaseAlert alertText="조직 초대가 정상적으로 발송되었습니다." v-if="invitationSuccessAlert" />
  <BaseFailAlert :alertText="errorMsg" v-if="invitationFailAlert" />
</template>

<script setup>
import { ref } from 'vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseAlert from '@/components/common/BaseAlert.vue'
import BaseFailAlert from '@/components/common/BaseFailAlert.vue'
import { inviteOrganization } from '@/api/organization.js'
import { useCommonStore } from '@/stores/common'
const props = defineProps({
  selectedOrganizationId: Number
})
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const email = ref('')
const role = ref('MEMBER')
const invitationSuccessAlert = ref(false)
const invitationFailAlert = ref(false)
const errorMsg = ref('')
async function sendInvitation() {
  try {
    await inviteOrganization({
      organizationId: props.selectedOrganizationId,
      email: email.value,
      organizationRole: role.value
    })

    invitationSuccessAlert.value = true
    setTimeout(() => {
      invitationSuccessAlert.value = false
    }, 3000)
  } catch (error) {
    errorMsg.value = error.response.data.message
    invitationFailAlert.value = true
    setTimeout(() => {
      invitationFailAlert.value = false
    }, 3000)
  }
  toggleHidden('organizationInvitationModal')
}

// }
</script>

<style scoped></style>
