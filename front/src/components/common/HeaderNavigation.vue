<template>
  <div>
    <header>
      <nav class="bg-white border-gray-200 px-4 lg:px-6 py-2.5 dark:bg-gray-800 pb-8">
        <div class="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl">
          <router-link :to="{ name: 'home' }" class="flex items-center">
            <span class="self-center text-xl font-samsungone700c whitespace-nowrap dark:text-white"
              >PASDSWORLD</span
            >
          </router-link>
          <div class="flex items-center lg:order-2">
            <div>
              <router-link
                :to="{ name: 'main' }"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >메인
              </router-link>
            </div>
            <div>
              <router-link
                :to="{ name: 'organization' }"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >조직관리</router-link
              >
            </div>
            <div v-if="nickname">
              <button
                @click="logout"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
              >
                로그아웃
              </button>
              <router-link :to="{ name: 'myPage' }">
                <span
                  class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                  >{{ nickname }}</span
                >
              </router-link>
              <button
                @click="jwtTest"
                class="text-gray-800 dark hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
              >
                토큰테스트
              </button>
            </div>

            <!-- 알림 시작 -->
            <div v-if="nickname" class="relative notification">
              <svg
                @click="toggleNotifications"
                class="icon-bell cursor-pointer text-gray-800 dark:text-white hover:text-white hover:bg-samsung-blue p-2 rounded-full"
                fill="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  d="M12 21c1.1 0 2-.9 2-2h-4c0 1.1.9 2 2 2zm6-3v-5c0-3.11-1.64-5.87-4.5-6.64V5.5c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.76C7.64 6.13 6 8.89 6 12v5l-2 2v1h16v-1l-2-2zm-2 1H8v-6c0-2.48 1.68-4.5 4-4.5s4 2.02 4 4.5v6z"
                />
                <text x="12" y="16" font-size="6" text-anchor="middle" font-weight="bold">
                  {{ notifications.length > 99 ? '99+' : notifications.length }}
                </text>
              </svg>
              <div
                v-show="showNotifications"
                class="absolute bg-white border border-gray-300 shadow-lg mt-2 py-2 w-48 right-0"
              >
                <!-- Loop through notifications -->
                <div
                  v-for="notification in notifications"
                  :key="notification.id"
                  @click="handleNotificationClick(notification)"
                  class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >
                  {{ notification.message }}
                </div>
              </div>
            </div>
            <!-- 알림 끝-->
            <div v-if="!nickname">
              <router-link
                :to="{ name: 'memberLogin' }"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >로그인</router-link
              >
            </div>

            <!-- <a
              href="#"
              class="text-white bg-samsung-blue hover:bg-samsung-blue focus:ring-4 focus:ring-samsung-blue font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:bg-samsung-blue dark:hover:bg-samsung-blue focus:outline-none dark:focus:ring-samsung-blue"
              >Get started</a
            > -->
            <!-- <button
              data-collapse-toggle="mobile-menu-2"
              type="button"
              class="inline-flex items-center p-2 ml-1 text-sm text-gray-500 rounded-lg lg:hidden hover:bg-samsung-blue focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
              aria-controls="mobile-menu-2"
              aria-expanded="false"
              @click="toggleHidden('mobile-menu-2')"
            >
              <span class="sr-only">Open main menu</span>
              <svg
                class="w-6 h-6"
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  fill-rule="evenodd"
                  d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
                  clip-rule="evenodd"
                ></path>
              </svg>
              <svg
                class="hidden w-6 h-6"
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"
                ></path>
              </svg>
            </button>
          </div>
          <div
            class="hidden justify-between items-center w-full lg:flex lg:w-auto lg:order-1"
            id="mobile-menu-2"
          >
            <ul class="flex flex-col mt-4 font-medium lg:flex-row lg:space-x-8 lg:mt-0">
              <li>
                <router-link
                  :to="{ name: 'dashboard' }"
                  class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-samsung-blue hover:text-white lg:hover:bg-transparent lg:border-0 lg:hover:text-samsung-blue lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700"
                  >비밀</router-link
                >
              </li> -->
            <!-- <li>
                <a
                  href="#"
                  class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-samsung-blue hover:text-white lg:hover:bg-transparent lg:border-0 lg:hover:text-samsung-blue lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700"
                  >Menu</a
                >
              </li>
              <li>
                <a
                  href="#"
                  class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-samsung-blue hover:text-white lg:hover:bg-transparent lg:border-0 lg:hover:text-samsung-blue lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700"
                  >Menu</a
                >
              </li>
              <li>
                <a
                  href="#"
                  class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-samsung-blue hover:text-white lg:hover:bg-transparent lg:border-0 lg:hover:text-samsung-blue lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700"
                  >Menu</a
                >
              </li>
              <li>
                <a
                  href="#"
                  class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-samsung-blue hover:text-white lg:hover:bg-transparent lg:border-0 lg:hover:text-samsung-blue lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700"
                  >Menu</a
                >
              </li> 
            </ul> -->
          </div>
        </div>
      </nav>
    </header>
  </div>
</template>

<script setup>
import { onMounted, ref, onUnmounted } from 'vue'
import { useCommonStore } from '@/stores/common'
import { localAxios } from '@/utils/http-commons.js'
import { getNotifications, readNotification } from '@/api/notification.js'
import { useRouter } from 'vue-router'
const router = useRouter()
const commonStore = useCommonStore()
const { toggleDropdown, toggleHidden } = commonStore
const nickname = ref('')
const showNotifications = ref(false)
const notifications = ref([
  {
    message: 'hello'
  }
])

const logout = async () => {
  try {
    await localAxios.get(`/member/logout`)
    sessionStorage.removeItem('nickname')
    nickname.value = ''
  } catch (error) {
    sessionStorage.removeItem('nickname')
    nickname.value = ''
  }
}

const jwtTest = async () => {
  try {
    const response = await localAxios.post(`/member/jwt-test`, null)
    console.log(response)
  } catch (error) {}
}

//알림 관련 함수
const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
}

//알림 목록 끄기
const handleClickOutside = (event) => {
  if (!event.target.closest('.notification') && showNotifications.value) {
    showNotifications.value = false
  }
}

//알림 클릭시 이동
const handleNotificationClick = async (notification) => {
  try {
    // 알림 읽음 처리 API 호출
    await readNotification(notification.id)
    // 알림토글 끄기
    showNotifications.value = !showNotifications.value
    // 알림 하나 제거
    const index = notifications.value.findIndex((n) => n.id === notification.id)
    if (index !== -1) {
      notifications.value.splice(index, 1)
    }
    // myPage로 라우팅
    router.push({ name: 'myPage' })
  } catch (error) {
    console.error('Notification read error:', error)
  }
}

onMounted(async () => {
  nickname.value = sessionStorage.getItem('nickname')
  try {
    notifications.value = await getNotifications(0)
  } catch (error) {
    console.log('에러 발생. ', error.respnse.data.message)
  }
  document.addEventListener('mousedown', handleClickOutside)
  // fetchUnreadNotifications()
})

onUnmounted(() => {
  document.removeEventListener('mousedown', handleClickOutside)
})
</script>

<style scoped>
.notification {
  width: 50px;
  height: 50px;
  -webkit-user-select: none; /* Chrome/Safari */
  -moz-user-select: none; /* Firefox */
  -ms-user-select: none; /* IE10+ */
  user-select: none; /* Standard syntax */
  position: relative;
  z-index: 1000; /* 높은 값으로 설정하여 다른 요소들 위에 위치하도록 함 */
}

.icon-bell {
  width: 70px;
  height: 70px;
}
</style>
