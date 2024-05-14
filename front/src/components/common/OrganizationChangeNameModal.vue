<template>
  <BaseModal modalId="changeOrganizationNameModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">조직명 변경</h3>
      <div>
        <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >조직이름</label
        >
        <input
          type="text"
          id="newName"
          v-model="newOrganizationName"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="20자 이내"
          required
        />
      </div>

      <button
        @click="updateOrganizationName"
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        변경
      </button>
    </form>
  </BaseModal>
</template>

<script setup>
import { ref, defineProps, onMounted } from 'vue'
import BaseModal from './BaseModal.vue'
import router from '@/router'
import { useRoute } from 'vue-router'
import { renameOrganization } from '@/api/organization'

const route = useRoute()
const newOrganizationName = ref('')
const emit = defineEmits(['organizationName-updated'])
const props = defineProps({
  organizationId: {
    type: Number,
    required: true
  },
  oldOrganizationName: {
    type: String,
    required: true
  }
})

onMounted(() => {
  newOrganizationName.value = props.oldOrganizationName
})

const updateOrganizationName = async (event) => {
  event.preventDefault()
  const body = {
    organizationId: props.organizationId,
    newName: newOrganizationName.value
  }
  try {
    if (props.oldOrganizationName === 'MY ORGANIZATION') {
      alert('MY ORGANIZATION은 변경할 수 없습니다')
      return
    }
    const response = await renameOrganization(body)
    if (response) {
      alert('조직명이 성공적으로 변경되었습니다.')
      emit('teamName-updated', newOrganizationName.value)
      router.push({ query: { ...route.query, teamName: newOrganizationName.value } })
    }
  } catch (error) {
    const errmsg = error.response ? error.response.data.message : 'Error fetching data'
    alert(errmsg)
  }
  location.reload()
}
</script>

<style scoped></style>
