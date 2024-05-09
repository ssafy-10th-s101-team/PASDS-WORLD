<template>
  <div>
    <OrganizationMemberTable :selectedOrganizationId="selectedOrganizationId" />
  </div>
</template>

<script setup>
import OrganizationMemberTable from '../common/OrganizationMemberTable.vue'
defineProps({
  selectedOrganizationId: Number
})
</script>

<style scoped></style>
