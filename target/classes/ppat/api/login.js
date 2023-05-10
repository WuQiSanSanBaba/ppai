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


function getSubjectById(subjectId) {
  return $axios({
    url: `/classroom/subject/getSUbjectById/${subjectId}`,
    method: 'get'
  })
}
