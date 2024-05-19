import { localAxios } from '@/utils/http-commons'

const prefix = '/notification'

const success = (response) => {
  return response.data
}

const fail = (error) => {
  console.error(error)
  const errmsg = error.response ? error.response.data.message : 'Error fetching data'
  console.error(errmsg)
  return Promise.reject(error)
}

//알림목록 가져옴
async function getNotifications(offset) {
  return localAxios.get(prefix+`?offset=${offset}`).then(success).catch(fail)
}

//알림 읽음 처리
async function readNotification(notificationId){
    return localAxios.get(prefix+`/${notificationId}`).then(success).catch(fail)
}

// //조직원 역할 수정
// async function updateOrganizationRole(body){
//   return localAxios.post(prefix +`/update-role`, body).then(success).catch(fail);
// }


export {
    getNotifications,
    readNotification
}
