async function saveannotations() {
    let classroomPart
    const classroomPartJson = window.localStorage.getItem("classroomPart");
    if (classroomPartJson) {
        classroomPart = JSON.parse(window.localStorage.getItem("classroomPart"));
    } else {
        new $.zui.Messager('提示消息：你没有加入小组，无法操作', {
            type: 'danger' // 定义颜色主题
        }).show();
        return;
    }
    $('#myModal').modal('hide')
    const annotations = []
    if (editorContainerArray == null || editorContainerArray.length === 0) {
        new $.zui.Messager('提示消息：你还没有注释请先注释', {
            type: 'danger' // 定义颜色主题
        }).show();
        return;
    }
    for (let item of editorContainerArray) {
        const text = item.editor.getText();
        let annotation = {
            questionId: questionId,
            questionTitle: questionTitle,
            annotationWord: annotationWord,
            annotationTitle: item.annotationTitle,
            content: text,
            html: item.editor.getHtml(),
            categorize: item.categorize,
        }
        await analizyQuestion(text, item.editor).then(res => {
            annotation.jsonArray1 = res.jsonArray1
            annotation.flag1 = res.flag1
            annotation.partId = res.partId
            annotation.groupId = res.groupId
            annotation.userId = res.userId
            annotation.userName = res.userName
            annotation.subjectId = res.subjectId
            annotation.subjectName = res.subjectName
            annotation.classroomId = res.classroomId
        });
        annotations.push(annotation)
    }
    const data = JSON.stringify(annotations)
    $.ajax({
        url: '/classroom/annotation/addAnnotation',
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



