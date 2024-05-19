<template>
  <div class="flex">
    <div v-if="!isTeamManagement">
      <MainSidebar
        @organization-selected="handleOrganizationSelected"
        @loaded="handleLoaded"
        :selectedOrganizationId="selectedOrganizationId"
      />
    </div>
    <div class="flex-1">
      <RouterView
        v-if="isLoaded"
        :selectedOrganizationId="selectedOrganizationId"
        @organization-search-selected="handleSearchOrganizationSelected"
        :fromSearch="fromSearch"
      />
    </div>
  </div>
</template>

<script setup>
import MainSidebar from '@/components/common/MainSidebar.vue'
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isLoaded = ref(false)
const selectedOrganizationId = ref(null)
const selectedSearchOrganizationId = ref(null)
const fromSearch = ref(false)

const isTeamManagement = computed(() => route.name === 'teamManagement')

function handleLoaded() {
  isLoaded.value = true
}

function handleOrganizationSelected(id) {
  selectedOrganizationId.value = id
  fromSearch.value = false
}

function handleSearchOrganizationSelected(id) {
  selectedOrganizationId.value = id
  selectedSearchOrganizationId.value = id
  fromSearch.value = true
}
</script>

<style scoped></style>
