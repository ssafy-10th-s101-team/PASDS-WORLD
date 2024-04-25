<template>
  <div class="relative" ref="scrollContainer" @scroll="handleScroll">
    <!-- page1 -->
    <div ref="page1" class="h-screen flex flex-col items-center justify-center bg-white">
      <div>
        <h2 class="text-2xl font-samsungone700c text-samsung-blue">First Section</h2>
        <p class="font-samsungone700c text-lg text-black">안녕안녕</p>
      </div>
    </div>
    <!-- page1 end -->
    <!-- page2 -->
    <div ref="page2" class="h-screen flex flex-col items-center justify-center bg-white text-white">
      <div>
        <h2 class="text-2xl font-samsungone700c text-samsung-blue">Second Section</h2>
        <p class="font-samsungone700c text-lg text-black">나는 지수야</p>
      </div>
    </div>
    <!-- page2 end -->
    <!-- page3 -->
    <div ref="page3" class="h-screen flex flex-col items-center justify-center bg-white text-white">
      <div>
        <h2 class="text-2xl font-samsungone700c text-samsung-blue">Third Section</h2>
        <p class="font-samsungone700c text-lg text-black">헬륨가스 먹었더니 요렇게 됐지</p>
      </div>
    </div>
    <!-- page3 end -->
    <!-- page4 -->
    <div ref="page4" class="h-screen flex flex-col items-center justify-center bg-white text-white">
      <img src="../assets/images/QR_example.png" alt="QR" />
      <h2 class="font-samsungone700c text-black text-lg">어플리케이션 다운로드</h2>
    </div>
    <!-- page4 end -->
  </div>
</template>

<script setup>
import { ref } from 'vue'

const scrollContainer = ref(null)
const page1 = ref(null)
const page2 = ref(null)
const page3 = ref(null)
const page4 = ref(null)

const handleScroll = () => {
  const scrollTop = scrollContainer.value.scrollTop
  if (scrollTop < page2.value.offsetTop) {
    scrollTo(page2.value.offsetTop)
  } else if (scrollTop < page3.value.offsetTop) {
    scrollTo(page3.value.offsetTop)
  } else if (scrollTop < page4.value.offsetTop) {
    scrollTo(page4.value.offsetTop)
  }
}

const scrollTo = (offset) => {
  scrollContainer.value.scrollTo({
    top: offset,
    behavior: 'smooth'
  })
}
</script>

<style scoped>
/* Add your styles here */
</style>
