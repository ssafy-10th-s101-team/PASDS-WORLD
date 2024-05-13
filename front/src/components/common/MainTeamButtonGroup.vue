<template>
  <div class="max-w-2xl mx-auto flex justify-between">
    <!-- This is an example component -->
    <div class="max-w-lg">
      <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
        <button
          v-for="team in teamList"
          :key="team.teamId"
          ref="teamButtons"
          type="button"
          :class="{
            'bg-samsung-blue text-white': team.teamId === selectedTeamId
          }"
          class="rounded-md border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-samsung-blue hover:text-white focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
          @click="selectTeam(team.teamId)"
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
    <router-link
      :to="{
        name: 'teamManagement',
        query: {
          teamId: selectedTeamId,
          teamName: selectedTeamName,
          organizationId: props.selectedOrganizationId
        }
      }"
    >
      <div class="mt-5">
        <BaseButton buttonText="팀 설정" />
      </div>
    </router-link>
  </div>
  <TeamCreationModal :organizationId="props.selectedOrganizationId" />
</template>

<script setup>
import TeamCreationModal from './TeamCreationModal.vue'
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import { watch, ref, defineProps, defineEmits } from 'vue'
import { getTeams } from '@/api/team'

const emit = defineEmits(['team-selected', 'loaded'])
const props = defineProps({
  selectedOrganizationId: Number,
  selectedSearchOrganizationId: Number,
  selectedSearchTeamId: Number
})
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const teamList = ref([])
const selectedTeamId = ref(null)
const selectedTeamName = ref('')
const teamButtons = ref([])

const fetchTeams = async (orgId) => {
  if (orgId && orgId !== -1) {
    const teams = await getTeams(orgId)
    teamList.value = teams
    if (teams.length > 0) {
      if (props.selectedSearchOrganizationId == null) {
        selectedTeamId.value = teams[0].teamId
      } else {
        selectedTeamId.value = props.selectedSearchTeamId
      }
      selectTeam(selectedTeamId.value)
    } else {
      selectTeam(-1)
    }
  } else {
    teamList.value = []
  }
}

watch(
  [() => props.selectedOrganizationId, () => props.selectedSearchOrganizationId],
  async ([newOrgId, newSearchOrgId], [oldOrgId, oldSearchOrgId]) => {
    const currentOrgId = newOrgId || newSearchOrgId
    if (currentOrgId && (newOrgId !== oldOrgId || newSearchOrgId !== oldSearchOrgId)) {
      await fetchTeams(currentOrgId)
    }
  },
  { immediate: true }
)

watch(
  () => props.selectedSearchTeamId,
  (newVal) => {
    if (teamList.value.some((team) => team.teamId === newVal)) {
      selectTeam(newVal)
    }
  },
  { immediate: true }
)

function selectTeam(id) {
  selectedTeamId.value = id
  const team = teamList.value.find((t) => t.teamId === id)
  selectedTeamName.value = team ? team.teamName : ''
  emit('team-selected', id)
  emit('loaded', true)

  teamButtons.value.forEach((btn) => {
    if (btn) btn.blur()
  })
}
</script>

<style scoped></style>
