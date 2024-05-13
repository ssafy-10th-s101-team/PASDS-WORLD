<template>
  <div class="flex items-center justify-center py-8 px-4 bg-white">
    <div class="w-11/12 lg:w-2/3">
      <div class="flex flex-col justify-between h-full ">
        <div>
          <div class="lg:flex w-full justify-between">
            <h3 class="text-gray-600 dark:text-gray-400 leading-5 text-base md:text-xl">월별 조회수</h3>
            <div class="flex items-center justify-between lg:justify-start mt-2 md:mt-4 lg:mt-0">
              <div class="flex items-center">
                <button
                  class="py-2 px-4 bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 rounded text-white ease-in duration-150 text-xs hover:bg-indigo-600">
                  Tickets
                </button>
              </div>
              <div class="lg:ml-14">
                <div
                  class="bg-gray-100 dark:bg-gray-700 ease-in duration-150 hover:bg-gray-200 pb-2 pt-1 px-3 rounded-sm">
                  <select v-model="selectedYear" aria-label="select year"
                          class="text-xs text-gray-600 dark:text-gray-400 bg-transparent focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-800 rounded">
                    <option class="leading-1">Year</option>
                    <option v-for="year in yearList" :key="year" class="leading-1">{{ year }}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="flex items-end mt-6">
            <h3 class="text-indigo-500 leading-5 text-lg md:text-2xl">65,875</h3>
            <div class="flex items-center md:ml-4 ml-1 text-green-700">
              <p class="text-green-700 text-xs md:text-base">전월 대비 17% 증가</p>
              <svg role="img" class="text-green-700" aria-label="increase. upward arrow icon"
                   xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 12 12" fill="none">
                <path d="M6 2.5V9.5" stroke="currentColor" stroke-width="0.75" stroke-linecap="round"
                      stroke-linejoin="round"></path>
                <path d="M8 4.5L6 2.5" stroke="currentColor" stroke-width="0.75" stroke-linecap="round"
                      stroke-linejoin="round"></path>
                <path d="M4 4.5L6 2.5" stroke="currentColor" stroke-width="0.75" stroke-linecap="round"
                      stroke-linejoin="round"></path>
              </svg>
            </div>
          </div>
        </div>
        <div class="mt-6">
          <canvas id="myChart2" width="1025" height="400" role="img"
                  aria-label="line graph to show selling overview in terms of months and numbers"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import Chart from 'chart.js/auto'

// Props 정의
const props = defineProps({
  organizationViewList: {
    type: Array
  },
  organizationId: {
    type: Number
  },
  yearList: {
    type: Array
  }
})

const chartInstance = ref(null)
const graphData = ref([])
const selectedYear = ref(2024)
const month = ref([])

watch(
  [() => props.organizationId, () => selectedYear.value],
  () => {
    try {

      // 기존 차트 인스턴스가 있으면 파괴
      if (chartInstance.value) {
        chartInstance.value.destroy()
      }

      // 로그를 통해 전달된 props 확인
      console.log('전달된 organizationCountList:', props.organizationViewList)

      month.value = []
      graphData.value = []

      // 월
      props.organizationViewList.forEach((data) => {
        console.log(selectedYear.value)
        console.log(data)
        if (selectedYear.value == data[0] && !month.value.includes(data[1])) {
          month.value.push(data[1])
          graphData.value.push(data[2])
          console.log(month.value)
        }
      })


      const ctx = document.getElementById('myChart2').getContext('2d')
      chartInstance.value = new Chart(ctx, {
        type: 'line',
        data: {
          labels: month.value,
          datasets: [{
            label: '비밀 수',
            borderColor: '#4A5568',
            data: graphData.value,
            fill: false,
            pointBackgroundColor: '#4A5568',
            borderWidth: 3,
            pointBorderWidth: 4,
            pointHoverRadius: 6,
            pointHoverBorderWidth: 8,
            pointHoverBorderColor: 'rgb(74,85,104,0.2)'
          }]
        },
        options: {
          legend: {
            display: false
          },
          scales: {
            y: {
              display: false
            }
          }
        }
      })
    } catch (error) {
      console.error(error)
      // showErrorAlert(error.response.data.message)
    }
  }, { deep: true }
)


</script>

<style scoped>
/* Include any component-specific styles here */
</style>
