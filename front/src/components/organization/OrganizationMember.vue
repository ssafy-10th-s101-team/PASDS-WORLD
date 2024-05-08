<template>
  {{ selectedOrganizationId }}
  <div>
    <OrganizationMemberTable :selectedOrganizationId="selectedOrganizationId" />
  </div>
</template>

<script setup>
import OrganizationMemberTable from '../common/OrganizationMemberTable.vue'
defineProps({
  selectedOrganizationId: String
})
</script>

<style scoped></style>
