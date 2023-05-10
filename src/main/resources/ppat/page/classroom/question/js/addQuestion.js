
async function analizyQuestion() {
    let classInfo
    let subject
    let classroomPart
    //获取主题
    await loadClassroom().then(result => {
        classroomPart = result.data.classroomPart
    });
    if (classroomPart) {
        classInfo = classroomPart
    } else {
        alert("获取当前课堂失败")
    }
    const boolen = valiateClassAndSubject(classInfo)
    if (boolen === false) {
        return
    }
    if (classInfo.partId) await getSubjectById(classInfo.subjectId).then(res => {
        subject = res.data
    })
    const text = editor.getText();
    const title = document.getElementById('question-input').value
    //内容校验
    const flag = checkContent(text, title)
    if (!flag) {
        return;
    }
    //分析核心概念和一般概念
    let result = checkConcepts(text, subject)
    result.title = title
    result.partId = classInfo.partId
    result.subjectId = classInfo.subjectId
    result.subjectName = classInfo.subjectName
    result.groupId =classInfo.groupId
    return result;
    //保存
}

function valiateClassAndSubject(classInfo) {
    let boo = true
    if (classInfo == null) {
        editor.alert('你还没有加入课堂')
        boo = false;
    }
    if (!classInfo.subjectId) {
        editor.alert('你还没有选择主题')
        boo = false;
    }
    if (classInfo.questionId != null) {
        editor.alert('你已经新建问题了请勿重复新建')
        boo = false;
    }
    return boo;
}

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


//内容校验
function checkContent(text, title) {
    if (!text) {
        alert('你没有输入任何内容')
        return false;
    }
    if (!title) {
        alert('你没有输入标题')
        return false;
    }
    return true;
}


//概念处理
function checkConcepts(text, subject) {
    const coreList = JSON.parse(subject.coreConceptJsonArray)
    const generalList = JSON.parse(subject.generalConceptJsonArray)
    /*获取核心概念高亮*/
    let coreConceptJsonArray = [];
    coreList.map(item => {
        if (text.indexOf(item) > -1) {
            coreConceptJsonArray.push(item);
        }
    })
    /*获取一般概念高亮*/
    let generalConceptJsonArray = [];
    generalList.map(item => {
        if (text.indexOf(item) > -1) {
            generalConceptJsonArray.push(item);
        }
    })
    //概念比较处理
    const html = editor.getHtml();
    const content = editor.getText();
    let coreConceptFlag = 0;
    let generalConceptFlag = 0;

    if (coreConceptJsonArray.length > 0) {
        coreConceptFlag = 1;
        coreConceptJsonArray = JSON.stringify(coreConceptJsonArray)
    } else {
        coreConceptJsonArray = ''
    }
    if (generalConceptJsonArray.length > 0) {
        generalConceptFlag = 1
        generalConceptJsonArray = JSON.stringify(generalConceptJsonArray)
    } else {
        generalConceptJsonArray = ''
    }

    return {content,html,coreConceptFlag, coreConceptJsonArray, generalConceptFlag, generalConceptJsonArray}
}

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


