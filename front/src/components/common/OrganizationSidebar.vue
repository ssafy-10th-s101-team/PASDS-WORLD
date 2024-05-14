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
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >팀 목록</router-link
                  >
                </li>
                <li>
                  <router-link
                    :to="{ name: 'organizationMember' }"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >조직원 목록
                  </router-link>
                </li>
                <li>
                  <router-link
                    :to="{ name: 'organizationSetting' }"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >설정</router-link
                  >
                </li>
                <li>
                  <router-link
                    :to="{ name: 'organizationDashboard' }"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    >대시보드
                  </router-link>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue'
import { useCommonStore } from '@/stores/common'
import { getOrganizations } from '@/api/organization.js'
const emit = defineEmits(['organization-selected', 'loaded'])
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const organizations = ref([])
const currentOrganization = ref('s101')

const selectOrganization = async (organization) => {
  if (currentOrganization.value == organization) {
    toggleHidden('dropdownOrganization')
    return
  }
  emit('organization-selected', [organization.organizationId, organization.name])

  currentOrganization.value = organization
  toggleHidden('dropdownOrganization') // 드롭다운을 닫거나 열기
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
    currentOrganization.value = organizations.value[0]
  }
})
</script>

<style scoped></style>
