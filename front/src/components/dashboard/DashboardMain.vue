<template>
  <div>
    <BaseSearchBar />
    <DashboardTable />
    <div class="flex justify-center">
      <BasePagination />
    </div>
  </div>
</template>

<script setup>
import BaseSearchBar from '@/components/common/BaseSearchBar.vue'
import DashboardTable from '@/components/common/DashboardTable.vue'
import BasePagination from '@/components/common/BasePagination.vue'
</script>

<style scoped></style>
