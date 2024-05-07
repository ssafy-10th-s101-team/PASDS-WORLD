<template>
  <div class="flex">
    <div>
      <DashboardSidebar
        @organization-selected="handleOrganizationSelected"
        @loaded="handleLoaded"
      />
    </div>
    <div class="flex-1">
      <RouterView v-if="isLoaded" :selectedOrganizationId="selectedOrganizationId" />
    </div>
  </div>
</template>

<script setup>
import DashboardSidebar from '@/components/common/DashboardSidebar.vue'
import { ref } from 'vue'
const isLoaded = ref(false)
const selectedOrganizationId = ref(null)

function handleLoaded() {
  isLoaded.value = true
}

function handleOrganizationSelected(id) {
  selectedOrganizationId.value = id
}
</script>

<style scoped></style>
