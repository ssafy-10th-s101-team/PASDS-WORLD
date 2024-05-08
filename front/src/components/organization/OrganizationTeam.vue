<template>
  <TeamTable :selectedOrganizationId="selectedOrganizationId" />
</template>

<script setup>
import TeamTable from '@/components/common/TeamTable.vue'
defineProps({
  selectedOrganizationId: String
})
</script>

<style scoped></style>
