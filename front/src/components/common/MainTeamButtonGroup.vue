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
        <BaseButton v-if="selectedTeamId !== -1" buttonText="팀 설정" />
      </div>
    </router-link>
  </div>
  <TeamCreationModal
    :organizationId="props.selectedOrganizationId"
    @team-created="handleTeamCreated"
  />
</template>

<script setup>
import TeamCreationModal from './TeamCreationModal.vue'
import BaseButton from './BaseButton.vue'
import { useCommonStore } from '@/stores/common'
import { watch, ref, defineProps, defineEmits } from 'vue'
import { getTeams } from '@/api/team'

const emit = defineEmits(['team-selected', 'loaded', 'team-created'])
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

watch(
  () => props.selectedOrganizationId,
  async (newOrgId, oldOrgId) => {
    if (newOrgId !== oldOrgId && newOrgId) {
      const teams = await getTeams(newOrgId)
      teamList.value = teams
      if (props.selectedSearchOrganizationId && newOrgId === props.selectedSearchOrganizationId) {
        if (teams.length > 0 && props.selectedSearchTeamId) {
          selectTeam(props.selectedSearchTeamId)
        } else {
          emit('team-selected', -1)
        }
      } else {
        if (teams.length > 0) {
          selectTeam(teams[0].teamId)
        } else {
          selectedTeamId.value = -1
          emit('team-selected', -1)
        }
      }
    }
  },
  { immediate: true }
)

watch(
  () => props.selectedSearchTeamId,
  (newTeamId) => {
    if (newTeamId && teamList.value.some((team) => team.teamId === newTeamId)) {
      selectTeam(newTeamId)
    }
  }
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

async function handleTeamCreated() {
  const teams = await getTeams(props.selectedOrganizationId)
  teamList.value = teams
  if (props.selectedSearchOrganizationId) {
    if (teams.length > 0 && props.selectedSearchTeamId) {
      selectTeam(props.selectedSearchTeamId)
    } else {
      emit('team-selected', -1)
    }
  } else {
    if (teams.length > 0) {
      selectTeam(teams[0].teamId)
    } else {
      selectedTeamId.value = -1
      emit('team-selected', -1)
    }
  }
}
</script>

<style scoped></style>
