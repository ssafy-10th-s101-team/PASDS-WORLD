import { localAxios } from '@/utils/http-commons'

const prefix = '/role'

const success = (response) => {
  return response.data
}

const fail = (error) => {
  console.error(error)
  const errmsg = error.response ? error.response.data.message : 'Error fetching data'
  console.error(errmsg)
  return Promise.reject(error)
}

//역할 조회
async function getRole(teamId) {
  return localAxios
    .get(prefix + `/${teamId}`)
    .then(success)
    .catch(fail)
}

//역할 생성
async function createRole(body) {
  return localAxios
    .post(prefix + `/create`, body)
    .then(success)
    .catch(fail)
}

//역할 수정
async function updateRole(body) {
  return localAxios
    .post(prefix + `/update`, body)
    .then(success)
    .catch(fail)
}

//역할 삭제
async function deleteRole(body) {
  return localAxios
    .post(prefix + `/delete`, body)
    .then(success)
    .catch(fail)
}

//팀원 역할 설정
async function assignRole(body) {
  return localAxios
    .post(prefix + `/assgin-role`, body)
    .then(success)
    .catch(fail)
}

export { getRole, createRole, updateRole, deleteRole, assignRole }
