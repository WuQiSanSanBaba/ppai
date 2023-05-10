//获取小组信息
function getGroupInfo () {
    return $axios({
        url: `/classroom/group/getGroupInfo`,
        method: 'get'
    })
}
//获取小组信息
function saveGroupInfo (data) {
    return $axios({
        url: `/classroom/group/saveGroupInfo`,
        method: 'post',
        data:data
    })
}

//更新groupMember信息
function updateClassroomPartByPartId (data) {
    return $axios({
        url: `/classroom/classroomPart/updateClassroomPartByPartId`,
        method: 'post',
        data:data
    })
}

