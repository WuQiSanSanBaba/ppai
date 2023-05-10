//获取班级列表
function getClassList (params) {
    return $axios({
        url: '/manage/class/getClassList',
        method: 'post',
        data:params
    })
}
// 新增---添加班级
function addClass (data) {
    return $axios({
        url: '/manage/class/addClass',
        method: 'post',
        data: data
    })
}
// 根据ID修改班级信息
function updateClassById (data) {
    return $axios({
        url: '/manage/class/updateClassById',
        method: 'post',
        data: data
    })
}
function deleteClass(id) {
    return $axios({
        url: '/manage/class/deleteClass/'+id,
        method: 'delete',
    })
}
//根据ID查询班级信息
function queryClassById (param) {
    return $axios({
        url: '/manage/class/queryClassById/'+param,
        method: 'get',
    })
}

