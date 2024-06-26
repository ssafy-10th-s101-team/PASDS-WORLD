<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div
      class="space-y-6 bg-white shadow-md border border-gray-200 rounded-lg p-4 sm:p-6 lg:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">조직 설정</h3>
      <label for="organizationName" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
        >조직명</label
      >
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <!-- 조직명 입력 필드 -->
        <div
          class="text-black text-lg rounded-lg block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        >
          {{ organizationName }}
        </div>
        <div class="flex justify-center">
          <BaseButton buttonText="변경" @click="checkOrganizationName" />
        </div>
      </div>

      <!-- 조직장 변경 -->
      <label for="organizationName" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
        >조직장</label
      >
      <div class="grid gap-6 mb-6 lg:grid-cols-2">
        <div
          class="text-black text-lg rounded-lg block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        >
          {{ organizationHeader }}
        </div>
        <div class="flex justify-center">
          <BaseButton buttonText="변경" @click="toggleHidden('changeOrganizationHeaderModal')" />
        </div>
      </div>

      <div class="text-red-700 text-sm" @click="toggleHidden('deleteOrganizationModal')">
        조직삭제
      </div>
    </div>
  </div>
  <BaseModal modalId="deleteOrganizationModal">
    <div class="space-y-6 px-6 lg:px-8 sm:pb-6 xl:pb-8">
      <div class="text-red-500">삭제한 조직은 복구할 수 없습니다.</div>
      <div class="text-red-500">삭제하시겠습니까?</div>
    </div>
    <div class="flex justify-center pb-6">
      <BaseButton buttonText="삭제" @click="removeOrganization" />
      <div class="disabled"></div>
    </div>
  </BaseModal>
  <BaseAlert alertText="조직이 성공적으로 생성되었습니다." v-if="deleteOrganizationSuccessAlert" />
  <BaseFailAlert :alertText="errorMsg" v-if="deleteOrganizationFailAlert" />
  <OrganizationChangeHeaderModal
    :organizationId="props.selectedOrganizationId"
    :organizationMembers="organizationMembers"
    @organization-change-header="handleOrganizationChangeHeader"
  />
  <OrganizationChangeNameModal
    :organizationId="props.selectedOrganizationId"
    :oldOrganizationName="organizationName"
    @organizationName-updated="handleOrganizationChangeName"
  />
</template>

<script setup>
import { ref, onMounted, watch, defineEmits } from 'vue'
import BaseButton from '../common/BaseButton.vue'
import BaseAlert from '@/components/common/BaseAlert.vue'
import BaseFailAlert from '@/components/common/BaseFailAlert.vue'
import OrganizationChangeNameModal from '../common/OrganizationChangeNameModal.vue'
import OrganizationChangeHeaderModal from '../common/OrganizationChangeHeaderModal.vue'
import { deleteOrganization } from '@/api/organization'
import router from '@/router'
import BaseModal from '../common/BaseModal.vue'
import { useCommonStore } from '@/stores/common'
import { getOrganizations, getOrganizationMembers } from '@/api/organization'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const emit = defineEmits(['organizationName-updated', 'organization-deleted'])
const deleteOrganizationSuccessAlert = ref(false)
const deleteOrganizationFailAlert = ref(false)
const errorMsg = ref('')

const props = defineProps({
  selectedOrganizationId: {
    type: Number,
    required: true
  }
})
watch(
  () => props.selectedOrganizationId,
  async (newVal, oldVal) => {
    if (newVal !== oldVal) {
      await fetchOrganizationName()
      fetchOrganizationMembers()
    }
  }
)
onMounted(async () => {
  await fetchOrganizationName()
  fetchOrganizationMembers()
})
const organizationName = ref()
const organizationHeader = ref('')
const organizationMembers = ref('')

// my organization 확인
const checkOrganizationName = () => {
  if (organizationName.value === 'MY ORGANIZATION') {
    alert('기본 조직은 변경할 수 없습니다')
  } else toggleHidden('changeOrganizationNameModal')
}

// 조직 삭제
const removeOrganization = async (event) => {
  event.preventDefault()
  console.log('organizationId:', props.selectedOrganizationId)

  try {
    const body = {
      organizationId: props.selectedOrganizationId
    }
    await deleteOrganization(body)
    deleteOrganizationSuccessAlert.value = true
    setTimeout(() => {
      deleteOrganizationSuccessAlert.value = false
      window.location.reload()
    }, 2000)
  } catch (error) {
    errorMsg.value = '조직 삭제에 실패하였습니다.'
    deleteOrganizationFailAlert.value = true
    setTimeout(() => {
      deleteOrganizationFailAlert.value = false
    }, 2000)
  }
}
// 조직명을 가져오기 위한 나의 전체 조직 조회
const fetchOrganizationName = async () => {
  try {
    const response = await getOrganizations()
    const selectedOrganization = response.find(
      (org) => org.organizationId === props.selectedOrganizationId
    )
    if (selectedOrganization) {
      organizationName.value = selectedOrganization.name
    }
  } catch (error) {
    return
  }
}
// 조직원가져오기 및 조직장 이름
const fetchOrganizationMembers = async () => {
  try {
    const response = await getOrganizationMembers(props.selectedOrganizationId, 1)

    const selectedMember = response.organizationMemberResponse.find(
      (member) => member.organizationRole === 'HEADER'
    )
    organizationMembers.value = response.organizationMemberResponse
    console.log('멤버들', organizationMembers.value)
    if (selectedMember) {
      organizationHeader.value = selectedMember.name
    }
  } catch (error) {
    alert(error.response.data.message)
  }
}

function handleOrganizationChangeHeader() {
  fetchOrganizationMembers()
}

function handleOrganizationChangeName(newOrganizationName) {
  organizationName.value = newOrganizationName
  emit('organizationName-updated', newOrganizationName)
}
</script>

<style scoped></style>
