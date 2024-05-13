<template>
  <div class="mt-6">
    <canvas id="myChart" width="1025" height="400" role="img"
            aria-label="line graph to show selling overview in terms of months and numbers"></canvas>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'

// Props 정의
const props = defineProps({
  organizationCountList: {
    type: Array
  },
  organizationId: {
    type: Number
  }
})

const chartInstance = ref(null)
const graphData = ref([])
const selectedYear = ref(2024)
const month = ref([])
const isLoaded = ref(false)

const graphType = ref('pie')


onMounted(() => {
  isLoaded.value = true
})

watch(
  [() => props.organizationId, () => selectedYear.value, () => isLoaded, () => graphType.value],
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
        type: graphType.value,
        data: {
          labels: month.value,
          datasets: [{
            label: '조회수',
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
              display: true
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

</style>