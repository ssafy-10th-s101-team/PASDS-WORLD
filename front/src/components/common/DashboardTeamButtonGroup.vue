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
    <router-link :to="{ name: 'teamManagement' }">
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
import { watch, ref, defineProps, defineEmits } from 'vue'
import { getTeams } from '@/api/team'

const emit = defineEmits(['team-selected', 'loaded'])
const props = defineProps({
  selectedOrganizationId: Number
})
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const teamList = ref([])
const selectedTeamId = ref(null)

const fetchTeams = async (orgId) => {
  return await getTeams(orgId)
}

watch(
  () => props.selectedOrganizationId,
  async (newVal) => {
    if (newVal) {
      const response = await fetchTeams(newVal)
      teamList.value = response
      if (response.length > 0) {
        selectedTeamId.value = response[0].teamId
        selectTeam(selectedTeamId.value)
      }
    }
  },
  { immediate: true }
)

function selectTeam(id) {
  selectedTeamId.value = id
  emit('team-selected', id)
  emit('loaded', true)
}
</script>

<style scoped></style>
