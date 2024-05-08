import { localAxios } from '@/utils/http-commons'

const prefix = '/organization'

//조직리스트 가져옴
async function getOrganizations(success, fail) {
  return localAxios.get(prefix).then(success).catch(fail)
}

//조직원 목록 가져옴
async function getOrganizationMembers(organizationId, offset, success, fail) {
  return localAxios
    .get(prefix + `/${organizationId}/${offset}`)
    .then(success)
    .catch(fail)
}

//조직 생성
async function createOrganization(body, success, fail) {
  return localAxios
    .post(prefix + `/create`, body)
    .then(success)
    .catch(fail)
}

//조직 초대
async function inviteOrganization(body, success, fail) {
  return localAxios
    .post(prefix + `/invite`, body)
    .then(success)
    .catch(fail)
}

//조직 추방
async function removeOrganization(body, success, fail) {
  return localAxios
    .post(prefix + `/remove`, body)
    .then(success)
    .catch(fail)
}

//조직장 위임
async function assignHeader(body, success, fail) {
  return localAxios
    .post(prefix + `/assign`, body)
    .then(success)
    .catch(fail)
}

//조직 탈퇴
async function leaveOrganization(body, success, fail) {
  return localAxios
    .post(prefix + `/leave`, body)
    .then(success)
    .catch(fail)
}

//조직 삭제
async function deleteOrganization(body, success, fail) {
  return localAxios
    .post(prefix + `/delete`, body)
    .then(success)
    .catch(fail)
}

//조직명 변경
async function renameOrganization(body, success, fail) {
  return localAxios
    .post(prefix + `/rename`, body)
    .then(success)
    .catch(fail)
}

//조직초대 수락
async function acceptOrganizationInvitaion(body, success, fail) {
  return localAxios.post(`/invitaion/accept`, body).then(success).catch(fail)
}

//조직초대 거절
async function rejectOrganizationInvitation(body, success, fail) {
  return localAxios.post(`invitaion/reject`, body).then(success).catch(fail)
}

export {
  getOrganizations,
  getOrganizationMembers,
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
