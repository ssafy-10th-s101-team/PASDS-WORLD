<template>
  <span class="timer text-red-500" v-if="message === null && intervalId !== null"> {{ min }} : {{ sec }} </span>
</template>

<script setup>
import { useCommonStore } from '@/stores/common.js'
import { toRefs } from 'vue'
const commonStore = useCommonStore();
const { min, sec, message, intervalId } = toRefs(commonStore);



</script>

<style scoped>
.timer {
  flex-direction: row;
  margin-left: 10px;
  font-weight: 700;
}
.msg {
  flex-direction: row;
  margin-left: 10px;
  font-weight: 700;
}
</style>