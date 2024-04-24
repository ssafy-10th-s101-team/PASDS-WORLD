import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCommonStore = defineStore('common', () => {
  const toggleDropdown = (id) => {
    const dropdown = document.getElementById(id)
    if (dropdown) {
      dropdown.classList.toggle('hidden')
    }
  }

  return {
    toggleDropdown
  }
})
