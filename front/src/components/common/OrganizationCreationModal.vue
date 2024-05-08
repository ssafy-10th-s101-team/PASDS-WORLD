<template>
  <BaseModal modalId="organizationCreationModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8">
      <h3 class="text-xl font-medium text-gray-900 dark:text-white">조직 추가</h3>
      <div>
        <label for="email" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
          >조직이름</label
        >
        <input
          type="text"
          id="organizationName"
          v-model="organizationName"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-samsung-blue focus:border-samsung-blue block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="20자 이내"
          required
        />
      </div>

      <button
        @click.prevent="createOrg"
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        생성
      </button>
    </form>
  </BaseModal>
</template>

<script setup>
import { ref } from 'vue'
import BaseModal from './BaseModal.vue'
import { createOrganization } from '@/api/organization'

const organizationName = ref('')

const createOrg = async () => {
  try {
    const body = { name: organizationName.value }
    await createOrganization(body)
    alert('조직이 성공적으로 생성되었습니다.')
    location.reload()
  } catch (error) {
    alert('조직 생성에 실패했습니다.')
    location.reload()
  }
}
</script>

<style scoped></style>
