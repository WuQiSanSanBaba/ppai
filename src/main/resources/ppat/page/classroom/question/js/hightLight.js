async function analizyQuestion(text) {
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
        return 'false'
    }
    if (classInfo.partId) await getSubjectById(classInfo.subjectId).then(res => {
        subject = res.data
    })

    //分析核心概念和一般概念
    let result = checkConcepts(text, subject)
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
        window.parent.dialogChange('/ppat/page/classroom/subject/selectSubject.html','选择主题',3)
        boo = false;
    }
    if (classInfo.questionId != null) {
        editor.alert('你已经新建问题了请勿重复新建')
        boo = false;
    }
    return boo;
}
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