import { localAxios } from "@/utils/http-commons";

const local = localAxios;
const prefix = "/role";

//역할 조회
async function getRole(teamId, success,fail){
    return local.get(prefix+`/${teamId}`).then(success).catch(fail);
}


//역할 생성
async function createRole(body, success, fail){
    return local.post(prefix+`/create`,body).then(success).catch(fail);
}

//역할 수정
async function updateRole(body, success, fail){
    return local.post(prefix+`/update`,body).then(success).catch(fail);
}

//역할 삭제
async function deleteRole(body, success, fail){
    return local.post(prefix+`/delete`,body).then(success).catch(fail);
}

//팀원 역할 설정
async function assignRole(body, success, fail){
    return local.post(prefix+`/assgin-role`,body).then(success).catch(fail);
}

export {getRole,
    createRole,
    updateRole,
    deleteRole,
    assignRole,
}
