<template>
  <div class="max-w-2xl mx-auto bg-white p-16">
    <div>
      {{ memberStore.tmp }}
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useMemberStore } from '../../stores/member'
import { useRoute, useRouter } from 'vue-router'
import { localAxios } from '../../utils/http-commons.js'

const memberStore = useMemberStore()
const router = useRouter()
</script>

<style scoped></style>
