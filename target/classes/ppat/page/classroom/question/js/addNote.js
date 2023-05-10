function loadPage() {
    //const categorizes = JSON.parse(window.localStorage.getItem("classroom"));
    const categorizes=['蔡徐坤','王子异','Administ']
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
        $('body').append(checkbox).append(label).append('<br/>');
        // 为多选框添加点击事件
        checkbox.on('click', function () {
            console.log($(this).prop('checked') + " - " + $(this).next('label').text());
        });
        //创建富文本编辑器容器
        var wrapper = $('<div/>', {
            'class': 'myDiv',
            'text': 'New div ' + i,
            'id': 'editor—wrapper' + i
        });
        $('#editor-containers').append(wrapper);
        var editor_container = $('<div/>', {
            'class': 'myDiv',
            'text': 'editor_container' + i,
            'id': 'editor_container' + i
        });
        var container_toolbar = $('<div/>', {
            'class': 'myDiv',
            'text': 'container_toolbar' + i,
            'id': 'container_toolbar' + i
        });
        wrapper.append(editor_container);
        wrapper.append(container_toolbar);
    }

}

function loadTitle() {
    //获取参数 采用urlCoder 编码 防止乱码
    const urlParams = new URLSearchParams(window.location.search); // 获取URL的查询参数部分
    const questionId = urlParams.get('questionId'); // 获取param1的值
    const nodeWord = urlParams.get('nodeWord'); // 获取param2的值
    const questionTitle = urlParams.get('questionTitle'); // 获取param2的值
    console.log(questionId, nodeWord); // 输出value1 value2
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
        '|', // 菜单 key
        'bold', 'italic', "color", "fontSize",
        "uploadImage"

    ]

    const toolbar = createToolbar({
        editor, selector: '#' + toobarId, config: toolbarConfig, mode: 'default', // or 'simple'
    })
    //加载完编辑器后

    return editor;
}
