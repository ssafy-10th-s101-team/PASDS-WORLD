<template>
  <BaseModal modalId="privateDataDetail">
    <div
      class="max-w-2xl mx-auto bg-white p-16 bg-white shadow-md rounded-lg p-4 sm:px-6 lg:px-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <h3 class="text-xl text-gray-900 dark:text-white">비밀 상세 정보</h3>
      <!-- 종류 변경 버튼 -->
      <!-- 버튼 -->
      <div class="max-w-2xl mx-auto flex justify-start justify-between pb-6">
        <div class="max-w-lg">
          <div class="inline-flex shadow-sm rounded-md mt-5" role="group">
            <button
              v-if="type === 'LOGIN'"
              ref="loginButton"
              type="button"
              class="rounded-l-lg border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
              :class="{ 'bg-samsung-blue text-white': type === 'LOGIN' }"
            >
              로그인
            </button>

            <button
              v-if="type === 'TEXT'"
              ref="textButton"
              type="button"
              class="rounded-r-md border border-gray-200 text-sm font-medium px-4 py-2 text-gray-900 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700"
              :class="{ 'bg-samsung-blue text-white': type === 'TEXT' }"
            >
              텍스트
            </button>
          </div>
        </div>
      </div>
      <!-- 버튼 끝 -->
      <form>
        <!-- 타입이 id, pw인 경우 -->
        <div v-if="type === 'LOGIN'">
          <!-- 이름 입력 필드 -->
          <div class="mb-6">
            <label for="title" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >이름</label
            >
            <input
              type="text"
              id="title"
              v-model="title"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder=""
              required
            />
          </div>
          <div class="grid gap-6 mb-6 lg:grid-cols-2">
            <!-- 아이디 입력 필드 -->
            <div>
              <label for="privateDataId" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
                >아이디</label
              >
              <input
                type="text"
                id="privateDataId"
                v-model="privateDataId"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                required
              />
            </div>

            <!-- 비밀번호 입력 필드 -->
            <div>
              <label for="content" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
                >비밀번호</label
              >
              <div class="relative">
                <input
                  id="content"
                  v-model="content"
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="•••••••••"
                  :type="showPassword ? 'text' : 'password'"
                  required
                />
                <button
                  @click="togglePasswordVisibility"
                  class="absolute inset-y-0 right-0 flex items-center px-2 text-gray-500 dark:text-gray-400"
                >
                  <svg
                    v-if="!showPassword"
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-5 w-5"
                    viewBox="0 0 20 20"
                    fill="currentColor"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M10 5a2.5 2.5 0 100 5 2.5 2.5 0 000-5zm0 8a4.5 4.5 0 100-9 4.5 4.5 0 000 9z"
                      clip-rule="evenodd"
                    />
                    <path
                      fill-rule="evenodd"
                      d="M10 1a9 9 0 100 18 9 9 0 000-18zm0 16a7 7 0 100-14 7 7 0 000 14z"
                      clip-rule="evenodd"
                    />
                  </svg>
                  <svg
                    v-else
                    class="h-5 w-5"
                    viewBox="0 0 24 24"
                    fill="currentColor"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                    <g
                      id="SVGRepo_tracerCarrier"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    ></g>
                    <g id="SVGRepo_iconCarrier">
                      <path
                        fill-rule="evenodd"
                        clip-rule="evenodd"
                        d="M22.2954 6.31083C22.6761 6.474 22.8524 6.91491 22.6893 7.29563L21.9999 7.00019C22.6893 7.29563 22.6894 7.29546 22.6893 7.29563L22.6886 7.29731L22.6875 7.2998L22.6843 7.30716L22.6736 7.33123C22.6646 7.35137 22.6518 7.37958 22.6352 7.41527C22.6019 7.48662 22.5533 7.58794 22.4888 7.71435C22.3599 7.967 22.1675 8.32087 21.9084 8.73666C21.4828 9.4197 20.8724 10.2778 20.0619 11.1304L21.0303 12.0987C21.3231 12.3916 21.3231 12.8665 21.0303 13.1594C20.7374 13.4523 20.2625 13.4523 19.9696 13.1594L18.969 12.1588C18.3093 12.7115 17.5528 13.2302 16.695 13.6564L17.6286 15.0912C17.8545 15.4383 17.7562 15.9029 17.409 16.1288C17.0618 16.3547 16.5972 16.2564 16.3713 15.9092L15.2821 14.2353C14.5028 14.4898 13.659 14.6628 12.7499 14.7248V16.5002C12.7499 16.9144 12.4141 17.2502 11.9999 17.2502C11.5857 17.2502 11.2499 16.9144 11.2499 16.5002V14.7248C10.3689 14.6647 9.54909 14.5004 8.78982 14.2586L7.71575 15.9093C7.48984 16.2565 7.02526 16.3548 6.67807 16.1289C6.33089 15.903 6.23257 15.4384 6.45847 15.0912L7.37089 13.689C6.5065 13.2668 5.74381 12.7504 5.07842 12.1984L4.11744 13.1594C3.82455 13.4523 3.34968 13.4523 3.05678 13.1594C2.76389 12.8665 2.76389 12.3917 3.05678 12.0988L3.98055 11.175C3.15599 10.3153 2.53525 9.44675 2.10277 8.75486C1.83984 8.33423 1.6446 7.97584 1.51388 7.71988C1.44848 7.59182 1.3991 7.48914 1.36537 7.41683C1.3485 7.38067 1.33553 7.35207 1.32641 7.33167L1.31562 7.30729L1.31238 7.29984L1.31129 7.29733L1.31088 7.29638C1.31081 7.2962 1.31056 7.29563 1.99992 7.00019L1.31088 7.29638C1.14772 6.91565 1.32376 6.474 1.70448 6.31083C2.08489 6.1478 2.52539 6.32374 2.68888 6.70381C2.68882 6.70368 2.68894 6.70394 2.68888 6.70381L2.68983 6.706L2.69591 6.71972C2.7018 6.73291 2.7114 6.7541 2.72472 6.78267C2.75139 6.83983 2.79296 6.92644 2.84976 7.03767C2.96345 7.26029 3.13762 7.58046 3.37472 7.95979C3.85033 8.72067 4.57157 9.70728 5.55561 10.6218C6.42151 11.4265 7.48259 12.1678 8.75165 12.656C9.70614 13.0232 10.7854 13.2502 11.9999 13.2502C13.2416 13.2502 14.342 13.013 15.3124 12.631C16.5738 12.1345 17.6277 11.3884 18.4866 10.5822C19.4562 9.67216 20.1668 8.69535 20.6354 7.9434C20.869 7.5685 21.0405 7.25246 21.1525 7.03286C21.2085 6.92315 21.2494 6.83776 21.2757 6.78144C21.2888 6.75328 21.2983 6.73242 21.3041 6.71943L21.31 6.70595L21.3106 6.70475C21.3105 6.70485 21.3106 6.70466 21.3106 6.70475M22.2954 6.31083C21.9147 6.14771 21.4738 6.32423 21.3106 6.70475L22.2954 6.31083ZM2.68888 6.70381C2.68882 6.70368 2.68894 6.70394 2.68888 6.70381V6.70381Z"
                      ></path>
                    </g>
                  </svg>
                </button>
              </div>
            </div>
          </div>
          <!-- URI 입력 필드 -->
          <div class="mb-6">
            <label for="url" class="block mb-2 text-sm text-gray-900 dark:text-gray-300">URL</label>
            <input
              type="text"
              id="url"
              v-model="url"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="www.domain.com/"
              required
            />
          </div>

          <!-- 메모 입력 필드 -->
          <div class="mb-6">
            <label for="memo" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >메모</label
            >
            <textarea
              id="memo"
              rows="5"
              v-model="memo"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="메모"
            ></textarea>
          </div>
          <!-- 역할 선택 드롭다운 -->
          <div class="mb-6">
            <button
              type="button"
              class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700"
              @click="toggleDropdown"
            >
              <span class="flex-1 ml-3 text-left whitespace-nowrap">접근 가능자 선택</span>
              <svg
                class="w-6 h-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 9l-7 7-7-7"
                ></path>
              </svg>
            </button>
            <div v-if="showDropdown" class="mt-2">
              <div v-for="role in roles" :key="role.roleId" class="flex items-center p-2">
                <input
                  type="checkbox"
                  :id="'role-' + role.roleId"
                  v-model="selectedRoles"
                  :value="role.roleId"
                />
                <label :for="'role-' + role.roleId" class="ml-2 text-sm text-gray-600">{{
                  role.name
                }}</label>
              </div>
            </div>
          </div>
        </div>
        <!-- 타입이 text인 경우 -->
        <div v-else-if="type === 'TEXT'">
          <!-- 이름 입력 필드 -->
          <div class="mb-6">
            <label for="title" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >이름</label
            >
            <input
              type="text"
              id="title"
              v-model="title"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder=""
              required
            />
          </div>
          <div class="mb-6">
            <!-- 비밀 입력 필드 -->
            <div>
              <label for="content" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
                >비밀</label
              >
              <div class="relative">
                <input
                  id="content"
                  v-model="content"
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  :type="showPassword ? 'text' : 'password'"
                  required
                />

                <button
                  @click="togglePasswordVisibility"
                  class="absolute inset-y-0 right-0 flex items-center px-2 text-gray-500 dark:text-gray-400"
                >
                  <svg
                    v-if="!showPassword"
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-5 w-5"
                    viewBox="0 0 20 20"
                    fill="currentColor"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M10 5a2.5 2.5 0 100 5 2.5 2.5 0 000-5zm0 8a4.5 4.5 0 100-9 4.5 4.5 0 000 9z"
                      clip-rule="evenodd"
                    />
                    <path
                      fill-rule="evenodd"
                      d="M10 1a9 9 0 100 18 9 9 0 000-18zm0 16a7 7 0 100-14 7 7 0 000 14z"
                      clip-rule="evenodd"
                    />
                  </svg>
                  <svg
                    v-else
                    class="h-5 w-5"
                    viewBox="0 0 24 24"
                    fill="currentColor"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                    <g
                      id="SVGRepo_tracerCarrier"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    ></g>
                    <g id="SVGRepo_iconCarrier">
                      <path
                        fill-rule="evenodd"
                        clip-rule="evenodd"
                        d="M22.2954 6.31083C22.6761 6.474 22.8524 6.91491 22.6893 7.29563L21.9999 7.00019C22.6893 7.29563 22.6894 7.29546 22.6893 7.29563L22.6886 7.29731L22.6875 7.2998L22.6843 7.30716L22.6736 7.33123C22.6646 7.35137 22.6518 7.37958 22.6352 7.41527C22.6019 7.48662 22.5533 7.58794 22.4888 7.71435C22.3599 7.967 22.1675 8.32087 21.9084 8.73666C21.4828 9.4197 20.8724 10.2778 20.0619 11.1304L21.0303 12.0987C21.3231 12.3916 21.3231 12.8665 21.0303 13.1594C20.7374 13.4523 20.2625 13.4523 19.9696 13.1594L18.969 12.1588C18.3093 12.7115 17.5528 13.2302 16.695 13.6564L17.6286 15.0912C17.8545 15.4383 17.7562 15.9029 17.409 16.1288C17.0618 16.3547 16.5972 16.2564 16.3713 15.9092L15.2821 14.2353C14.5028 14.4898 13.659 14.6628 12.7499 14.7248V16.5002C12.7499 16.9144 12.4141 17.2502 11.9999 17.2502C11.5857 17.2502 11.2499 16.9144 11.2499 16.5002V14.7248C10.3689 14.6647 9.54909 14.5004 8.78982 14.2586L7.71575 15.9093C7.48984 16.2565 7.02526 16.3548 6.67807 16.1289C6.33089 15.903 6.23257 15.4384 6.45847 15.0912L7.37089 13.689C6.5065 13.2668 5.74381 12.7504 5.07842 12.1984L4.11744 13.1594C3.82455 13.4523 3.34968 13.4523 3.05678 13.1594C2.76389 12.8665 2.76389 12.3917 3.05678 12.0988L3.98055 11.175C3.15599 10.3153 2.53525 9.44675 2.10277 8.75486C1.83984 8.33423 1.6446 7.97584 1.51388 7.71988C1.44848 7.59182 1.3991 7.48914 1.36537 7.41683C1.3485 7.38067 1.33553 7.35207 1.32641 7.33167L1.31562 7.30729L1.31238 7.29984L1.31129 7.29733L1.31088 7.29638C1.31081 7.2962 1.31056 7.29563 1.99992 7.00019L1.31088 7.29638C1.14772 6.91565 1.32376 6.474 1.70448 6.31083C2.08489 6.1478 2.52539 6.32374 2.68888 6.70381C2.68882 6.70368 2.68894 6.70394 2.68888 6.70381L2.68983 6.706L2.69591 6.71972C2.7018 6.73291 2.7114 6.7541 2.72472 6.78267C2.75139 6.83983 2.79296 6.92644 2.84976 7.03767C2.96345 7.26029 3.13762 7.58046 3.37472 7.95979C3.85033 8.72067 4.57157 9.70728 5.55561 10.6218C6.42151 11.4265 7.48259 12.1678 8.75165 12.656C9.70614 13.0232 10.7854 13.2502 11.9999 13.2502C13.2416 13.2502 14.342 13.013 15.3124 12.631C16.5738 12.1345 17.6277 11.3884 18.4866 10.5822C19.4562 9.67216 20.1668 8.69535 20.6354 7.9434C20.869 7.5685 21.0405 7.25246 21.1525 7.03286C21.2085 6.92315 21.2494 6.83776 21.2757 6.78144C21.2888 6.75328 21.2983 6.73242 21.3041 6.71943L21.31 6.70595L21.3106 6.70475C21.3105 6.70485 21.3106 6.70466 21.3106 6.70475M22.2954 6.31083C21.9147 6.14771 21.4738 6.32423 21.3106 6.70475L22.2954 6.31083ZM2.68888 6.70381C2.68882 6.70368 2.68894 6.70394 2.68888 6.70381V6.70381Z"
                      ></path>
                    </g>
                  </svg>
                </button>
              </div>
            </div>
          </div>

          <!-- 메모 입력 필드 -->
          <div class="mb-6">
            <label for="memo" class="block mb-2 text-sm text-gray-900 dark:text-gray-300"
              >메모</label
            >
            <textarea
              id="memo"
              v-model="memo"
              rows="5"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="메모"
            ></textarea>
          </div>
          <!-- 역할 선택 드롭다운 -->
          <div class="mb-6">
            <button
              type="button"
              class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-samsung-blue hover:text-white dark:text-white dark:hover:bg-gray-700"
              @click="toggleDropdown"
            >
              <span class="flex-1 ml-3 text-left whitespace-nowrap">접근 가능자 선택</span>
              <svg
                class="w-6 h-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 9l-7 7-7-7"
                ></path>
              </svg>
            </button>
            <div v-if="showDropdown" class="mt-2">
              <div v-for="role in roles" :key="role.roleId" class="flex items-center p-2">
                <input
                  type="checkbox"
                  :id="'role-' + role.roleId"
                  v-model="selectedRoles"
                  :value="role.roleId"
                />
                <label :for="'role-' + role.roleId" class="ml-2 text-sm text-gray-600">{{
                  role.name
                }}</label>
              </div>
            </div>
          </div>
        </div>
        <div class="flex justify-center">
          <button
            @click="updatePrivate"
            type="submit"
            class="text-white bg-samsung-blue hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            수정
          </button>
        </div>
      </form>
    </div>
  </BaseModal>
