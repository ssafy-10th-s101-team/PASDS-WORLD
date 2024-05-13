import { localAxios } from '@/utils/http-commons'

const prefix = '/data'

const success = (response) => {
  return response.data
}

const fail = (error) => {
  console.error(error)
  const errmsg = error.response ? error.response.data.message : 'Error fetching data'
  console.error(errmsg)
  return Promise.reject(error)
}

//팀 비밀 리스트 조회
async function getPrivateDatas(teamId, offset) {
  return localAxios
    .get(prefix + `/list/${teamId}/${offset}`)
    .then(success)
    .catch(fail)
}

//팀 비밀 상세 조회
async function getPrivateData(teamId, privateDataId) {
  return localAxios
    .get(prefix + `/${teamId}/${privateDataId}`)
    .then(success)
    .catch(fail)
}

//팀 비밀 생성
async function createPrivateData(body) {
  return localAxios
    .post(prefix + `/create`, body)
    .then(success)
    .catch(fail)
}

//팀 비밀 수정
async function updatePrivateData(body) {
  return localAxios
    .post(prefix + `/update`, body)
    .then(success)
    .catch(fail)
}

//팀 비밀 삭제
async function deletePrivateData(body) {
  return localAxios
    .post(prefix + `/delete`, body)
    .then(success)
    .catch(fail)
}

// 비밀 검색
async function searchPrivateData(text) {
  return localAxios
    .get(prefix + `/search?title=${text}`)
    .then(success)
    .catch(fail)
}

export {
  getPrivateDatas,
  getPrivateData,
  createPrivateData,
  updatePrivateData,
  deletePrivateData,
  searchPrivateData
}
