<template>
  <BaseModal modalId="teamRoleCreationModal">
    <div class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl text-gray-900 dark:text-white">역할 추가</h3>
      <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
        <div>
          <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
            >역할 이름</label
          >
          <input
            type="text"
            id="roleName"
            v-model="roleName"
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
                checked=""
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
          type="submit"
          class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          @click="postRole"
        >
          생성
        </button>
        <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
          Not registered?
          <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
        </div> -->
      </form>
    </div>
  </BaseModal>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import BaseModal from './BaseModal.vue'
import { getAuthority, createRole } from '@/api/role'
const authorities = ref([])
const roleName = ref('')
const selectedAuthorities = ref([])

onMounted(async () => {
  const auth = await fetchAuthority()
  authorities.value = auth
})
const props = defineProps({
  teamId: {
    type: Number,
    required: true
  }
})

// 권한 목록 가져오기
const fetchAuthority = async () => {
  try {
    const handleSuccess = (response) => {
      return response.data
    }

    const handleFail = (error) => {
      console.error(error)
      const errmsg = error.response ? error.response.data.message : 'Error fetching data'
      console.error(errmsg)
      return []
    }
    return await getAuthority(handleSuccess, handleFail)
  } catch (error) {
    console.error('Unexpected error:', error)
    return []
  }
}

const postRole = async (event) => {
  event.preventDefault()
  try {
    const body = {
      teamId: props.teamId,
      roleName: roleName.value,
      authorities: selectedAuthorities.value
    }

    const handleSuccess = (response) => {
      return response.data
    }

    const handleFail = (error) => {
      console.error(error)
      const errmsg = error.response ? error.response.data.message : 'Error fetching data'
      console.error(errmsg)
      return []
    }
    return await createRole(body, handleSuccess, handleFail)
  } catch (error) {
    console.error('Unexpected error:', error)
    return []
  }
}

//
</script>

<style scoped></style>
