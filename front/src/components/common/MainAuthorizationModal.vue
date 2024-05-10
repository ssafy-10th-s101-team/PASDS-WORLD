<template>
  <BaseModal modalId="teamRoleUpdateModal">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">역할 수정</h3>
      <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
        <div>
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >역할 이름</label
          >
          <input
            type="text"
            id="roleName"
            v-model="newRoleName"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder=""
            required
          />
        </div>
        <div class="px-6">
          <fieldset>
            <legend class="sr-only">Checkbox variants</legend>

            <div
              v-for="authority in authorities"
              :key="authority.id"
              class="flex items-center items-start mb-4"
            >
              <input
                :id="'checkbox-' + authority.id"
                :aria-describedby="'checkbox-' + authority.id"
                type="checkbox"
                class="bg-gray-50 border-gray-300 focus:ring-3 focus:ring-blue-300 h-4 w-4 rounded"
                v-model="selectedAuthorities"
                :value="authority.id"
                :checked="selectedAuthorities.includes(authority.id)"
              />
              <label
                :for="'checkbox-' + authority.id"
                class="text-sm ml-3 font-medium text-gray-900"
                >{{ authority.name }}</label
              >
            </div>
          </fieldset>
        </div>

        <button
          type="button"
          class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          @click="postRole"
        >
          수정
        </button>

        <div class="text-red-500 text-sm" @click="removeRole">
          <a href=""> 역할 삭제 </a>
        </div>
        <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
          Not registered?
          <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
        </div> -->
      </form>
    </div>
  </BaseModal>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import BaseModal from './BaseModal.vue'
import { getAuthority, updateRole, deleteRole, getRoleDetail } from '@/api/role'
import { useCommonStore } from '@/stores/common'
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const authorities = ref([])
const selectedAuthorities = ref([])
const newRoleName = ref('')

const roleAuths = ref([])

const props = defineProps({
  teamId: {
    type: Number,
    required: true
  },
  roleId: {
    type: Number,
    required: true
  }
})
onMounted(async () => {
  const auth = await fetchAuthority()
  authorities.value = auth
  if (props.roleId) {
    fetchRoleDetail() // Make sure role details are fetched when the component mounts if roleId is already set
  }
})

watch(
  () => props.roleId,
  async (newVal, oldVal) => {
    if (newVal !== oldVal && newVal) {
      fetchRoleDetail()
    }
  }
)
watch(
  roleAuths,
  (newRoleAuths) => {
    selectedAuthorities.value = newRoleAuths.map((auth) => auth.id)
  },
  { immediate: true }
)
const emit = defineEmits(['role-updated'])
// 권한 목록 가져오기
const fetchAuthority = async () => {
  try {
    const response = await getAuthority()
    const authorityMap = {
      1: '팀 비밀 생성',
      2: '팀 비밀 읽기',
      3: '팀 비밀 수정',
      4: '팀 비밀 삭제',
      5: '역할 생성',
      7: '역할 수정',
      8: '역할 삭제',
      9: '팀 수정',
      10: '팀 삭제',
      11: '팀 초대',
      12: '팀 추방',
      13: '비밀 접근 가능 역할 수정'
    }
    const authorities = response
      .filter((authority) => authority.id !== 10)
      .map((authority) => ({
        ...authority,
        id: Number(authority.id), // id를 숫자로 변환
        name: authorityMap[authority.id] || '알 수 없는 권한' // 권한 ID를 한글로 변환, 매핑되지 않은 항목은 '알 수 없는 권한'으로 표시
      }))
    console.log('Converted authorities:', authorities)
    return authorities
  } catch (error) {
    console.error('Unexpected error:', error)
  }
}

const fetchRoleDetail = async () => {
  try {
    const response = await getRoleDetail(props.roleId)

    newRoleName.value = response.name
    roleAuths.value = response.authorities

    console.log('이 역할의 권한들: ', roleAuths.value)
  } catch (error) {
    return
  }
}

const postRole = async (event) => {
  event.preventDefault()
  try {
    const body = {
      teamId: props.teamId,
      roleId: props.roleId,
      newRoleName: newRoleName.value,
      authorities: selectedAuthorities.value
    }
    await updateRole(body)

    emit('role-updated')
    toggleHidden('teamRoleUpdateModal')
    // 닫기
  } catch (error) {
    window.alert(error.response.data.message)
  }
}

const removeRole = async (event) => {
  event.preventDefault()
  console.log('teamId:', props.teamId)
  console.log('roleId:', props.roleId)
  try {
    const body = {
      teamId: props.teamId,
      roleId: props.roleId
    }
    await deleteRole(body)
    emit('role-updated')
    toggleHidden('teamRoleUpdateModal')
  } catch (error) {
    window.alert(error.response.data.message)
  }
}
</script>

<style scoped></style>
