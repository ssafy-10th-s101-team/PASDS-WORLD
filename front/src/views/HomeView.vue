<template>
  <div class="relative bg-white dark:bg-gray-900">
    <!-- 첫 번째 섹션 -->
    <div class="sticky top-0 h-screen flex flex-col items-center justify-center dark:bg-gray-800">
      <div class="relative pt-[0px] lg:pt-[0px] pb-[110px] dark:bg-gray-800">
        <div class="container mx-auto px-4">
          <div class="flex flex-wrap">
            <div class="w-full lg:w-6/12 px-4 flex flex-col justify-center">
              <div class="text-gray-900 dark:text-gray-200 mb-6">
                <h1 class="text-4xl font-bold mb-2">
                  PASDSWORLD에서<br />
                  비밀번호를 쉽고 안전하게<br />
                  관리하세요.
                </h1>
                <p class="mb-4">Manage your passwords easily and safely at PASDSWORLD.</p>
                <ul class="flex flex-wrap items-center">
                  <li>
                    <router-link
                      :to="{ name: 'memberLogin' }"
                      class="py-2 px-4 bg-samsung-blue hover:bg-blue-700 dark:bg-samsung-blue dark:hover:bg-blue-600 text-white rounded-lg transition duration-300"
                    >
                      Get Started
                    </router-link>
                  </li>
                  <li>
                    <a
                      href="#download-section"
                      class="ml-4 py-2 px-4 bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-800 dark:text-gray-200 rounded-lg transition duration-300"
                    >
                      Download App
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="hidden lg:block lg:w-1/12 px-4"></div>
            <div class="w-full lg:w-5/12 px-4">
              <div class="lg:text-right lg:ml-auto">
                <div class="relative inline-block z-10 pt-11 lg:pt-0">
                  <img
                    src="https://cdn.tailgrids.com/1.0/assets/images/hero/hero-image-01.png"
                    alt="hero"
                    class="max-w-full lg:ml-auto"
                  />
                  <span class="absolute -left-8 -bottom-8 z-[-1]">
                    <svg
                      width="93"
                      height="93"
                      viewBox="0 0 93 93"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <circle cx="2.5" cy="2.5" r="2.5" fill="#3056D3" />
                      <circle cx="2.5" cy="24.5" r="2.5" fill="#3056D3" />
                      <circle cx="2.5" cy="46.5" r="2.5" fill="#3056D3" />
                      <circle cx="2.5" cy="68.5" r="2.5" fill="#3056D3" />
                      <circle cx="2.5" cy="90.5" r="2.5" fill="#3056D3" />
                      <circle cx="24.5" cy="2.5" r="2.5" fill="#3056D3" />
                      <circle cx="24.5" cy="24.5" r="2.5" fill="#3056D3" />
                      <circle cx="24.5" cy="46.5" r="2.5" fill="#3056D3" />
                      <circle cx="24.5" cy="68.5" r="2.5" fill="#3056D3" />
                      <circle cx="24.5" cy="90.5" r="2.5" fill="#3056D3" />
                      <circle cx="46.5" cy="2.5" r="2.5" fill="#3056D3" />
                      <circle cx="46.5" cy="24.5" r="2.5" fill="#3056D3" />
                      <circle cx="46.5" cy="46.5" r="2.5" fill="#3056D3" />
                      <circle cx="46.5" cy="68.5" r="2.5" fill="#3056D3" />
                      <circle cx="46.5" cy="90.5" r="2.5" fill="#3056D3" />
                      <circle cx="68.5" cy="2.5" r="2.5" fill="#3056D3" />
                      <circle cx="68.5" cy="24.5" r="2.5" fill="#3056D3" />
                      <circle cx="68.5" cy="46.5" r="2.5" fill="#3056D3" />
                      <circle cx="68.5" cy="68.5" r="2.5" fill="#3056D3" />
                      <circle cx="68.5" cy="90.5" r="2.5" fill="#3056D3" />
                      <circle cx="90.5" cy="2.5" r="2.5" fill="#3056D3" />
                      <circle cx="90.5" cy="24.5" r="2.5" fill="#3056D3" />
                      <circle cx="90.5" cy="46.5" r="2.5" fill="#3056D3" />
                      <circle cx="90.5" cy="68.5" r="2.5" fill="#3056D3" />
                      <circle cx="90.5" cy="90.5" r="2.5" fill="#3056D3" />
                    </svg>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.sticky {
  height: 100vh; /* 뷰포트 높이를 100%로 설정 */
}

/* 화면 크기가 768px 미만일 때 적용할 스타일 */
@media (max-width: 1200px) {
  .sticky {
    height: auto; /* 높이를 자동으로 설정 */
  }
}
</style>
