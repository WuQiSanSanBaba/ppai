async function saveannotationsEditor() {
    let classroomPart
    //获取主题
    await loadClassroom().then(result => {
        classroomPart = result.data.classroomPart
    });
    if (classroomPart == null) {
        new $.zui.Messager('提示消息：获取课堂失败', {
            type: 'danger' // 定义颜色主题
        }).show();
    }
    $('#myModal').modal('hide')
    if (editorContainerArray == null || editorContainerArray.length === 0) {
        new $.zui.Messager('提示消息：你还没有注释请先注释', {
            type: 'danger' // 定义颜色主题
        }).show();
        return;
    }
    let annotationDto = {}
    let annotation = {
        questionId: questionId,
        questionTitle: questionTitle,
        annotationId: annotationId,
        annotationWord: annotationWord,
        partId: classroomPart.partId,
        groupId: classroomPart.groupId,
        userId: classroomPart.userId,
        userName: classroomPart.userName,
        subjectId: classroomPart.subjectId,
        subjectName: classroomPart.subjectName,
        classroomId: classroomPart.classroomId,
        annotationType: 'question',
    }
    const annotationBatchList = []
    let jsonArray = []
    for (let item of editorContainerArray) {
        const text = item.editor.getText();
        const checkBox$ = $('[name="' + item.categorize + '"][type="checkbox"]')
        let annotationBatch = {
            questionId: questionId,
            annotationWord: annotationWord,
            annotationTitle: item.annotationTitle,
            content: text,
            html: item.editor.getHtml(),
            categorize: item.categorize,
            annotationBatchId: checkBox$.prop('annotationBatchId'),
        }
        await analizyQuestion(text, item.editor).then(res => {
            if (res.flag1 === 1) {
                jsonArray.push(...JSON.parse(res.jsonArray1))
            }
            annotationBatch.partId = res.partId
            annotationBatch.groupId = res.groupId
            annotationBatch.classroomId = res.classroomId
        });

        annotationBatchList.push(annotationBatch)
    }
    if (jsonArray.length > 0) {
        const newArray = jsonArray.filter((item, index) => {
            return jsonArray.indexOf(item) === index
        })
        annotation.jsonArray1 = JSON.stringify(newArray)
        annotation.flag1 = 1

    } else {
        annotation.flag1 = 0
    }
    annotationDto.annotation = annotation
    annotationDto.annotationBatchList = annotationBatchList
    const data = JSON.stringify(annotationDto)

    $.ajax({
        url: '/classroom/annotation/updateAnnotation',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: data,
        success: function (data) {
            // 请求成功后的逻辑处理，数据为 JSON 格式
            new $.zui.Messager(data.data, {
                type: 'success' // 定义颜色主题
            }).show();
            window.parent.menuHandle({
                menuId: 'node',
                name: '关系图',
                url: '/ppat/page/classroom/node/node.html'
            }, true);
        },
        error: function (error) {
            // 请求失败后的逻辑处理
            console.log(error);
        }
    });
}

function getAnnotationDtoByAnnotationId(annotationId) {
    return $axios({
        url: '/classroom/annotation/getAnnotationDtoByAnnotationId/' + annotationId,
        method: 'get'
    })
}

/**
 * 循环加载富文本编辑器
 * @param annotationList 根据questionId查询出来的 annotation数组
 * @param categorizes 新建课堂的分类
 */
function loadEdit(annotation, categorizes) {
    const annotationList = annotation.annotationBatchList;
    //1.遍历注释分组
    annotationList.map(item => {
        //1.1如果注释分组存在分类列表里，证明已经存在
        if (categorizes.indexOf(item.categorize) > -1) {
            //1.2获取对应展示和隐藏分类的多选框
            const checkBox$ = $('[name="' + item.categorize + '"][type="checkbox"]')
            //1.3多选框为选择
            //1.4触发点击事件创建富文本编辑器
            checkBox$.click();
            checkBox$.prop('annotationBatchId', item.annotationBatchId)

        }
        editorContainerArray.map(res => {
            if (res.categorize === item.categorize) {
                editor = res.editor
                //1.5获取editor对象并赋值
                editor.setHtml(item.html);
            }

        })
    })
}