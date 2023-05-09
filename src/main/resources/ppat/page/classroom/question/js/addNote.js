


function loadPage(){

}


function loadEditor(containerId,toobarId) {
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
        selector: '#'+containerId, html: '<p><br></p>', config: editorConfig, mode: 'simple' // or 'simple'
    })

    const toolbarConfig = {}

    toolbarConfig.toolbarKeys = [// 菜单 key
        "headerSelect", // 分割线
        '|', // 菜单 key
        'bold', 'italic', "color", "fontSize",
        "uploadImage"

    ]

    const toolbar = createToolbar({
        editor, selector: '#'+toobarId, config: toolbarConfig, mode: 'default', // or 'simple'
    })
    //加载完编辑器后

    return editor;
}
