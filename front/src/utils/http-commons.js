import axios from 'axios'
import { nextTick } from 'vue'
import router from '@/router'

// const baseURL = 'https://pasds.world/app/api'
const baseURL = 'http://localhost:8080/app/api'

const localAxios = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  withCredentials: true //쿠키사용시 필요
})

// 응답 인터셉터 추가
localAxios.interceptors.response.use(
  (response) => {
    // 2XX 범위의 상태 코드에 대해 요청이 성공적으로 수행된 경우
    return response
  },
  (error) => {
    // 응답 상태 코드가 2XX 범위를 벗어난 경우
    if (error.response && error.response.status === 401) {
      console.log('401: ' + error)
      sessionStorage.clear()
      router.push({ name: 'memberLogin' }).then(() => {
        nextTick(() => {
          window.location.reload()
        })
      })
      // return Promise.resolve()
    }
    return Promise.reject(error) // 에러를 다음 체인으로 전달
  }
)

export { localAxios }
