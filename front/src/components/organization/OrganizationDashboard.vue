<template>
  <div class="mx-auto grid max-w-4xl grid-cols-10 gap-4 p-1">
    <div class="col-span-12 rounded-lg shadow-md bg-gray-200 p-0 sm:col-span-7 h-64 flex flex-col">
      <!-- Title Section with gray background -->
      <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
        <p class="text-black text-xl">{{ organizationName }} 비용</p>
      </div>
      <!-- Main Content Section with white background -->
      <div
        class="flex-grow h-4/6 bg-white rounded-b-lg p-0 border-t border-gray-200 grid grid-cols-2 grid-rows-2 gap-0"
      >
        <div class="p-0 pt-3 pl-4">
          <!-- 첫 번째 구역 -->
          <p class="text-sm">월간 비용 누계</p>
          <div class="text-2xl text-gray-800">230원</div>
        </div>
        <div class="p-0 pt-3 pl-4">
          <!-- 두 번째 구역 -->
          <p class="text-sm">이번 달 총 예상 비용</p>
          <div class="text-2xl text-gray-800">230원</div>
        </div>
        <div class="p-0 pt-3 pl-4">
          <!-- 세 번째 구역 -->
          <p class="text-sm">동일 기간 지난 달 비용</p>
          <div class="text-2xl text-gray-800">230원</div>
        </div>
        <div class="p-0 pt-3 pl-4">
          <!-- 네 번째 구역 -->
          <p class="text-sm">지난 달의 총 비용</p>
          <div class="text-2xl text-gray-800">230원</div>
        </div>
      </div>
      <div
        class="flex-grow h-1/6 bg-white rounded-b-lg p-0 border-t border-gray-200 flex items-center justify-center"
      >
        <button
          class="text-center text-gray-700 hover:text-blue-500 hover:underline"
        >결제내역 상세 보기
        </button>
      </div>
    </div>

    <div
      class="col-span-12 rounded-lg shadow-md bg-white p-0 sm:col-span-3 h-70 flex flex-col overflow-hidden"
    >
      <!-- Upper Section (1/6 height) -->
      <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
        <h1 class="text-black text-xl">결제수단 정보</h1>
      </div>
      <!-- Lower Section (5/6 height) -->
      <div class="relative w-full h-1/6 pt-2 pl-2 pr-2">
        <button type="button"
                class="w-full text-gray-900 bg-white hover:bg-gray-100 border border-gray-200 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2 text-center inline-flex items-center dark:focus:ring-gray-800 dark:bg-white dark:border-gray-700 dark:text-gray-900 dark:hover:bg-gray-200 mr-2">
          <svg class="mr-2 -ml-1 w-10 h-3" viewBox="0 0 660 203" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path
              d="M233.003 199.762L266.362 4.002H319.72L286.336 199.762H233.003V199.762ZM479.113 8.222C468.544 4.256 451.978 0 431.292 0C378.566 0 341.429 26.551 341.111 64.604C340.814 92.733 367.626 108.426 387.865 117.789C408.636 127.387 415.617 133.505 415.517 142.072C415.384 155.195 398.931 161.187 383.593 161.187C362.238 161.187 350.892 158.22 333.368 150.914L326.49 147.803L319.003 191.625C331.466 197.092 354.511 201.824 378.441 202.07C434.531 202.07 470.943 175.822 471.357 135.185C471.556 112.915 457.341 95.97 426.556 81.997C407.906 72.941 396.484 66.898 396.605 57.728C396.605 49.591 406.273 40.89 427.165 40.89C444.611 40.619 457.253 44.424 467.101 48.39L471.882 50.649L479.113 8.222V8.222ZM616.423 3.99899H575.193C562.421 3.99899 552.861 7.485 547.253 20.233L468.008 199.633H524.039C524.039 199.633 533.198 175.512 535.27 170.215C541.393 170.215 595.825 170.299 603.606 170.299C605.202 177.153 610.098 199.633 610.098 199.633H659.61L616.423 3.993V3.99899ZM551.006 130.409C555.42 119.13 572.266 75.685 572.266 75.685C571.952 76.206 576.647 64.351 579.34 57.001L582.946 73.879C582.946 73.879 593.163 120.608 595.299 130.406H551.006V130.409V130.409ZM187.706 3.99899L135.467 137.499L129.902 110.37C120.176 79.096 89.8774 45.213 56.0044 28.25L103.771 199.45L160.226 199.387L244.23 3.99699L187.706 3.996"
              fill="#0E4595"></path>
            <path
              d="M86.723 3.99219H0.682003L0 8.06519C66.939 24.2692 111.23 63.4282 129.62 110.485L110.911 20.5252C107.682 8.12918 98.314 4.42918 86.725 3.99718"
              fill="#F2AE14"></path>
          </svg>
          Pay with Visa
        </button>
      </div>
      <div class="relative w-full h-4/6 p-2">
        <img
          src="https://horizon-tailwind-react-git-tailwind-components-horizon-ui.vercel.app/static/media/Nft3.3b3e6a4b3ada7618de6c.png"
          class="mb-3 h-full w-full rounded-xl 3xl:h-full 3xl:w-full" alt="">
        <button
          class="absolute top-3 right-3 flex items-center justify-center rounded-full bg-white p-2 text-brand-500 hover:cursor-pointer">
          <div class="flex h-full w-full items-center justify-center rounded-full text-xl hover:bg-gray-50">
            <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em"
                 width="1em" xmlns="http://www.w3.org/2000/svg">
              <path fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"
                    d="M352.92 80C288 80 256 144 256 144s-32-64-96.92-64c-52.76 0-94.54 44.14-95.08 96.81-1.1 109.33 86.73 187.08 183 252.42a16 16 0 0018 0c96.26-65.34 184.09-143.09 183-252.42-.54-52.67-42.32-96.81-95.08-96.81z"></path>
            </svg>
          </div>
        </button>
      </div>
    </div>
    <div class="col-span-12 rounded-lg shadow-md bg-gray-200 p-0 sm:col-span-7 h-70 flex flex-col">
      <OrganizationCounts
        :organizationCountList="organizationCountList"
        :organizationId="organizationId"
        :yearList="years"
      />
    </div>
    <div
      class="col-span-12 rounded-lg shadow-md bg-white p-0 sm:col-span-3 h-70 flex flex-col overflow-hidden"
    >
      <!-- Upper Section (1/6 height) -->
      <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
        <h1 class="text-black text-xl">저장공간</h1>
      </div>
      <!-- Lower Section (5/6 height) -->
    </div>
    <div class="col-span-12 rounded-lg shadow-md bg-gray-200 p-0 sm:col-span-7 h-70 flex flex-col">
      <OrganizationViewCounts
        :organizationViewList="organizationViewList"
        :organizationId="organizationId"
        :yearList="years2"
        @top-view-count-loaded="handleTopCountTeams"
      />
    </div>
    <div
      class="col-span-12 rounded-lg shadow-md bg-white p-0 sm:col-span-3 h-70 flex flex-col overflow-hidden"
    >
      <!-- Upper Section (1/6 height) -->
      <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
        <h1 class="text-black text-xl">2024년 5월 상위 팀</h1>
      </div>
      <CircleChart :topCountTeams="topCountTeams" :organizationId="organizationId" :organizationViewList="organizationViewList" />
      <!-- Lower Section (5/6 height) -->

    </div>

    <div class="col-span-12 rounded-lg shadow-md p-0 sm:col-span-7 h-70 flex flex-col">
      <OrganizationKeyRotations
        :organizationRotateList="organizationRotateList"
        :organizationId="organizationId"
        :yearList="years3"
      />
    </div>

    <BaseAlert :alertText="ErrorMsg" v-if="ErrorAlert" />
  </div>