</template>

<script setup>
import { ref, defineProps, watch } from 'vue'
import BaseModal from './BaseModal.vue'
import { useCommonStore } from '@/stores/common'
import { getPrivateData, updatePrivateData } from '@/api/data'
import { getRole } from '@/api/role'

const commonStore = useCommonStore()
const { toggleHidden } = commonStore
const showPassword = ref(false)
const type = ref('LOGIN')
const title = ref('')
const privateDataId = ref(null)
const content = ref('')
const url = ref('')
const memo = ref('')
const roles = ref([])
const selectedRoles = ref([])
const showDropdown = ref(false)

const props = defineProps({
  teamId: Number,
  privateDataId: Number
})

const togglePasswordVisibility = (event) => {
  event.preventDefault()
  showPassword.value = !showPassword.value
}

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value
}

const fetchPrivateData = async () => {
  if (props.privateDataId) {
    const roleResponse = await getRole(props.teamId)
    roles.value = roleResponse.filter((role) => role.name !== 'HEADER' && role.name !== 'LEADER')

    const dataResponse = await getPrivateData(props.teamId, props.privateDataId)
    if (dataResponse) {
      title.value = dataResponse.title
      type.value = dataResponse.type
      privateDataId.value = dataResponse.privateDataId
      content.value = dataResponse.privateData
      url.value = dataResponse.url
      memo.value = dataResponse.memo

      selectedRoles.value = dataResponse.roles
        .filter((role) => role.roleId !== 1 && role.roleId !== 2)
        .map((role) => role.roleId)
      showPassword.value = false
    }
  }
}

watch(
  () => props.privateDataId,
  (newVal, oldVal) => {
    if (newVal !== oldVal && newVal) {
      fetchPrivateData()
    }
  }
)

const updatePrivate = async (event) => {
  event.preventDefault()
  if (!validateFields()) {
    alert('모든 항목을 채워주세요.')
    return
  }
  try {
    const body = {
      teamId: props.teamId,
      privateDataId: props.privateDataId,
      title: title.value,
      content: content.value,
      memo: memo.value,
      id: privateDataId.value,
      url: url.value,
      roleId: selectedRoles.value
    }
    const response = await updatePrivateData(body)
    if (response) {
      alert('비밀이 성공적으로 생성되었습니다.')
    }
    toggleHidden('privateDataDetail')
  } catch (error) {
    const errmsg = error.response ? error.response.data.message : 'Error fetching data'
    alert(errmsg)
  }
  location.reload()
}

function validateFields() {
  return title.value.trim() && content.value.trim()
}
</script>

<style scoped></style>
