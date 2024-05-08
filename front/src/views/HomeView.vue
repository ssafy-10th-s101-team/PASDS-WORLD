<template>
  <div class="relative">
    <!-- <img src="../assets/images/background1-example.jpg" class="h-full w-full object-cover" /> -->

    <!-- 첫 번째 섹션 -->
    <div class="sticky top-0 h-screen flex flex-col items-center justify-center">
      <div class="relative pt-[0px] lg:pt-[0px] pb-[110px] bg-white">
      <div class="container">
        <div class="flex flex-wrap -mx-4">
          <div class="w-full lg:w-6/12 px-4 flex flex-col justify-center">
            <div class="hero-content">
              <h1
                class="
                  text-dark
                  font-bold
                  text-4xl
                  sm:text-[42px]
                  lg:text-[40px]
                  xl:text-[42px]
                  leading-snug
                  mb-3
                "
              >
                PASDSWRORLD 에서 <br />
                비밀번호를 쉽고 안전하게 <br />
                관리하세요.
              </h1>
              <p class="text-base mb-8 text-body-color max-w-[480px]">
                Manage your passwords easily and safely at PASDSWORLD.
              </p>
              <ul class="flex flex-wrap items-center">
                <li>
                  <router-link :to="{ name: 'memberLogin' }"
                    href="javascript:void(0)"
                    class="
                      py-4
                      px-6
                      sm:px-10
                      lg:px-8
                      xl:px-10
                      inline-flex
                      items-center
                      justify-center
                      text-center text-white text-base
                      bg-primary
                      hover:bg-opacity-90
                      font-normal
                      rounded-lg
                      bg-samsung-blue
                    "
                  >
                    Get Started
                  </router-link>
                </li>
                <li>
                  <a
                    href="#download-section"
                    class="
                      py-4
                      px-6
                      sm:px-10
                      lg:px-8
                      xl:px-10
                      inline-flex
                      items-center
                      justify-center
                      text-center text-base
                      font-normal
                      text-body-color
                      hover:text-primary
                    "
                  >
                    <span class="mr-2">
                      <svg
                        width="22"
                        height="22"
                        viewBox="0 0 22 22"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <circle cx="11" cy="11" r="11" fill="#3056D3" />
                        <rect
                          x="6.90906"
                          y="13.3636"
                          width="8.18182"
                          height="1.63636"
                          fill="white"
                        />
                        <rect
                          x="10.1818"
                          y="6"
                          width="1.63636"
                          height="4.09091"
                          fill="white"
                        />
                        <path
                          d="M11 12.5454L13.8343 9.47726H8.16576L11 12.5454Z"
                          fill="white"
                        />
                      </svg>
                    </span>
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
    <div
      class="sticky top-0 h-screen flex flex-col items-center justify-center bg-white text-white"
    >
      <img id="download-section" src="../assets/images/pasdsworld_v2_qr.png" alt="QR" />
      <h2 class="font-samsungone700c text-black text-lg">어플리케이션 다운로드</h2>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
/* 필요에 따라 스타일링을 추가하세요 */
</style>
