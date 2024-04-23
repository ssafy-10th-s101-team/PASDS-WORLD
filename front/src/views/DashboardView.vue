<template>
  <div class="flex">
    <div>
      <DashboardSidebar />
    </div>
    <div class="flex-1">
      <RouterView />
    </div>
  </div>
</template>

<script setup>
import DashboardSidebar from '@/components/common/DashboardSidebar.vue'
</script>

<style scoped></style>
