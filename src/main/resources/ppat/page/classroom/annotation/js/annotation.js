function getAnnotationListByAnnotationId (pageMap) {
    return $axios({
        url: `/classroom/annotation/getAnnotationListByAnnotationId`,
        method: 'post',
        data:pageMap
    })
}

function loadAnnotationContainer (data){
    let container = $('#annotationContainer');
    data.map(item=>{
        const wrapper = $('<div/>', {
            'class': 'editor—wrapper',
        });
        container.append(wrapper);
        const title_container = $('<h3/>', {
            text: item.annotationTitle,
            class: 'annotation-title'
        })
        wrapper.append(title_container);
        const richTextDiv = $('<div/>', {
        });
        const richText$=$(item.html).attr('id', item.annotationId).addClass('richText')
        richTextDiv.append(richText$)
        wrapper.append(richTextDiv)
        dealHightLight.excute(item,richTextDiv)
    })
}