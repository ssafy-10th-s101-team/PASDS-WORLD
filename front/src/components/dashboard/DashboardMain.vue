<template>
  <div>
    <BaseSearchBar />
    <div>
      <DashboardTeamButtonGroup />
      <DashboardTable />
      <div class="flex justify-center">
        <BasePagination />
      </div>
    </div>
  </div>
</template>

<script setup>
import BaseSearchBar from '@/components/common/BaseSearchBar.vue'
import BasePagination from '@/components/common/BasePagination.vue'
import DashboardTable from '@/components/common/DashboardTable.vue'
import DashboardTeamButtonGroup from '@/components/common/DashboardTeamButtonGroup.vue'
</script>

<style scoped></style>
