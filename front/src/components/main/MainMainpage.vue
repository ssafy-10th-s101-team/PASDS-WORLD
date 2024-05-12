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
        :selected-organization-id="selectedOrganizationId"
        :selected-search-organization-id="selectedSearchOrganizationId"
        :selected-search-team-id="selectedSearchTeamId"
      />
      <MainTable
        v-if="isLoaded"
        :selectedTeamId="selectedTeamId"
        :selectedSearchTeamId="selectedSearchTeamId"
      />
    </div>
  </div>
</template>

<script setup>
import BaseSearchBar from '@/components/common/BaseSearchBar.vue'
import MainTable from '@/components/common/MainTable.vue'
import MainTeamButtonGroup from '@/components/common/MainTeamButtonGroup.vue'
import { defineProps, ref, defineEmits } from 'vue'

const isLoaded = ref(false)
const selectedTeamId = ref(null)
const selectedSearchOrganizationId = ref(null)
const selectedSearchTeamId = ref(null)
const selectedSearchPrivateDataId = ref(null)

const props = defineProps({
  selectedOrganizationId: Number
})

const emit = defineEmits(['organization-search-selected'])

function handleLoaded() {
  isLoaded.value = true
}

function handleTeamSelected(id) {
  selectedTeamId.value = id
}

function handleSearchOrganizationSelected(id) {
  selectedSearchOrganizationId.value = id
  emit('organization-search-selected', id)
}

function handleSearchTeamSelected(id) {
  selectedSearchTeamId.value = id
}

function handleSearchPrivateDataSelected(id) {
  selectedSearchPrivateDataId.value = id
}
</script>

<style scoped></style>
