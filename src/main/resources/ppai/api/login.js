function loginApi(data) {
  return $axios({
    'url': '/manage/login/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/manage/login/logout',
    'method': 'post',
  })
}


