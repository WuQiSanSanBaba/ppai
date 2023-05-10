function getSUbjectList (params) {
    return $axios({
        url: `/classroom/subject/getSUbjectList`,
        method: 'post',
        data:params
    })
}

function addSubject(params){
    return $axios({
        url: `/classroom/subject/addSubject`,
        method: 'post',
        data:params
    })
}
function updateSubject(params){
    return $axios({
        url: `/classroom/subject/updateSubject`,
        method: 'post',
        data:params
    })
}

function deleteSubject(subjectId) {
    return $axios({
        url: `/classroom/subject/deleteSubject/${subjectId}`,
        method: 'get'
    })
}
function updateSubjectStatus(params) {
    return $axios({
        url: `/classroom/subject/updateSubjectStatus`,
        method: 'post',
        data:params
    })
}
function getSubjectById(subjectId) {
    return $axios({
        url: `/classroom/subject/getSUbjectById/${subjectId}`,
        method: 'get'
    })
}

function getSujectsByStage(stage) {
    return $axios({
        url: `/classroom/subject/getSujectsByStage/${stage}`,
        method: 'get'
    })
}

