<template>
  <div class="flex">
    <div>
      <OrganizationSidebar
        @organization-selected="handleOrganizationSelected"
        @loaded="handleLoaded"
      />
    </div>
    <div v-if="isLoaded" class="flex-1">
      <RouterView v-if="isLoaded" :selectedOrganizationId="selectedOrganizationId" />
    </div>
    <div v-else class="flex-1 flex items-center justify-center text-center">
      <b>관리권한을 가진 조직이 없습니다.</b>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import OrganizationSidebar from '@/components/common/OrganizationSidebar.vue'
const selectedOrganizationId = ref(-1)
const isLoaded = ref(true)
function handleOrganizationSelected(data) {
  console.log('선택된거 부모가 잘 확인했어요. data : ', data)
  selectedOrganizationId.value = data
}
function handleLoaded(data) {
  if (data) {
    isLoaded.value = true
  } else {
    isLoaded.value = false
  }
}
</script>

<style scoped></style>
