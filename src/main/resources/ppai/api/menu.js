function getMenuApi(){
    return $axios({
        'url': '/manage/menu/getMenuList',
        method: 'get',
    })
}
function getChildrenMenuApi(id) {
    return $axios({
        url: '/manage/menu/getChildrenMenu/'+id,
        method: 'get'
    })
}