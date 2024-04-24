<script setup>
import { RouterView } from 'vue-router'
import HeaderNavigation from '@/components/common/HeaderNavigation.vue'
</script>

<template>
  <div id="app" class="basic-font">
    <HeaderNavigation />
    <RouterView />
  </div>
</template>

<style scoped>
.basic-font {
  font-family: 'SamsungOne500', sans-serif;
}
</style>
