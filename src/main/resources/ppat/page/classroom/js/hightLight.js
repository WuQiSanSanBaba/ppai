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
    result.subjectName = classroomPart.subjectName
    result.groupId = classroomPart.groupId
    result.classroomId = classroomPart.classroomId
    result.userId = classroomPart.userId
    result.userName = classroomPart.userName
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
    let jsonArray1 = [];
    coreList.map(item => {
        if (text.indexOf(item) > -1) {
            jsonArray1.push(item);
        }
    })
    /*获取一般概念高亮*/
    generalList.map(item => {
        if (text.indexOf(item) > -1) {
            jsonArray1.push(item);
        }
    })
    //概念比较处理
    const html = editor.getHtml();
    const content = editor.getText();
    let flag1 = 0;

    if (jsonArray1.length > 0) {
        flag1 = 1;
        jsonArray1 = JSON.stringify(jsonArray1)
    } else {
        jsonArray1 = ''
    }

    return {content, html, flag1, jsonArray1}
}

var dealHightLight = {
    hadHighArray: [],
    hadUnderlineArray: [],
    hadAddHighlightArray: [],
    hadAnnotationArray: [],
    excute(question, element) {
        dealHightLight.hadHighArray = []
        dealHightLight.hadUnderlineArray = []
        dealHightLight.hadAddHighlightArray = []
        dealHightLight.hadAnnotationArray = []
        if (question.highlightFlag === 1) {
            this.preElement(question.highlightJsonArray, 'highlight', element)
        }
        if (question.addHighlightFlag === 1) {
            this.preElement(question.addHighlightJsonArray, 'addHighlight', element)
        }
        if (question.underlineFlag === 1) {
            this.preElement(question.underlineJsonArray, 'underline', element)
        }
        if (question.annotationFlag === 1) {
            this.preElement(question.annotationJsonArray, 'annotation', element)
        }
    },
    preElement(jsonArray, type, element) {
        let array = JSON.parse(jsonArray);
        //去重
        array = array.filter((obj, index, self) =>
            index === self.findIndex(other => JSON.stringify(obj) === JSON.stringify(other)) // 去除重复对象
        );
        for (let string of array) {
            if (type === 'addHighlight') {
                this.traverseAddHighlight(element, string, type)
            } else if (type === 'annotation') {
                this.traverseAnnotation(element, string, type)

            } else {
                this.traverse(element, string, type)
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
                } else if (type === 'highlight' && dealHightLight.hadHighArray.indexOf(keyword) < 0) {
                    dealHightLight.hadHighArray.push(keyword);
                    $(this).html($(this).html().replace(new RegExp(keyword, "i"), "<span class='highlight'>" + keyword + "</span>"));
                }
            }
        });
    }, traverseAddHighlight(node, keyword, type) {
        const flag = keyword.flag;
        const word = keyword.word;
        $(node).find("*").each(function () {
            // 判断当前节点是否包含关键词
            if ($(this).text().indexOf(word) >= 0 || $(this).text().indexOf(word)) {
                // 如果包含，则将关键词用<span>标签包裹起来，并添加样式
                if (flag === 'addHighlight') {
                    $(this).html($(this).html().replace(new RegExp(word, "i"), "<span class='addHighlight'>" + word + "</span>"));

                } else {
                    $(this).html($(this).html().replace(new RegExp(word, "i"), "<span class='highlight'>" + word + "</span>"));
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