</template>

<script setup>
import { localAxios } from '@/utils/http-commons.js'
import { defineProps, onMounted, ref, watch } from 'vue'
import BaseAlert from '@/components/common/BaseAlert.vue'
import OrganizationCounts from '@/components/common/OrganizationCounts.vue'
import OrganizationViewCounts from '@/components/common/OrganizationViewCounts.vue'
import OrganizationKeyRotations from '@/components/common/OrganizationKeyRotations.vue'
import CircleChart from '@/components/dashboard/CircleChart.vue'

const organizationViewList = ref([])
const organizationRotateList = ref([])
const organizationCountList = ref([])

const organizationId = ref(1)
const organizationName = ref('')

const years = ref([])
const years2 = ref([])
const years3 = ref([])

const topCountTeams = ref([])

const props = defineProps({
  selectedOrganizationId: {
    type: Number,
    required: true
  },
  selectedOrganizationName: {
    type: String,
    required: true
  }
})

// ErrorAlert
const ErrorAlert = ref(false)

// ErrorMsg
const ErrorMsg = ref('')

const showErrorAlert = (message) => {
  ErrorMsg.value = message
  ErrorAlert.value = true
  setTimeout(() => {
    ErrorAlert.value = false
  }, 3000)
}

onMounted(async () => {
  organizationId.value = props.selectedOrganizationId
  organizationName.value = props.selectedOrganizationName

  // ${props.selectedOrganizationId}
  await localAxios
    .get(`/dashboard/1`)
    .then((response) => {
      const data = response.data
      organizationViewList.value = data.organizationViewList
      organizationRotateList.value = data.organizationRotateList
      organizationCountList.value = data.organizationCountList
      console.log(organizationCountList.value) // 비밀수
      console.log(organizationViewList.value) // 조회수
      console.log(organizationRotateList.value) // 키회전수

      // 연도 selectBox
      organizationCountList.value.forEach((data) => {
        if (!years.value.includes(data[0])) {
          years.value.push(data[0])
        }
      })


      // 연도 selectBox
      organizationViewList.value.forEach((data) => {
        if (!years2.value.includes(data[0])) {
          years2.value.push(data[0])
        }
      })


      // 연도 selectBox
      organizationRotateList.value.forEach((data) => {
        if (!years3.value.includes(data[0])) {
          years3.value.push(data[0])
        }
      })


    })
    .catch((error) => {
      console.error(error)
      showErrorAlert(error.response.data.message)
    })


})

