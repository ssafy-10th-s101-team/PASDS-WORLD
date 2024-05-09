<script setup>
import { RouterView } from 'vue-router'
import HeaderNavigation from '@/components/common/HeaderNavigation.vue'
</script>

<template>
  <div id="app" class="basic-font">
    <HeaderNavigation class="header-nav" />
    <RouterView />
  </div>
</template>

<style scoped>
.basic-font {
  font-family: 'SamsungOne500', sans-serif;
}

.header-nav {
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000; /* 높은 z-index 값으로 설정 */
}

</style>
