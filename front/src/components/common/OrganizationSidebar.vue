<template>
  <!-- <div class="grid grid-cols-auto-1fr"> -->
  <div>
    <!-- This is an example component -->
    <div class="max-w-2xl mx-auto lg:ml-0">
      <aside class="w-64" aria-label="Sidebar">
        <div class="px-3 py-4 overflow-y-auto rounded bg-gray-50 dark:bg-gray-800">
          <ul class="space-y-2">
            <li>
              <div>
                <button
                  type="button"
                  class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700"
                  aria-controls="dropdown-example"
                  data-collapse-toggle="dropdown-example"
                  @click="toggleHidden('dropdownOrganization')"
                >
                  <span class="flex-1 ml-3 text-left whitespace-nowrap" sidebar-toggle-item
                    ><b>{{ currentOrganization.name }}</b></span
                  >
                  <svg
                    sidebar-toggle-item
                    class="w-6 h-6"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                      clip-rule="evenodd"
                    ></path>
                  </svg>
                </button>
                <!-- Dropdown menu -->
                <div
                  class="absolute hidden bg-white text-base z-50 list-none divide-y divide-gray-100 rounded shadow my-4"
                  id="dropdownOrganization"
                >
                  <!-- <div class="px-4 py-3">
                <span class="block text-sm">Bonnie Green</span>
                <span class="block text-sm font-medium text-gray-900 truncate"
                >name@flowbite.com</span
                >
              </div> -->
                  <ul class="py-1" aria-labelledby="dropdown">
                    <li v-for="organization in organizations" :key="organization.organizationId">
                      <a
                        @click="selectOrganization(organization)"
                        href="#"
                        class="text-sm hover:bg-samsung-blue hover:text-white text-gray-700 block px-4 py-2"
                        >{{ organization.name }}
                      </a>
                    </li>
                  </ul>
                </div>
              </div>

              <ul id="dropdown-example" class="py-2 space-y-2">
                <li>
                  <router-link
                    :to="{ name: 'organizationTeam' }"
                    @click="clickRouterList('organizationTeam')"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    :class="{ 'bg-samsung-blue text-white': curRouteName === 'organizationTeam' }"
                    >팀 목록</router-link
                  >
                </li>
                <li>
                  <router-link
                    :to="{ name: 'organizationMember' }"
                    @click="clickRouterList('organizationMember')"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    :class="{ 'bg-samsung-blue text-white': curRouteName === 'organizationMember' }"
                    >조직원 목록
                  </router-link>
                </li>
                <li v-if="currentOrganization.role == 'HEADER'">
                  <router-link
                    @click="clickRouterList('organizationSetting')"
                    :to="{ name: 'organizationSetting' }"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    :class="{
                      'bg-samsung-blue text-white': curRouteName === 'organizationSetting'
                    }"
                    >설정</router-link
                  >
                </li>
                <li>
                  <router-link
                    @click="clickRouterList('organizationDashboard')"
                    :to="{ name: 'organizationDashboard' }"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    :class="{
                      'bg-samsung-blue text-white': curRouteName === 'organizationDashboard'
                    }"
                    >대시보드
                  </router-link>
                </li>
              </ul>
            </li>

            <!-- <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-base text-gray-900 rounded-lg dark:text-white hover:bg-samsung-blue hover:text-white dark:hover:bg-gray-700"
                >
                  <svg
                    class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M5 3a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2V5a2 2 0 00-2-2H5zM5 11a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2v-2a2 2 0 00-2-2H5zM11 5a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V5zM11 13a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"
                    ></path>
                  </svg>
                  <span class="flex-1 ml-3 whitespace-nowrap">Kanban</span>
                  <span
                    class="inline-flex items-center justify-center px-2 ml-3 text-sm font-medium text-gray-800 bg-gray-200 rounded-full dark:bg-gray-700 dark:text-gray-300"
                    >Pro</span
                  >
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-base text-gray-900 rounded-lg dark:text-white hover:bg-samsung-blue hover:text-white dark:hover:bg-gray-700"
                >
                  <svg
                    class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M8.707 7.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l2-2a1 1 0 00-1.414-1.414L11 7.586V3a1 1 0 10-2 0v4.586l-.293-.293z"
                    ></path>
                    <path
                      d="M3 5a2 2 0 012-2h1a1 1 0 010 2H5v7h2l1 2h4l1-2h2V5h-1a1 1 0 110-2h1a2 2 0 012 2v10a2 2 0 01-2 2H5a2 2 0 01-2-2V5z"
                    ></path>
                  </svg>
                  <span class="flex-1 ml-3 whitespace-nowrap">Inbox</span>
                  <span
                    class="inline-flex items-center justify-center w-3 h-3 p-3 ml-3 text-sm font-medium text-blue-600 bg-blue-200 rounded-full dark:bg-blue-900 dark:text-blue-200"
                    >3</span
                  >
                </a>
              </li> -->

            <!-- 아이콘: 쇼핑백 -->
            <!-- <svg
                    class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900 dark:text-gray-400 dark:group-hover:text-white"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M10 2a4 4 0 00-4 4v1H5a1 1 0 00-.994.89l-1 9A1 1 0 004 18h12a1 1 0 00.994-1.11l-1-9A1 1 0 0015 7h-1V6a4 4 0 00-4-4zm2 5V6a2 2 0 10-4 0v1h4zm-6 3a1 1 0 112 0 1 1 0 01-2 0zm7-1a1 1 0 100 2 1 1 0 000-2z"
                      clip-rule="evenodd"
                    ></path>
                  </svg> -->
            <!-- 아이콘: 사람모양 -->
            <!-- <svg
                    class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                      clip-rule="evenodd"
                    ></path>
                  </svg> -->
          </ul>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue'
import { useRoute } from 'vue-router'
import { useCommonStore } from '@/stores/common'
import { getOrganizations } from '@/api/organization.js'
import router from '@/router'
const emit = defineEmits(['organization-selected', 'loaded'])
const route = useRoute()
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const organizations = ref([])
const currentOrganization = ref('s101')
const curRouteName = ref('')
const selectOrganization = async (organization) => {
  if (currentOrganization.value == organization) {
    toggleHidden('dropdownOrganization')
    return
  }

  currentOrganization.value = organization
  if (curRouteName.value === 'organizationSetting' && currentOrganization.value.role !== 'HEADER') {
    console.log('설정안보이니까 팀목록으로스르륵')
    router.push({ name: 'organizationTeam' })
    curRouteName.value = 'organizationTeam'
  }

  emit('organization-selected', [organization.organizationId, organization.name])

  toggleHidden('dropdownOrganization') // 드롭다운을 닫거나 열기
}

function clickRouterList(name) {
  curRouteName.value = name
  return route.name === name
}

onMounted(async () => {
  organizations.value = await getOrganizations()
  if (organizations.value == null || organizations.value.length == 0) {
    emit('organization-selected', null)
    emit('loaded', false)
  } else {
    emit('organization-selected', [
      organizations.value[0].organizationId,
      organizations.value[0].name
    ])
    emit('loaded', true)
    curRouteName.value = 'organizationTeam'
    currentOrganization.value = organizations.value[0]
  }
})
</script>

<style scoped></style>
