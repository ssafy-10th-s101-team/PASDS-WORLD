<template>
  <div>
    <BaseSearchBar />
    <div class="max-w-full mx-auto">
      <DashboardTable />
    </div>
  </div>
</template>

<script setup>
import BaseSearchBar from '@/components/common/BaseSearchBar.vue'
import DashboardTable from '@/components/common/DashboardTable.vue'
</script>

<style scoped></style>
