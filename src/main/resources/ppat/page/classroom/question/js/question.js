

function addHighLight_(questionId, newArrayString) {
    return $axios({
        url: '/classroom/question/addHighlight',
        method: 'post',
        data: {questionId: questionId, newArrayString: newArrayString}
    });
}

function getQuestionBySelf() {
    return $axios({
        url: '/classroom/question/getQuestionBySelf',
        method: 'get'
    });
}

function getQuestionByIdAxios(questionId) {
    return $axios({
        url: `/classroom/question/getQuestionById/${questionId}`,
        method: 'get'
    });
}

