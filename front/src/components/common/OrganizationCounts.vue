<template>
  <div class="flex items-center justify-center py-4 px-4 bg-white">
    <div class="w-11/12 lg:w-3/4">
      <div class="flex flex-col justify-between h-full ">
        <div>
          <div class="lg:flex w-full justify-between">
            <h3 class="text-gray-600 dark:text-gray-400 leading-5 text-base md:text-xl">월별 비밀번호수</h3>
            <div class="flex items-center justify-between lg:justify-start mt-2 md:mt-4 lg:mt-0">
              <div class="lg:ml-10">
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
            <h3 v-if="selectedYear == 2024" class="text-indigo-500 leading-5 text-lg md:text-2xl">{{ graphData[graphData.length-1] }}개</h3>
            <div v-if="graphData.length > 1 && selectedYear == 2024" class="flex items-center md:ml-4 ml-1 text-green-700">
              <p class="text-green-700 text-xs md:text-base">전월 대비 {{ increasing }}% 증가</p>
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
          <canvas id="myChart" width="1025" height="400" role="img"
                  aria-label="line graph to show selling overview in terms of months and numbers"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import Chart from 'chart.js/auto'


// Props 정의
const props = defineProps({
  organizationCountList: {
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
const prefix = ref('증가')

const increasing = computed(() => {
  // graphData.value가 비어있지 않고, 최소 두 개의 항목이 있는지 확인
  if (selectedYear.value != 2024) return;

  if (graphData.value.length < 2) {
    return 0; // 또는 적절한 기본값
  }

  // 마지막 두 항목을 숫자로 변환
  const lastValue = parseFloat(graphData.value[graphData.value.length - 1]);
  const secondLastValue = parseFloat(graphData.value[graphData.value.length - 2]);

  // 증가율 계산
  const increaseRate = ((lastValue - secondLastValue) / secondLastValue) * 100;


  // 소수점 아래 첫째 자리까지만 반환 (반올림)
  return Math.round(increaseRate * 10) / 10;

});

const isLoaded = ref(false)

onMounted(() => {
  isLoaded.value = true
})

watch(
  [()=> props.organizationCountList, () => props.organizationId, () => selectedYear.value],
  () => {
    try {
      isLoaded.value = false;
      // 기존 차트 인스턴스가 있으면 파괴
      if (chartInstance.value) {
        chartInstance.value.destroy()
      }

      month.value = []
      graphData.value = []

      // 월
      props.organizationCountList.forEach((data) => {
        if (selectedYear.value == data[0] && !month.value.includes(data[1])) {
          month.value.push(data[1])
          graphData.value.push(data[2])
        }
      })

      const ctx = document.getElementById('myChart').getContext('2d')
      chartInstance.value = new Chart(ctx, {
        type: 'line',
        data: {
          labels: month.value,
          datasets: [{
            label: '비밀번호수',
            borderColor: '#6d79c4',
            backgroundColor: '#6d79c4',
            data: graphData.value,
            fill: false,
            pointBackgroundColor: '#6d79c4',
            borderWidth: 1,
            pointBorderWidth: 1,
            pointHoverRadius: 2,
            pointHoverBorderWidth: 2,
          }]
        },
        options: {
          legend: {
            display: false
          },
          scales: {
            y: {
              display: true
            }
          }
        }
        
      })

      setTimeout(() => {
        isLoaded.value = true;
    }, 1000)
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
