<template>
  <div class="max-w-2xl mx-auto p-4">
    <!-- Main modal -->
    <div
      id="organizationRole"
      aria-hidden="true"
      class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center rounded-lg"
    >
      <div
        class="relative w-full max-w-md px-4 py-5 h-auto bg-white rounded-lg shadow dark:bg-gray-700"
      >
        <!-- Modal content -->
        <div class="flex justify-end p-2">
          <button
            @click="toggleHidden('organizationRole')"
            type="button"
            class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white"
            data-modal-toggle="modal"
          >
            <!-- SVG omitted for brevity -->
          </button>
        </div>
        <div class="px-6 py-4">
          조직 내 {{ member.name }}'s role : {{ member.organizationRole }}
        </div>
        <div class="px-6 py-4">
          <fieldset class="mb-4">
            <legend class="sr-only">Roles</legend>
            <div class="flex items-center mb-3">
              <input
                id="role-option-1"
                type="radio"
                name="roles"
                value="ADMIN"
                class="h-4 w-4 text-samsung-blue border-gray-300 focus:ring-samsung-blue"
                aria-labelledby="role-option-1"
                aria-describedby="role-option-1"
                checked=""
                v-model="selectedRole"
              />
              <label for="role-option-1" class="ml-2 text-sm font-medium text-gray-900 block">
                Admin(관리자)
              </label>
            </div>
            <div class="flex items-center mb-3">
              <input
                id="role-option-2"
                type="radio"
                name="roles"
                value="MEMBER"
                class="h-4 w-4 text-samsung-blue border-gray-300 focus:ring-samsung-blue"
                aria-labelledby="role-option-2"
                aria-describedby="role-option-2"
                v-model="selectedRole"
              />
              <label for="role-option-2" class="ml-2 text-sm font-medium text-gray-900 block">
                Member(조직원)
              </label>
            </div>
          </fieldset>
        </div>
        <div @click="toggleHidden('organizationRole')" class="flex justify-center gap-2 pb-4">
          <button
            @click="clickChange()"
            class="bg-samsung-blue hover:bg-blue-700 text-white font-medium rounded-lg text-sm px-5 py-2 focus:outline-none"
          >
            변경
          </button>
          <button
            @click="clickBan()"
            class="bg-samsung-blue hover:bg-blue-700 text-white font-medium rounded-lg text-sm px-5 py-2 focus:outline-none"
          >
            추방
          </button>
          <button
            @click="clickCancel()"
            class="bg-samsung-blue hover:bg-blue-700 text-white font-medium rounded-lg text-sm px-5 py-2 focus:outline-none"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useCommonStore } from '@/stores/common'
import { updateOrganizationRole } from '@/api/organization.js'
const emit = defineEmits(['organiationRoleChanged'])
const selectedRole = ref('ADMIN') // 기본 선택 값 설정
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const props = defineProps({
  selectedOrganizedId: Number,
  member: Object
})

async function clickChange() {
  console.log('변경누름')
  try {
    await updateOrganizationRole({
      organizationMember: props.member.memberId,
      organizationId: props.selectedOrganizedId,
      newOrganizationRole: selectedRole.value
    })
    emit('organizationRoleChanged', { memberId: props.member.memberId, role: selectedRole.value })
  } catch (error) {
    alert('역할 변경 실패! 권한이없습니다.')
    console.log('역할 변경 실패', error)
  }
}

function clickBan() {
  console.log('확인누름')
}
function clickCancel() {
  console.log('취소누름')
}
</script>

<style scoped></style>
