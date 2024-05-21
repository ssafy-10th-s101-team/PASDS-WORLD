import { defineStore } from 'pinia'
import cookieHelper from '@/utils/cookie'

export const useUserStore = defineStore('user', {
  state: () => ({
    nickname: cookieHelper.get('nickname') || '',
  }),
  actions: {
    setNickname(newNickname) {
      this.nickname = newNickname;
      cookieHelper.generate('nickname', newNickname); // Assuming you have a method to set cookies
    },
    clearNickname() {
      this.nickname = '';
      cookieHelper.delete('nickname');
    }
  }
})
