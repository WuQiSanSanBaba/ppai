function getUserList (params) {
  return $axios({
    url: '/manage/user/getUserList',
    method: 'post',
    params
  })
}

function getTypeList(params){
  return $axios({
    url: '/manage/user/getTypeList/'+params,
    method: 'get'
  })
}

// 修改---启用禁用接口
function enableOrDisableEmployee (params) {
  return $axios({
    url: '/employee',
    method: 'put',
    data: { ...params }
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

// 修改---添加员工
function editEmployee (params) {
  return $axios({
    url: '/employee',
    method: 'put',
    data: { ...params }
  })
}

// 修改页面反查详情接口
function getUserByCondition (param) {
  return $axios({
    url: `/manage/user/getUserByCondition`,
    method: 'post',
    data:param
  })
}