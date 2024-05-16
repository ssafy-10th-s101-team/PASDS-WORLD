<template>
  <div>
    <header>
      <nav class="bg-white border-gray-200 px-4 lg:px-6 py-2.5 dark:bg-gray-800 pb-8">
        <div class="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl">
          <router-link :to="{ name: 'home' }" class="flex items-center mt-6">
            <span class="self-center text-xl font-samsungone700c whitespace-nowrap dark:text-white"
              >PASDSWORLD</span
            >
          </router-link>
          <div class="flex items-center lg:order-2">
            <div class="mt-6">
              <router-link
                :to="{ name: 'main' }"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >메인
              </router-link>
            </div>
            <div class="mt-6">
              <router-link
                :to="{ name: 'organization' }"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >조직관리
              </router-link>
            </div>
            <div v-if="userStore.nickname" class="mt-6">
              
              <router-link :to="{ name: 'myPage' }">
                <span
                  class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                  >{{ userStore.nickname }}</span
                >
              </router-link>

              <button
                @click="logout"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
              >
                로그아웃
              </button>
              <!-- <button
                @click="jwtTest"
                class="text-gray-800 dark hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
              >
                토큰테스트
              </button> -->
            </div>

            <!-- 알림 시작 -->
            <div v-if="userStore.nickname" class="relative notification">
              <svg
                id="Layer_1"
                xmlns="http://www.w3.org/2000/svg"
                class="cursor-pointer hover:bg-samsung-blue text-gray-800 dark:text-white hover:text-white p-2 rounded-full"
                @click="toggleNotifications"
                fill="currentColor"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px"
                y="0px"
                width="60px"
                height="72px"
                viewBox="-11 -6 67 67"
                xml:space="preserve"
              >
                <image
                  id="image0"
                  width="50"
                  height="50"
                  x="0"
                  y="0"
                  xlink:href="data:image/png;base64,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"
                />
                <text x="37" y="18" font-size="12" text-anchor="middle" font-weight="bold">
                  {{ notifications.length > 99 ? '99+' : notifications.length }}
                </text>
              </svg>
              <div
                v-show="showNotifications"
                class="absolute bg-white border rounded border-gray-300 shadow-lg mt-2 py-2 w-48 right-0"
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
            <div v-if="!userStore.nickname" class="mt-6">
              <router-link
                :to="{ name: 'memberLogin' }"
                class="text-gray-800 dark:text-white hover:bg-samsung-blue hover:text-white focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"
                >로그인
              </router-link>
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
import { onMounted, onUnmounted, ref, watch } from 'vue'
import { localAxios } from '@/utils/http-commons.js'
import { getNotifications, readNotification } from '@/api/notification.js'
import { useRouter } from 'vue-router'
import cookieHelper from '@/utils/cookie'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
// const nickname = ref('')
const showNotifications = ref(false)
const notifications = ref([
  {
    message: 'hello'
  }
])

const logout = async () => {
  try {
    await localAxios.get(`/member/logout`)
    // sessionStorage.clear()
    cookieHelper.deleteAll()
    userStore.clearNickname()
    router.push({ name: 'home' })
  } catch (error) {}
}

const jwtTest = async () => {
  try {
    const response = await localAxios.post(`/member/jwt-test`, null)
    console.log(response.data)
  } catch (error) {}
}

//알림 관련 함수
const toggleNotifications = () => {
  if (notifications.value.length <= 0) {
    showNotifications.value = false
    return
  }
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
  // nickname.value = sessionStorage.getItem('nickname')

  console.log(' nickname : ', userStore.nickname)

  // 잠시 버그만 안나게 추가 했습니다
  if (userStore.nickname) {
    try {
      notifications.value = await getNotifications(0)
    } catch (error) {
      console.log('에러 발생. ', error.respnse.data.message)
    }
    document.addEventListener('mousedown', handleClickOutside)
    // fetchUnreadNotifications()
  }
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
  width: 60px;
  height: 70px;
}
</style>
