<template>
  <div class="col-span-12 rounded-lg shadow-md bg-gray-200 p-0 sm:col-span-3 h-64 flex flex-col overflow-hidden">
    <!-- Upper Section (1/6 height) -->
    <div class="flex-none h-1/6 bg-gray-100 rounded-t-lg p-0 flex items-center pl-4">
      <h1 class="text-black text-xl">기본 결제 카드</h1>
    </div>
    <!-- Lower Section (5/6 height) -->
    <div class="flex-grow h-5/6 border-t border-gray-200 bg-white p-0">
      <!-- Lower content -->
      <div class="flex items-center justify-center py-8 px-4">
        <!--- more free and premium Tailwind CSS components at https://tailwinduikit.com/ --->

        <div class="w-11/12 lg:w-2/3">
          <div class="flex flex-col justify-between h-full">
            <div>
              <div class="lg:flex w-full justify-between">
                <h3 class="text-gray-600 dark:text-gray-400 leading-5 text-base md:text-xl font-bold">Selling
                  Overview</h3>
                <div class="flex items-center justify-between lg:justify-start mt-2 md:mt-4 lg:mt-0">
                  <div class="flex items-center">
                    <button
                      class="py-2 px-4 bg-gray-100 dark:bg-gray-700 rounded ease-in duration-150 text-xs text-gray-600 dark:text-gray-400 hover:bg-gray-200">
                      Dollars
                    </button>
                    <button
                      class="py-2 px-4 bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 rounded text-white ease-in duration-150 text-xs hover:bg-indigo-600">
                      Tickets
                    </button>
                  </div>
                  <div class="lg:ml-14">
                    <div
                      class="bg-gray-100 dark:bg-gray-700 ease-in duration-150 hover:bg-gray-200 pb-2 pt-1 px-3 rounded-sm">
                      <select aria-label="select year"
                              class="text-xs text-gray-600 dark:text-gray-400 bg-transparent focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-800 rounded">
                        <option class="leading-1">Year</option>
                        <option class="leading-1">2020</option>
                        <option class="leading-1">2019</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="flex items-end mt-6">
                <h3 class="text-indigo-500 leading-5 text-lg md:text-2xl">65,875</h3>
                <div class="flex items-center md:ml-4 ml-1 text-green-700">
                  <p class="text-green-700 text-xs md:text-base">17%</p>
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
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>

</style>