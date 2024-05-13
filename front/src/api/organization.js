import { localAxios } from '@/utils/http-commons'

const prefix = '/organization'

const success = (response) => {
  return response.data
}

const fail = (error) => {
  console.error(error)
  const errmsg = error.response ? error.response.data.message : 'Error fetching data'
  console.error(errmsg)
  return Promise.reject(error)
}

//조직리스트 가져옴
async function getOrganizations() {
  return localAxios.get(prefix).then(success).catch(fail)
}

//조직리스트 관리자권한이상것만 가져옴
async function getAdminOrganizations(){
  return localAxios.get(prefix+'?isAdmin=true').then(success).catch(fail)
}

//조직원 목록 가져옴
async function getOrganizationMembers(organizationId, offset) {
  return localAxios
    .get(prefix + `/${organizationId}/${offset}`)
    .then(success)
    .catch(fail)
}

//조직원 역할 수정
async function updateOrganizationRole(body){
  return localAxios.post(prefix +`/update-role`, body).then(success).catch(fail);
}
//조직 생성
async function createOrganization(body) {
  return localAxios
    .post(prefix + `/create`, body)
    .then(success)
    .catch(fail)
}

//조직 초대
async function inviteOrganization(body) {
  return localAxios
    .post(prefix + `/invite`, body)
    .then(success)
    .catch(fail)
}

//조직 추방
async function removeOrganization(body) {
  return localAxios
    .post(prefix + `/remove`, body)
    .then(success)
    .catch(fail)
}

//조직장 위임
async function assignHeader(body) {
  return localAxios
    .post(prefix + `/assign`, body)
    .then(success)
    .catch(fail)
}

//조직 탈퇴
async function leaveOrganization(body) {
  return localAxios
    .post(prefix + `/leave`, body)
    .then(success)
    .catch(fail)
}

//조직 삭제
async function deleteOrganization(body) {
  return localAxios
    .post(prefix + `/delete`, body)
    .then(success)
    .catch(fail)
}

//조직명 변경
async function renameOrganization(body) {
  return localAxios
    .post(prefix + `/rename`, body)
    .then(success)
    .catch(fail)
}

//조직초대 수락
async function acceptOrganizationInvitaion(body) {
  return localAxios.post(`/invitation/accept`, body).then(success).catch(fail)
}

//조직초대 거절
async function rejectOrganizationInvitation(body) {
  return localAxios.post(`invitation/reject`, body).then(success).catch(fail)
}

export {
  getOrganizations,
  getAdminOrganizations,
  getOrganizationMembers,
  updateOrganizationRole,
  createOrganization,
  inviteOrganization,
  removeOrganization,
  assignHeader,
  leaveOrganization,
  deleteOrganization,
  renameOrganization,
  acceptOrganizationInvitaion,
  rejectOrganizationInvitation
}
