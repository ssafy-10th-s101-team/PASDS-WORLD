<template>
  <div class="grid grid-cols-auto-1fr">
    <!-- <div class="fixed z-50"> -->
    <!-- This is an example component -->
    <div class="max-w-2xl mx-auto lg:ml-0">
      <aside class="w-64" aria-label="Sidebar">
        <div class="px-3 py-4 overflow-y-auto rounded bg-gray-50 dark:bg-gray-800">
          <ul class="space-y-2">
            <li>
              <button
                type="button"
                class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700"
                aria-controls="dropdown-example"
                data-collapse-toggle="dropdown-example"
                @click="toggleHidden('dropdown-example')"
              >
                <span class="flex-1 ml-3 text-left whitespace-nowrap" sidebar-toggle-item
                  >모든 조직</span
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
              <ul id="dropdown-example" class="py-2 space-y-2">
                <li v-for="org in organizations" :key="org.organizationId">
                  <a
                    href="#"
                    :class="{
                      'bg-samsung-blue text-white': org.organizationId === selectedOrganizationId
                    }"
                    class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700 pl-11"
                    @click="selectOrganization(org.organizationId)"
                    >{{ org.name }}</a
                  >
                </li>
              </ul>
            </li>

            <li>
              <a
                href="#"
                class="flex items-center p-2 text-base text-gray-900 rounded-lg dark:text-white hover:bg-samsung-blue hover:text-white dark:hover:bg-gray-700"
                @click="toggleHidden('organizationCreationModal')"
              >
                <span class="flex-1 ml-3 whitespace-nowrap">조직 추가 +</span>
              </a>
              <OrganizationCreationModal @organization-created="handleOrganizationCreated" />
            </li>
          </ul>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { useCommonStore } from '@/stores/common'
import OrganizationCreationModal from './OrganizationCreationModal.vue'
import { onMounted, ref, defineEmits, defineProps, watch } from 'vue'
import { getOrganizations } from '@/api/organization'

const emit = defineEmits(['organization-selected', 'loaded'])
const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const organizations = ref([])
const selectedOrganizationId = ref(null)

const props = defineProps({
  selectedOrganizationId: Number
})

onMounted(async () => {
  const orgs = await fetchOrganization()
  organizations.value = orgs
  if (orgs.length > 0) {
    selectedOrganizationId.value = orgs[0].organizationId
    selectOrganization(selectedOrganizationId.value)
  }
})

watch(
  () => props.selectedOrganizationId,
  (newVal) => {
    selectedOrganizationId.value = newVal
  }
)

const fetchOrganization = async () => {
  return await getOrganizations()
}

const selectOrganization = (id) => {
  selectedOrganizationId.value = Number(id)
  emit('organization-selected', Number(id))
  emit('loaded', true)
}

async function handleOrganizationCreated() {
  const orgs = await fetchOrganization()
  organizations.value = orgs
  if (orgs.length > 0) {
    selectedOrganizationId.value = orgs[orgs.length - 1].organizationId
    selectOrganization(selectedOrganizationId.value)
  }
}
</script>

<style scoped></style>
