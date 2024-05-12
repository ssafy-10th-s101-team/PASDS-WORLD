import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCommonStore = defineStore('common', () => {
  const toggleHidden = (id) => {
    const obj = document.getElementById(id)
    if (obj) {
      obj.classList.toggle('hidden')
    }
  }
  const removeHidden = (id) => {
    const obj = document.getElementById(id)
    if (obj) {
      obj.classList.remove('hidden')
    }
  }

  // 인증번호 timer
  const time = ref(180);
  const min = computed(() => Math.floor(time.value / 60));
  const sec = computed(() => String(time.value % 60).padStart(2,'0'));
  const intervalId = ref(null);
  const message = ref('');

  const inputTime = ref(180)
  const emailRequest = ref(true)

  const startTimer = () => {
    message.value = null
    time.value = inputTime.value
    if (intervalId.value !== null) {
      clearInterval(intervalId.value); // 기존 타이머가 있다면 중지
    }
    intervalId.value = setInterval(() => {
      if (time.value > 1) {
        time.value -= 1 // 1초 감소
      } else {
        clearInterval(intervalId.value);
        intervalId.value = null // 타이머 종료
        message.value = '인증시간이 만료되었습니다. 인증코드를 다시 전송해주세요.'
      }
    }, 1000);
  }

// 타이머 종료 함수
  const stopTimer = () => {
    if (intervalId.value !== null) {
      clearInterval(intervalId.value);
      intervalId.value = null;
      message.value = null;
    }
  }

  return {
    toggleHidden,
    removeHidden,
    startTimer,
    stopTimer,
    min,
    sec,
    intervalId,
    message,
    inputTime,
    emailRequest
  }
})
