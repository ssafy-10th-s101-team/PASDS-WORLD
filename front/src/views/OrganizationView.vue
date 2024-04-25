<template>
  <div class="flex">
    <div>
      <OrganizationSidebar />
    </div>
    <div class="flex-1">
      <RouterView />
    </div>
  </div>
</template>

<script setup>
import OrganizationSidebar from '@/components/common/OrganizationSidebar.vue'
</script>

<style scoped></style>
