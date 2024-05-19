<template>
  <BaseModal modalId="changeTeamNameModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">팀명 변경</h3>
      <div>
        <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >팀이름</label
        >
        <input
          type="text"
          id="newName"
          v-model="newTeamName"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="20자 이내"
          required
        />
      </div>

      <button
        @click="updateTeamName"
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        변경
      </button>
    </form>
  </BaseModal>
</template>

<script setup>
import { ref, defineProps, onMounted } from 'vue'
import BaseModal from './BaseModal.vue'
import { renameTeam } from '@/api/team'
import router from '@/router'
import { useRoute } from 'vue-router'

const route = useRoute()
const newTeamName = ref('')
const emit = defineEmits(['teamName-updated'])
const props = defineProps({
  teamId: {
    type: Number,
    required: true
  },
  teamName: {
    type: String,
    required: true
  }
})

onMounted(() => {
  newTeamName.value = props.teamName
  console.log('팀id ', props.teamId)
})

const updateTeamName = async (event) => {
  event.preventDefault()
  const body = {
    teamId: props.teamId,
    newName: newTeamName.value
  }
  try {
    const response = await renameTeam(body)
    if (response) {
      alert('팀명이 성공적으로 변경되었습니다.')
      emit('teamName-updated', newTeamName.value)
      router.push({ query: { ...route.query, teamName: newTeamName.value } })
    }
  } catch (error) {
    const errmsg = error.response ? error.response.data.message : 'Error fetching data'
    alert(errmsg)
  }
  location.reload()
}
</script>

<style scoped></style>