watch(
  () => props.selectedOrganizationId,
  async (newOrganizationId) => {
    console.log('hello', newOrganizationId)

    organizationId.value = props.selectedOrganizationId
    organizationName.value = props.selectedOrganizationName


    if (!newOrganizationId) return // Optionally, skip when ID is null/undefined

    try {
      // const response = await localAxios.get(`/dashboard/${newOrganizationId}`)
      const response = await localAxios.get(`/dashboard/1`)
      const data = response.data
      organizationViewList.value = data.organizationViewList
      organizationRotateList.value = data.organizationRotateList
      organizationCountList.value = data.organizationCountList

      console.log(organizationCountList.value) // 비밀수
      console.log(organizationViewList.value) // 조회수
      console.log(organizationRotateList.value) // 키회전수


      // 연도 selectBox
      organizationCountList.value.forEach((data) => {
        if (!years.value.includes(data[0])) {
          years.value.push(data[0])
        }
      })


      // 연도 selectBox
      organizationViewList.value.forEach((data) => {
        if (!years2.value.includes(data[0])) {
          years2.value.push(data[0])
        }
      })


      // 연도 selectBox
      organizationRotateList.value.forEach((data) => {
        if (!years3.value.includes(data[0])) {
          years3.value.push(data[0])
        }
      })


    } catch (error) {
      console.error(error)
      if (error.response && error.response.data) {
        // showErrorAlert(error.response.data.message)
      }
    }
  }
)

const handleTopCountTeams = (data) => {
  console.log('왜 안오니' + data)
  topCountTeams.value = data
}
</script>

<style scoped></style>
