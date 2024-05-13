<template>
  <div class="flex">
    <div>
      <OrganizationSidebar
        :selectedOrganizationId="selectedOrganizationId"
        @organization-selected="handleOrganizationSelected"
        @loaded="handleLoaded"
      />
    </div>
    <div v-if="isLoaded" class="flex-1">
      <RouterView
        :selectedOrganizationId="selectedOrganizationId"
        :selectedOrganizationName="selectedOrganizationName"
      />
    </div>
    <!--    <div v-else class="flex-1 flex items-center justify-center text-center">-->
    <!--      <b>관리권한을 가진 조직이 없습니다.</b>-->
    <!--    </div>-->
  </div>
</template>

<script setup>
import { ref } from 'vue'
import OrganizationSidebar from '@/components/common/OrganizationSidebar.vue'
const selectedOrganizationId = ref(-1)
const selectedOrganizationName = ref('')
const isLoaded = ref(false)
function handleOrganizationSelected(data) {
  selectedOrganizationId.value = data[0]
  selectedOrganizationName.value = data[1]
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
