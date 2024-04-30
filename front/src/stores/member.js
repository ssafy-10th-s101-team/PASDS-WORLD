import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useMemberStore = defineStore('member', () => {
  const tmp = ref('') // 'const'를 추가하여 tmp를 올바르게 선언

  return {
    tmp
  }
})
