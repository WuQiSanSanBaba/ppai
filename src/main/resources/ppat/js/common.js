function getDictListByGroupId(id) {
    return $axios({
        url: `/query/getDictListByGroupId/${id}`,
        method: 'get'
    })
}

function getMenuApi() {
    return $axios({
        'url': '/manage/menu/getMenuList',
        method: 'get',
    })
}



 function loadClassroom() {
     return  $axios({
        'url': '/manage/login/getUser',
        'method': 'get'
    })

}