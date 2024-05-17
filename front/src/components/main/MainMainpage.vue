<template>
  <div>
    <BaseSearchBar
      @organization-search-selected="handleSearchOrganizationSelected"
      @team-search-selected="handleSearchTeamSelected"
      @privateData-search-selected="handleSearchPrivateDataSelected"
    />
    <div>
      <MainTeamButtonGroup
        @team-selected="handleTeamSelected"
        @loaded="handleLoaded"
        @toggle-team-management="handleToggleTeamManagement"
        :selected-organization-id="selectedOrganizationId"
        :selected-search-organization-id="selectedSearchOrganizationId"
        :selected-search-team-id="selectedSearchTeamId"
      />
      <MainTable v-if="isLoaded && !isTeamManagementVisible" :selectedTeamId="selectedTeamId" />
      <MainTeamManagement
        v-if="isLoaded && isTeamManagementVisible"
        :selectedTeamId="selectedTeamId"
        :selectedTeamName="selectedTeamName"
        :selectedOrganizationId="selectedOrganizationId"
      />
    </div>
  </div>
</template>

<script setup>
import BaseSearchBar from '@/components/common/BaseSearchBar.vue'
import MainTable from '@/components/common/MainTable.vue'
import MainTeamButtonGroup from '@/components/common/MainTeamButtonGroup.vue'
import MainTeamManagement from './MainTeamManagement.vue'
import { defineProps, ref, defineEmits, watch } from 'vue'

const isLoaded = ref(false)
const selectedTeamId = ref(null)
const selectedTeamName = ref('')
const selectedOrganizationId = ref(null)
const selectedSearchOrganizationId = ref(-1)
const selectedSearchTeamId = ref(null)
const selectedSearchPrivateDataId = ref(null)
const isTeamManagementVisible = ref(false)

const props = defineProps({
  selectedOrganizationId: Number
})

const emit = defineEmits(['organization-search-selected'])

function handleLoaded() {
  isLoaded.value = true
}

function handleTeamSelected(id, name) {
  selectedTeamId.value = id
  selectedTeamName.value = name
}

function handleSearchOrganizationSelected(id) {
  selectedSearchOrganizationId.value = id
  emit('organization-search-selected', id)
}

const handleToggleTeamManagement = (isVisible) => {
  isTeamManagementVisible.value = isVisible
}

function handleSearchTeamSelected(id) {
  selectedSearchTeamId.value = id
}

function handleSearchPrivateDataSelected(id) {
  selectedSearchPrivateDataId.value = id
}

watch(
  () => props.selectedOrganizationId,
  (newVal) => {
    selectedOrganizationId.value = newVal
  },
  { immediate: true }
)
</script>

<style scoped></style>
