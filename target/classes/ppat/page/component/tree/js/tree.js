//获取所有年纪
function getGradeList () {
    return $axios({
        url: '/component/tree/getGradeList',
        method: 'get'
    })
}

//根据年级获取班级
function getClassList (id) {
    return $axios({
        url: '/component/tree/getClassList/'+id,
        method: 'get'
    })
}
//根据年级获取学生
function getStudentList (id) {
    return $axios({
        url: '/component/tree/getStudentList/'+id,
        method: 'get'
    })
}