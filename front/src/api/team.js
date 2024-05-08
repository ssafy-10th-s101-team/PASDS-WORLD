import { localAxios } from "@/utils/http-commons";

const prefix = "/team";

//팀리스트 가져옴
async function getTeams(organizationId, success,fail){
    return localAxios.get(prefix+`/${organizationId}`).then(success).catch(fail);
}

//팀원 목록 가져옴
async function getTeamMembers(teamId, offset, success,fail){
    return localAxios.get(prefix+`/${teamId}/${offset}`).then(success).catch(fail);

}

//팀 생성
async function createTeam(body, success, fail){
    return localAxios.post(prefix+`/create`,body).then(success).catch(fail);
}

//팀 초대
async function inviteTeam(body, success, fail){
    return localAxios.post(prefix+`/invite`,body).then(success).catch(fail);
}

//팀 추방
async function removeTeam(body, success, fail){
    return localAxios.post(prefix+`/remove`,body).then(success).catch(fail);
}

//팀장 위임
async function assignLeader(body, success, fail){
    return localAxios.post(prefix+`/assign`,body).then(success).catch(fail);
}

//팀 탈퇴
async function leaveTeam(body, success, fail){
    return localAxios.post(prefix+`/leave`,body).then(success).catch(fail);
}

//팀 삭제
async function deleteTeam(body, success, fail){
    return localAxios.post(prefix+`/delete`,body).then(success).catch(fail);
}

//팀명 변경
async function renameTeam(body, success, fail){
    return localAxios.post(prefix+`/rename`,body).then(success).catch(fail);
}

//팀초대 수락
async function acceptTeamInvitaion(body, success, fail){
    return localAxios.post(`/invitaion/accept-team`,body).then(success).catch(fail);
}

//팀초대 거절
async function rejectTeamInvitation(body, success, fail){
    return localAxios.post(`invitaion/reject-team`,body).then(success).catch(fail);
}

//팀 데이터 키 갱신 요청
async function regenerateDataKey(body, success, fail){
    return localAxios.post(prefix+`/regenerate-data-key`,body).then(success).catch(fail);
}

export {getTeams,
    getTeamMembers,
    createTeam,
    inviteTeam,
    removeTeam,
    assignLeader,
    leaveTeam,
    deleteTeam,
    renameTeam,
    acceptTeamInvitaion,
    rejectTeamInvitation,
    regenerateDataKey
}