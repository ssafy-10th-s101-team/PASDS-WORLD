import axios from 'axios'
import { nextTick, h, render } from 'vue'
import router from '@/router'
import cookieHelper from './cookie'
import BaseFailAlert from '@/components/common/BaseFailAlert.vue'

const baseURL = 'https://pasds.world/app/api'
// const baseURL = 'http://localhost:8080/app/api'

const localAxios = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  withCredentials: true //쿠키사용시 필요
})

const errorCodes = [
  'INVALID_SIGNATURE',
  // 'EMAIL_INVALID_SIGNATURE',
  // 'TEMPORARY_INVALID_SIGNATURE',
  // 'ACCESS_INVALID_SIGNATURE',
  // 'REFRESH_INVALID_SIGNATURE',
  'TOKEN_EXPIRED',
  // 'EMAIL_TOKEN_EXPIRED',
  // 'TEMPORARY_TOKEN_EXPIRED',
  // 'REFRESH_TOKEN_EXPIRED',
  'TOKEN_NOT_FOUND',
  // 'EMAIL_TOKEN_NOT_FOUND',
  // 'TEMPORARY_TOKEN_NOT_FOUND',
  // 'ACCESS_TOKEN_NOT_FOUND',
  // 'REFRESH_TOKEN_NOT_FOUND',
  'TOKEN_MISMATCH',
  // 'EMAIL_TOKEN_MISMATCH',
  // 'TEMPORARY_TOKEN_MISMATCH',
  // 'ACCESS_TOKEN_MISMATCH',
  // 'REFRESH_TOKEN_MISMATCH',
  'EMAIL_COOKIE_NOT_FOUND',
  // 'TEMPORARY_COOKIE_NOT_FOUND',
  'ACCESS_COOKIE_NOT_FOUND',
  'REFRESH_COOKIE_NOT_FOUND'
]

function showAlert(message) {
  const alertContainer = document.createElement('div')
  document.body.appendChild(alertContainer)

  const vnode = h(BaseFailAlert, { alertText: message })
  render(vnode, alertContainer)

  setTimeout(() => {
    render(null, alertContainer)
    document.body.removeChild(alertContainer)

    cookieHelper.deleteAll()
    router.push({ name: 'memberLogin' }).then(() => {
      nextTick(() => {
        window.location.reload()
      })
    })
  }, 1500)
}

localAxios.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error && error.response && error.response.data && error.response.data.exceptionCode) {
      const exceptionCode = error.response.data.exceptionCode
      if (errorCodes.includes(exceptionCode)) {
        // alert('세션이 만료되었습니다.\n로그인 해주세요.')
        console.log(exceptionCode)
        showAlert('세션이 만료되었습니다.\n 다시 로그인 해주세요.')
        // sessionStorage.clear()
      }
    }

    return Promise.reject(error)
  }
)

export { localAxios, baseURL }
