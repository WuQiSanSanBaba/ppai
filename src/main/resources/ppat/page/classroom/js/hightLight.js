async function analizyQuestion(text, editor, classroomPart, subject) {
    if (!classroomPart) {
        //获取主题
        await loadClassroom().then(result => {
            classroomPart = result.data.classroomPart
        });
        if (classroomPart == null) {
            new $.zui.Messager('提示消息：获取课堂失败', {
                type: 'danger' // 定义颜色主题
            }).show();
        }
    }
    const boolen = valiateClassAndSubject(classroomPart)
    if (boolen === false) {
        return 'false'
    }
    if (!subject) {
        await getSubjectById(classroomPart.subjectId).then(res => {
            subject = res.data
        })
    }

    //分析核心概念和一般概念
    let result = checkConcepts(text, subject, editor)
    result.partId = classroomPart.partId
    result.subjectId = classroomPart.subjectId
    result.groupId = classroomPart.groupId
    result.classroomId = classroomPart.classroomId
    return result;
    //保存
}

function valiateClassAndSubject(classroomPart) {
    let boo = true
    if (classroomPart == null) {
        new $.zui.Messager('你还没有加入课堂', {
            type: 'danger' // 定义颜色主题
        }).show();
        boo = false;
    }
    if (!classroomPart.subjectId) {
        new $.zui.Messager('你还没有选择主题', {
            type: 'danger' // 定义颜色主题
        }).show();
        boo = false;
        window.parent.dialogChange('/ppat/page/classroom/subject/selectSubject.html', '选择主题', 3)
        boo = false;
    }
    if (classroomPart.questionId != null) {
        new $.zui.Messager('你已经新建问题了请勿重复新建', {
            type: 'danger' // 定义颜色主题
        }).show();
        boo = false;
    }
    return boo;
}

function checkContent(text, title) {
    if (!text) {
        new $.zui.Messager('你没有输入任何内容', {
            type: 'warning' // 定义颜色主题
        }).show();
        return false;
    }
    if (!title) {
        new $.zui.Messager('你没有输入标题', {
            type: 'warning' // 定义颜色主题
        }).show();
        return false;
    }
    return true;
}

function checkConcepts(text, subject, editor) {
    const coreList = JSON.parse(subject.coreConceptJsonArray)
    const generalList = JSON.parse(subject.generalConceptJsonArray)
    /*获取核心概念高亮*/
    let coreJsonArray = [];
    let geneJsonArray = [];
    coreList.map(item => {
        if (text.indexOf(item) > -1) {
            coreJsonArray.push(item);
        }
    })
    /*获取一般概念高亮*/
    generalList.map(item => {
        if (text.indexOf(item) > -1) {
            geneJsonArray.push(item);
        }
    })
    //概念比较处理
    const html = editor.getHtml();
    const content = editor.getText();
    let flag1 = 0;

    if (coreJsonArray.length > 0) {
        coreJsonArray = JSON.stringify(coreJsonArray)
    } else {
        coreJsonArray = ''
    }
    if (geneJsonArray.length > 0) {
        geneJsonArray = JSON.stringify(geneJsonArray)
    } else {
        geneJsonArray = ''
    }

    return {content, html, coreJsonArray, geneJsonArray}
}

var dealHightLight = {
    hadCoreJsonArray: [],
    hadGeneJsonArray: [],
    hadUnderlineArray: [],
    hadAddJsonArray: [],
    hadAnnotationArray: [],
    excute(question, element) {
        dealHightLight.hadHighArray = []
        dealHightLight.hadUnderlineArray = []
        dealHightLight.hadAddHighlightArray = []
        dealHightLight.hadAnnotationArray = []
        this.preElement(question.coreJsonArray, 'core', element)
        this.preElement(question.geneJsonArray, 'gene', element)
        this.preElement(question.underlineJsonArray, 'underline', element)
        this.preElement(question.annotationJsonArry, 'annotation', element)
        this.preElement(question.addJsonArray, 'add', element)
    },
    preElement(jsonArray, type, element) {
        if (!jsonArray){
            return
        }
        let array = JSON.parse(jsonArray);
        if (array.length > 0) {
            for (let string of array) {
                if (type === 'add') {
                    this.traverseAdd(element, string, type)
                } else if (type === 'annotation') {
                    this.traverseAnnotation(element, string, type)
                } else {
                    this.traverse(element, string, type)
                }
            }
        }
    },

    traverse(node, keyword, type) {
        $(node).find("*").each(function () {
            // 判断当前节点是否包含关键词
            if ($(this).text().indexOf(keyword) >= 0 || $(this).text().indexOf(keyword.word)) {
                // 如果包含，则将关键词用<span>标签包裹起来，并添加样式
                if (type === 'underline' && dealHightLight.hadUnderlineArray.indexOf(keyword) < 0) {
                    dealHightLight.hadUnderlineArray.push(keyword);
                    $(this).html($(this).html().replace(new RegExp(keyword, "i"), "<span title='双击进入注释详情' ondblclick='addAnnotationJS(" + keyword + ")' class='underline'>" + keyword + "</span>"));
                } else if (type === 'core' && dealHightLight.hadHighArray.indexOf(keyword) < 0) {
                    dealHightLight.hadHighArray.push(keyword);
                    $(this).html($(this).html().replace(new RegExp(keyword, "i"), "<span class='core'>" + keyword + "</span>"));
                }else if (type === 'gene' && dealHightLight.hadHighArray.indexOf(keyword) < 0) {
                    dealHightLight.hadHighArray.push(keyword);
                    $(this).html($(this).html().replace(new RegExp(keyword, "i"), "<span class='gene'>" + keyword + "</span>"));
                }
            }
        });
    }, traverseAdd(node, keyword, type) {
        const flag = keyword.flag;
        const word = keyword.word;
        $(node).find("*").each(function () {
            // 判断当前节点是否包含关键词
            if ($(this).text().indexOf(word) >= 0 || $(this).text().indexOf(word)) {
                // 如果包含，则将关键词用<span>标签包裹起来，并添加样式
                if (flag === 'core') {
                    $(this).html($(this).html().replace(new RegExp(word, "i"), "<span class='core'>" + word + "</span>"));

                } else {
                    $(this).html($(this).html().replace(new RegExp(word, "i"), "<span class='gene'>" + word + "</span>"));
                }
                dealHightLight.hadAddHighlightArray.push(word);

            }
        });
    }, traverseAnnotation(node, keyword, type) {
        const annotationGroupId = keyword.annotationGroupId;
        const annotationWord = keyword.annotationWord;
        $(node).find("*").each(function () {
            // 判断当前节点是否包含关键词
            if ($(this).text().indexOf(annotationWord) >= 0 || $(this).text().indexOf(annotationWord)) {
                // 如果包含，则将关键词用<span>标签包裹起来，并添加样式
                $(this).html($(this).html().replace(new RegExp(annotationWord, "i"), "<span title='双击进入注释详情' ondblclick='toAnnotation(" + annotationGroupId + ")' class='annotation'>" + annotationWord + "</span>"));
                dealHightLight.hadAnnotationArray.push(annotationWord);
            }
        });
    }
}
