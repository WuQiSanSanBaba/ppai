async function saveannotations() {
    let classroomPart
    let subject
    //获取主题
    await loadClassroom().then(result => {
        classroomPart = result.data.classroomPart
    });
    if (classroomPart==null) {
        new $.zui.Messager('提示消息：获取课堂失败', {
            type: 'danger' // 定义颜色主题
        }).show();
    }
    await getSubjectById(classroomPart.subjectId).then(res => {
        subject = res.data
    })
    $('#myModal').modal('hide')
    if (editorContainerArray == null || editorContainerArray.length === 0) {
        new $.zui.Messager('提示消息：你还没有注释请先注释', {
            type: 'danger' // 定义颜色主题
        }).show();
        return;
    }
    let annotationDto={}
    let annotation={
        questionId: questionId,
        questionTitle: questionTitle,
        annotationWord: annotationWord,
        partId : classroomPart.partId,
        groupId : classroomPart.groupId,
        subjectId : classroomPart.subjectId,
        subjectName : classroomPart.subjectName,
        classroomId : classroomPart.classroomId,
        annotationType : 'question',
    }
    const annotationBatchList = []
    let jsonArray=[]
    for (let item of editorContainerArray) {
        const text = item.editor.getText();
        let annotationBatch = {
            questionId: questionId,
            annotationWord: annotationWord,
            annotationTitle: item.annotationTitle,
            content: text,
            html: item.editor.getHtml(),
            categorize: item.categorize,
        }
        await analizyQuestion(text, item.editor,classroomPart,subject).then(res => {
            if (res.flag1===1){
                jsonArray.push(...JSON.parse(res.jsonArray1))
            }
            annotationBatch.partId = res.partId
            annotationBatch.groupId = res.groupId
            annotationBatch.classroomId = res.classroomId
        });
        annotationBatchList.push(annotationBatch)
    }
    if (jsonArray.length>0){
        const newArray=  jsonArray.filter((item,index)=>{
            return jsonArray.indexOf(item)===index
        })
        annotation.jsonArray1=JSON.stringify(newArray)
        annotation.flag1=1

    }else {
        annotation.flag1=0
    }
    annotationDto.annotation=annotation
    annotationDto.annotationBatchList=annotationBatchList
    const data = JSON.stringify(annotationDto)
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



