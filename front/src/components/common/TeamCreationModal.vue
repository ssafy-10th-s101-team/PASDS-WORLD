<template>
  <BaseModal modalId="teamCreationModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">팀 생성</h3>
      <div>
        <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >팀이름</label
        >
        <input
          type="text"
          id="teamName"
          v-model="teamName"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="20자 이내"
          required
        />
      </div>

      <button
        @click.prevent="createT"
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        생성
      </button>
    </form>
  </BaseModal>
  <BaseAlert alertText="팀이 성공적으로 생성되었습니다." v-if="teamSuccessAlert" />
  <BaseFailAlert :alertText="errorMsg" v-if="teamFailAlert" />
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import { useCommonStore } from '@/stores/common'
import BaseModal from './BaseModal.vue'
import BaseAlert from '@/components/common/BaseAlert.vue'
import BaseFailAlert from '@/components/common/BaseFailAlert.vue'
import { createTeam } from '@/api/team'

const emit = defineEmits(['team-created'])
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const props = defineProps({
  organizationId: Number
})
const teamName = ref('')
const teamSuccessAlert = ref(false)
const teamFailAlert = ref(false)
const errorMsg = ref('')

const createT = async () => {
  try {
    const body = {
      organizationId: props.organizationId,
      teamName: teamName.value
    }
    const response = await createTeam(body)
    teamSuccessAlert.value = true
    setTimeout(() => {
      teamSuccessAlert.value = false
    }, 3000)
    emit('team-created', true)
    toggleHidden('teamCreationModal')
  } catch (error) {
    const errmsg = error.response ? error.response.data.message : 'Error fetching data'
    errorMsg.value = errmsg
    teamFailAlert.value = true
    setTimeout(() => {
      teamFailAlert.value = false
    }, 3000)
    toggleHidden('teamCreationModal')
  }
}
</script>

<style scoped></style>
