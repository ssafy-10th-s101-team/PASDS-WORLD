<template>
  <div class="relative w-full h-1/6 pl-2 pr-2 pb-4">
    <div class="mt-4 h-64">
      <canvas id="myChart4" width="400" height="400" role="img"
              aria-label="line graph to show selling overview in terms of months and numbers"></canvas>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import Chart from 'chart.js/auto'

// Props 정의
const props = defineProps({
  topCountTeams: {
    type: Array
  },
  organizationId: {
    type: Number
  },
  organizationViewList: {
    type: Array
  }
})

const chartInstance = ref(null)
const graphData = ref([])
const teams = ref([])


watch(
  [() => props.organizationViewList, () => props.organizationId, () => props.topCountTeams],
  () => {
    try {

      // 기존 차트 인스턴스가 있으면 파괴
      if (chartInstance.value) {
        chartInstance.value.destroy()
      }

      // 로그를 통해 전달된 props 확인
      console.log('전달된 topCountTeams:', props.topCountTeams)

      teams.value = []
      graphData.value = []

      // 월
      props.topCountTeams.forEach((team) => {
        teams.value.push(team.teamName)
        graphData.value.push(team.data)
      })


      const ctx = document.getElementById('myChart4').getContext('2d')
      chartInstance.value = new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: teams.value,
          datasets: [{
            backgroundColor: ['#6d79c4', '#E2E5F4', '#1428A0', '#3243AC', '#8A94D0', '#C5CAE8'],
            data: graphData.value,
            fill: false,
            pointBackgroundColor: ['#6d79c4', '#E2E5F4', '#1428A0', '#3243AC', '#8A94D0', '#C5CAE8'],
            borderWidth: 0,
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

</style>