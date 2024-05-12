<template>
  <span class="timer text-green-500" v-if="message === null && emailRequest"> {{ min }} : {{ sec }} </span>
  <span class="msg text-red-500" v-if="message !== null && emailRequest"> {{ message }} </span>
  <span class="timer text-red-500" v-if="message === null && !emailRequest"> {{ min }} : {{ sec }} 이후 로그인이 가능합니다. </span>
</template>

<script setup>
import { useCommonStore } from '@/stores/common.js'
import { toRefs } from 'vue'
const commonStore = useCommonStore();
const { min, sec, message, emailRequest } = toRefs(commonStore);



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