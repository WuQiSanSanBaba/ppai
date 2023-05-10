function getUserList (params) {
  return $axios({
    url: '/manage/user/getUserList',
    method: 'post',
    params
  })
}





// 新增---添加用户
function addUser (params) {
  return $axios({
    url: '/manage/user/addUser',
    method: 'post',
    data: { ...params }
  })
}

// 修改
function updateUser (params) {
  return $axios({
    url: '/manage/user/updateUser',
    method: 'post',
    data: { ...params }
  })
}// 删除
function deleteUser (params) {
  return $axios({
    url: '/manage/user/deleteUser',
    method: 'post',
    data: { ...params }
  })
}

// 修改页面反查详情接口
function getUserByCondition (param) {
  return $axios({
    url: `/query/getUserByCondition`,
    method: 'post',
    data:param
  })
}