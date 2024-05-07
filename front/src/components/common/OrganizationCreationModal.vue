<template>
  <BaseModal modalId="organizationCreationModal">
    <form class="space-y-6 px-6 lg:px-8 pb-4 sm:pb-6 xl:pb-8" action="#">
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
        @click="createOrganization"
        type="submit"
        class="w-full text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        생성
      </button>
      <!-- <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
        Not registered?
        <a href="#" class="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
      </div> -->
    </form>
  </BaseModal>
</template>

<script setup>
import { ref } from 'vue'
import BaseModal from './BaseModal.vue'
import { localAxios } from '@/utils/http-commons'
const organizationName = ref('')

const createOrganization = function () {
  localAxios({
    method: 'POST',
    url: `/organization/create`,
    data: {
      headers: { 'Access-Token': 'Bearer fjaskghsdkvvjkdalbdfklajghf123r' }
    }
  })
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
      const errmsg = err.response.data.message
      console.log(errmsg)
    })
}
</script>

<style scoped></style>
