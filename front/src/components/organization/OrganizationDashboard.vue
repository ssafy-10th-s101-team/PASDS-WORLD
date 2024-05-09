<template>
    <div class="mx-auto grid max-w-4xl grid-cols-10 gap-4 p-1">
      
      <div class="col-span-12 rounded-lg shadow-md bg-gray-200 p-0 sm:col-span-7 h-64 flex flex-col">
        <!-- Title Section with gray background -->
        <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
          <p class="text-black text-xl">비용 요약</p>
        </div>
        <!-- Main Content Section with white background -->
        <div class="flex-grow h-4/6 bg-white rounded-b-lg p-0 border-t border-gray-200 grid grid-cols-2 grid-rows-2 gap-0">
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
        <div class="flex-grow h-1/6 bg-white rounded-b-lg p-0 border-t border-gray-200 flex items-center justify-center">
          <router-link
                  :to="{ name: 'costGraph' }"
                  class="text-center text-gray-700 hover:text-blue-500 hover:underline"
                  >청구서 확인
          </router-link>
        </div>
      </div>
  
      <div class="col-span-12 rounded-lg shadow-md bg-gray-200 p-0 sm:col-span-3 h-64 flex flex-col overflow-hidden">
        <!-- Upper Section (1/6 height) -->
        <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
          <h1 class="text-black text-xl">기본 결제 카드</h1>
        </div>
        <!-- Lower Section (5/6 height) -->
        <div class="flex-grow h-5/6 border-t border-gray-200 bg-white p-0">
          <!-- Lower content -->
        </div>
      </div>
  
      <div class="footer col-span-12 rounded-lg border border-gray-800 bg-gray-700 p-6">
        <!-- Footer content -->
      </div>
    </div>
  </template>
  
  <script setup>
    defineProps({
        selectedOrganizationId: Number
    })
  </script>
  
  <style scoped></style>
  