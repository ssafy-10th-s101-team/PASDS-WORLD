<template>
  <div>
    <BaseSearchBar />
    <div>
      <MainTeamButtonGroup
        @team-selected="handleTeamSelected"
        @loaded="handleLoaded"
        :selected-organization-id="selectedOrganizationId"
      />
      <MainTable v-if="isLoaded" :selectedTeamId="selectedTeamId" />
      <div class="flex justify-center">
        <BasePagination />
      </div>
    </div>
  </div>
</template>

<script setup>
import BaseSearchBar from '@/components/common/BaseSearchBar.vue'
import BasePagination from '@/components/common/BasePagination.vue'
import MainTable from '@/components/common/MainTable.vue'
import MainTeamButtonGroup from '@/components/common/MainTeamButtonGroup.vue'
import { defineProps, ref } from 'vue'

const isLoaded = ref(false)
const selectedTeamId = ref(null)
const props = defineProps({
  selectedOrganizationId: Number
})

function handleLoaded() {
  isLoaded.value = true
}

function handleTeamSelected(id) {
  selectedTeamId.value = id
}
</script>

<style scoped></style>
