import { localAxios } from "@/utils/http-commons";

const local = localAxios;
const prefix = "/data";

//팀 비밀 리스트 조회
async function getPrivateDatas(teamId,success,fail){
    return local.get(prefix+`/${teamId}`).then(success).catch(fail);
}

//팀 비밀 상세 조회
async function getPrivateData(teamId, privateDataId, success,fail){
    return local.get(prefix+`/${teamId}/${privateDataId}`).then(success).catch(fail);

}

//팀 비밀 생성
async function createPrivateData(body, success, fail){
    return local.post(prefix+`/create`,body).then(success).catch(fail);
}

//팀 비밀 수정
async function updatePrivateData(body, success, fail){
    return local.post(prefix+`/update`,body).then(success).catch(fail);
}

//팀 비밀 삭제
async function deletePrivateData(body, success, fail){
    return local.post(prefix+`/delete`,body).then(success).catch(fail);
}

export {
    getPrivateDatas,
    getPrivateData,
    createPrivateData,
    updatePrivateData,
    deletePrivateData
}
