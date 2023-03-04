//获取所有配置
function getConfigList (params) {
    return $axios({
        url: '/system/config/getConfigList',
        method: 'post',
        data:params
    })
}

// 修改配置
function updateConfigById (data) {
    return $axios({
        url: '/system/config/updateConfigById',
        method: 'post',
        data: data
    })
}
// 修改配置
function queryConfigById (data) {
    return $axios({
        url: '/system/config/queryConfigById/'+data,
        method: 'get',
        data: data
    })
}


