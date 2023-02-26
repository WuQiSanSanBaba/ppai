function getMenuApi(){
    return $axios({
        'url': '/operator/menu/getMenuList',
        method: 'get',
    })
}
function getChildrenMenuApi(id) {
    return $axios({
        url: '/operator/menu/getChildrenMenu/'+id,
        method: 'get'
    })
}