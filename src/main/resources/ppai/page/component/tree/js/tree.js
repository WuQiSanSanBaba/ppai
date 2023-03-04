//获取所有年纪
function getGradeList () {
    return $axios({
        url: '/component/tree/getGradeList',
        method: 'get'
    })
}

//获取所有年纪
function getClassList (id) {
    return $axios({
        url: '/component/tree/getClassList/'+id,
        method: 'get'
    })
}