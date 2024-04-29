import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCommonStore = defineStore('common', () => {
  const toggleHidden = (id) => {
    const obj = document.getElementById(id)
    if (obj) {
      obj.classList.toggle('hidden')
    }
  }
  const removeHidden = (id) => {
    const obj = document.getElementById(id)
    if (obj) {
      obj.classList.remove('hidden')
    }
  }

  return {
    toggleHidden,
    removeHidden
  }
})
