




function getQuestionByIdJquery(questionId) {
    var question = undefined;
    $.ajax({
        url: `/classroom/question/getQuestionById/${questionId}`, type: "GET", async: false, success: function (res) {
            if (res.code === 1) {
                question = res.data
            } else {
                alert(res.msg)
            }
        }, error: function (e) {
            alert(e)
        }
    });
    return question;
}

function editorLoad(questionId) {
     //首先根据questionId去请求问题内容
    question = getQuestionByIdJquery(questionId)
    if (question) {
        //设置编辑器内容
        editor.setHtml(question.html)
        $('#question-input').prop("readonly", true)
        $('#question-input').val(question.title)
    }
}

function editQuestion() {
    window.location.href = '/ppat/page/classroom/question/question.html?questionId=' + question.questionId
}




//概念处理


function loadEditor() {
    //加载富文本编辑器
    const {createEditor, createToolbar} = window.wangEditor

    const editorConfig = {
        placeholder: '在此输入问题答案', onChange(editor) {
            const html = editor.getHtml()
            console.log('editor content', html)
            // 也可以同步到 <textarea>
        }, MENU_CONF: {},
    }

    /*图片上传*/
    editorConfig.MENU_CONF['uploadImage'] = {
        server: '/common/uploadWangEditor',
    }

    const editor = createEditor({
        selector: '#editor-container', html: '<p><br></p>', config: editorConfig, mode: 'simple' // or 'simple'
    })

    const toolbarConfig = {}

    toolbarConfig.toolbarKeys = [// 菜单 key
        "headerSelect", // 分割线
        "blockquote",//引用
        '|', // 菜单 key
        'bold', 'italic', "color", "fontSize",
        '|', // 菜单 key
        "fontSize",
        "fontFamily",
        "lineHeight",
        "bulletedList",
        "numberedList",
        "todo",
        "justifyLeft",
        "justifyRight",
        "justifyCenter",
        "justifyJustify",
        "emotion",
        "insertLink",
        "uploadImage",
        "insertTable",
        "divider",
        "undo",
        "redo",
    ]

    const toolbar = createToolbar({
        editor, selector: '#toolbar-container', config: toolbarConfig, mode: 'default', // or 'simple'
    })
    //加载完编辑器后

    return editor;
}

function addQuestion(params) {
    return axios.post('/classroom/question/addQuestion', {
        title: params.title,
        content: params.content,
        html: params.html,
        coreConceptFlag: params.coreConceptFlag,
        coreConceptJsonArray: params.coreConceptJsonArray,
        generalConceptFlag: params.generalConceptFlag,
        generalConceptJsonArray: params.generalConceptJsonArray,
        groupMemberId: params.groupMemberId,
        subjectId: params.subjectId,
        subjectName: params.subjectName,
        partId: params.partId,
        groupId: params.groupId
    })
}
function updateQuestion(params) {
    return axios.post('/classroom/question/updateQuestion', {
        content: params.content,
        html: params.html,
        coreConceptFlag: params.coreConceptFlag,
        coreConceptJsonArray: params.coreConceptJsonArray,
        generalConceptFlag: params.generalConceptFlag,
        generalConceptJsonArray: params.generalConceptJsonArray,
        questionId: params.questionId

    })
}


