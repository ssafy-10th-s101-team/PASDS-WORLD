<template>
  <div class="max-w-2xl mx-auto flex justify-between">
    <!-- This is an example component -->
    <div class="max-w-lg">
      <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
        <button
          v-for="team in teamList"
          :key="team.teamId"
          type="button"
          :class="{
            'bg-samsung-blue text-white': team.teamId === selectedTeamId
          }"
          class="rounded-md border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-samsung-blue hover:text-white focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
          @click="selectTeam(team.id)"
        >
          {{ team.teamName }}
        </button>
        <button
          @click="toggleHidden('teamCreationModal')"
          type="button"
          class="rounded-r-md border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-samsung-blue hover:text-white focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
        >
          팀생성 +
        </button>
      </div>
    </div>
    <router-link :to="{ name: 'teamManagement', query: { teamId: selectedTeamId } }">
      <div class="mt-5">
        <BaseButton buttonText="팀 설정" />
      </div>
    </router-link>
  </div>
  <TeamCreationModal />
</template>

<script setup>
import TeamCreationModal from './TeamCreationModal.vue'
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import { localAxios } from '@/utils/http-commons'
import { onMounted, ref, defineProps, defineEmits } from 'vue'
import { getTeams } from '@/api/team'

const emit = defineEmits(['team-selected', 'loaded'])
const props = defineProps({
  selectedOrganizationId: Number
})
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const teamList = ref([])
const selectedTeamId = ref(null)

onMounted(async () => {
  if (props.selectedOrganizationId) {
    const response = await fetchTeams(props.selectedOrganizationId)
    teamList.value = response
    if (response.length > 0) {
      selectedTeamId.value = response[0].teamId
      selectTeam(selectedTeamId.value)
    }
  }
})

function selectTeam(id) {
  selectedTeamId.value = id
  emit('team-selected', id)
  emit('loaded', true)
}

const fetchTeams = async (orgId) => {
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
    return await getTeams(orgId, handleSuccess, handleFail)
  } catch (error) {
    console.error('Unexpected error:', error)
    return []
  }
}

// const fetchTeams = async function (orgId) {
//   try {
//     const response = await localAxios({
//       method: 'GET',
//       url: `/team/${orgId}`
//     })
//     return response.data
//   } catch (err) {
//     console.error(err)
//     const errmsg = err.response ? err.response.data.message : 'Error fetching data'
//     console.error(errmsg)
//     return []
//   }
// }
</script>

<style scoped></style>
