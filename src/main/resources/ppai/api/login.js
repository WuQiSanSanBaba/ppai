function loginApi(data) {
  return $axios({
    'url': '/operator/user/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/operator/user/logout',
    'method': 'post',
  })
}

function getMenu(id){
  return $axios({
    'url': '/operator/user/getMenuList/'+id,
    'method': 'post',
  })
}
