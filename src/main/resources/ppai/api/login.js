function loginApi(data) {
  return $axios({
    'url': '/operator/login/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/operator/login/logout',
    'method': 'post',
  })
}


