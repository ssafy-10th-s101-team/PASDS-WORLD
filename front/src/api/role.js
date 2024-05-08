import { localAxios } from "@/utils/http-commons";

const prefix = "/role";

//역할 조회
async function getRole(teamId, success,fail){
    return localAxios.get(prefix+`/${teamId}`).then(success).catch(fail);
}

//역할 생성
async function createRole(body, success, fail){
    return localAxios.post(prefix+`/create`,body).then(success).catch(fail);
}

//역할 수정
async function updateRole(body, success, fail){
    return localAxios.post(prefix+`/update`,body).then(success).catch(fail);
}

//역할 삭제
async function deleteRole(body, success, fail){
    return localAxios.post(prefix+`/delete`,body).then(success).catch(fail);
}

//팀원 역할 설정
async function assignRole(body, success, fail){
    return localAxios.post(prefix+`/assgin-role`,body).then(success).catch(fail);
}

export {getRole,
    createRole,
    updateRole,
    deleteRole,
    assignRole,
}
