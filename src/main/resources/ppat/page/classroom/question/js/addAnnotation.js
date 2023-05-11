async function saveannotations() {
    let classroomPart
    const classroomPartJson = window.localStorage.getItem("classroomPart");
    if (classroomPartJson) {
        classroomPart = JSON.parse(window.localStorage.getItem("classroomPart"));
    }else {
        new $.zui.Messager('提示消息：你没有加入小组，无法操作', {
            type: 'danger' // 定义颜色主题
        }).show();
        return ;
    }
    $('#myModal').modal('hide')
    const annotations = []
    if (editorContainerArray==null || editorContainerArray.length===0) {
        new $.zui.Messager('提示消息：你还没有注释请先注释', {
            type: 'danger' // 定义颜色主题
        }).show();
        return ;
    }
    for (let item of editorContainerArray) {
        const text= item.editor.getText();
        let annotation = {
            questionId: questionId,
            questionTitle: questionTitle,
            annotationWord: annotationWord,
            annotationTitle: item.annotationTitle,
            content: text,
            html: item.editor.getHtml(),
        }
        await analizyQuestion(text,item.editor).then(res=>{
            annotation.jsonArray1=res.jsonArray1
            annotation.flag1=res.flag1
            annotation.partId=res.partId
            annotation.groupId=res.groupId
            annotation.userId=res.userId
            annotation.userName=res.userName
            annotation.subjectId=res.subjectId
            annotation.subjectName=res.subjectName
            annotation.classroomId=res.classroomId
        });
        annotations.push(annotation)
    }
    const data=JSON.stringify(annotations)
    $.ajax({
        url: '/classroom/annotation/addAnnotation',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: data,
        success: function(data) {
            // 请求成功后的逻辑处理，数据为 JSON 格式
            console.log(data);
        },
        error: function(error) {
            // 请求失败后的逻辑处理
            console.log(error);
        }
    });
}

function loadPage() {
    const categorizes_JSON = JSON.parse(window.localStorage.getItem("classroom")).categorizes;
    const categorizes = JSON.parse(categorizes_JSON);

    for (let i = 0; i < categorizes.length; i++) {
        // 创建多选框元素
        var checkbox = $('<input/>', {
            type: 'checkbox',
            id: 'checkbox' + i,
            value: categorizes[i],
            name: categorizes[i]
        });
        // 创建多选框文本元素
        var label = $('<label/>', {
            text: categorizes[i],
            for: 'checkbox' + i,
        });
        // 将多选框和文本添加到文档中
        $('#checkbox-container').append(checkbox).append(label);
        // 为多选框添加点击事件
        checkbox.on('click', function () {
            console.log(i)
            console.log($(this).prop('checked') + " - " + $(this).next('label').text());
            //创建富文本编辑器
            if ($(this).prop('checked') === true && $('#editor_container' + i).length === 0) {
                //创建富文本编辑器容器
                var wrapper = $('<div/>', {
                    'class': 'editor—wrapper',
                    'id': 'editor—wrapper' + i
                });
                $('#editor-containers').append(wrapper);
                //标题
                const title = categorizes[i] + $('#highlight-word').text();
                var title_container = $('<h1/>', {
                    text: title,
                    class: 'annotation-title'
                })
                wrapper.append(title_container);
                //编辑器容器
                var container_toolbar = $('<div/>', {
                    'class': 'toolbar-container',
                    'id': 'container_toolbar' + i
                });
                //编辑器菜单
                var editor_container = $('<div/>', {
                    'class': 'editor_container',
                    'id': 'editor_container' + i
                });
                wrapper.append(container_toolbar);
                wrapper.append(editor_container);
                const editor = loadEditor('editor_container' + i, 'container_toolbar' + i);
                const editorContainer = {
                    'id': 'editorContainer' + i,
                    'editor': editor,
                    'annotationTitle': categorizes[i] + questionTitle
                }
                editorContainerArray.push(editorContainer);
            } else if ($(this).prop('checked') === false && $('#editor_container' + i).length > 0) {
                editorContainerArray = editorContainerArray.filter(function (e) {
                    return e.id !== 'editorContainer' + i;
                })
                $('#editor—wrapper' + i).remove();
            }
        });

    }

}

function loadTitle() {
    //获取参数 采用urlCoder 编码 防止乱码
    const urlParams = new URLSearchParams(window.location.search); // 获取URL的查询参数部分
    questionId =urlParams.get('questionId'); // 获取param1的值
    annotationWord = decodeURI(urlParams.get('annotationWord')); // 获取param2的值
    questionTitle =decodeURI( urlParams.get('questionTitle')); // 获取param2的值
    $('#question-title').text(questionTitle);
    $('#highlight-word').text(annotationWord);
    console.log(questionId, annotationWord,questionTitle); // 输出value1 value2
}


function loadEditor(containerId, toobarId) {
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
        selector: '#' + containerId, html: '<p><br></p>', config: editorConfig, mode: 'simple' // or 'simple'
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
        editor, selector: '#' + toobarId, config: toolbarConfig, mode: 'default', // or 'simple'
    })
    //加载完编辑器后

    return editor;
}
