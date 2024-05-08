<template>
  <span class="timer text-green-500" v-if="message === null"> {{ min }} : {{ sec }} </span>
  <span class="msg text-red-500" v-else> {{ message }} </span>
</template>

<script setup>
import { useCommonStore } from '@/stores/common.js'
import { toRefs } from 'vue'
const commonStore = useCommonStore();
const { min, sec, message } = toRefs(commonStore);



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