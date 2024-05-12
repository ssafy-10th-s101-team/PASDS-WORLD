<template>
  <div class="flex">
    <div>
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
      />
    </div>
  </div>
</template>

<script setup>
import MainSidebar from '@/components/common/MainSidebar.vue'
import { ref } from 'vue'
const isLoaded = ref(false)
const selectedOrganizationId = ref(null)
const selectedSearchOrganizationId = ref(null)

function handleLoaded() {
  isLoaded.value = true
}

function handleOrganizationSelected(id) {
  selectedOrganizationId.value = id
}

function handleSearchOrganizationSelected(id) {
  selectedOrganizationId.value = id
  selectedSearchOrganizationId.value = id
}
</script>

<style scoped></style>
