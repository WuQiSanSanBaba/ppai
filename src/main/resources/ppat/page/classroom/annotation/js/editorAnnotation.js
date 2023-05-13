async function  saveannotationsEditor(){
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
        const checkBox$= $('[name="'+item.categorize+'"][type="checkbox"]')
        let annotation = {
            questionId: questionId,
            questionTitle: questionTitle,
            annotationWord: annotationWord,
            annotationTitle: item.annotationTitle,
            content: text,
            html: item.editor.getHtml(),
            categorize: item.categorize,
            annotationId: checkBox$.prop('annotationId'),
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

function getAnnotationsByAnnotationGroupId(annotationGroupId){
    return $axios({
        url: '/classroom/annotation/getAnnotationsByAnnotationGroupId/'+annotationGroupId,
        method: 'get'
    })
}

/**
 * 循环加载富文本编辑器
 * @param annotationList 根据questionId查询出来的 annotation数组
 * @param categorizes 新建课堂的分类
 */
function loadEdit(annotationList,categorizes){
    //1.遍历注释分组
    annotationList.map(item=>{
        //1.1如果注释分组存在分类列表里，证明已经存在
        if(categorizes.indexOf(item.categorize)>-1){
            //1.2获取对应展示和隐藏分类的多选框
            const checkBox$= $('[name="'+item.categorize+'"][type="checkbox"]')
            //1.3多选框为选择
            //1.4触发点击事件创建富文本编辑器
            checkBox$.click();
            checkBox$.prop('annotationId',item.annotationId)

        }

        editorContainerArray.map(res=>{
            if (res.categorize===item.categorize){
                editor=res.editor
            }
            //1.5获取editor对象并赋值
            editor.setHtml(item.html);
        })
    })
}