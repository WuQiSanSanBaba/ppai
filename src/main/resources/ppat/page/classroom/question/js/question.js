const dealHightLight = {
    excute(question,v_html) {
        if (question.highLightFlag === 1) {
            v_html= this.preElement(question.highLightJsonArray, 'highLight',v_html)
        }
        if (question.addHighLightFlag === 1) {
            v_html= this.preElement(question.addHighlightJsonArray, 'highLight-add',v_html)
        }
        if (question.underlineFlag === 1) {
            v_html= this.preElement(question.underlineJsonArray, 'note',v_html)
        }
        return v_html;
    },
    preElement(jsonArray, type,v_html) {
        let array = JSON.parse(jsonArray);
        //去重
        array = array.filter((obj, index, self) =>
            index === self.findIndex(other => JSON.stringify(obj) === JSON.stringify(other)) // 去除重复对象
        );
        for (let string of array) {
            for (let element of string) {
                v_html= this.elementChange(element, type,v_html)
            }
        }
        return v_html;
    },
    elementChange(keyword, type,v_html) {
        // 创建一个包含匹配到的文本的文本节点
        if (type === 'highLight') {
            v_html=v_html.replaceAll(new RegExp(keyword, 'g'), '<span class="highlight_y">'+ keyword + '</span>')
        } else if (type === 'note') {
            v_html=v_html.replaceAll(new RegExp(keyword, 'g'), '<u class="blue-underline">'+ keyword + '</u>')
        } else if (type === 'highLight-add') {
            v_html=v_html.replaceAll(new RegExp(keyword, 'g'), '<span class="highlight-add">'+ keyword + '</span>')
        }
        return v_html;
    },
    getTextNodesIn(element) {
        var nodes = element.childNodes;
        var textNodes = [];
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];
            if (node.nodeType === Node.TEXT_NODE) {
                textNodes.push(node);
            } else if (node.nodeType === Node.ELEMENT_NODE) {
                textNodes = textNodes.concat(this.getTextNodesIn(node));
            }
        }
        return textNodes;
    }
}


function addHighLight_(questionId, newArrayString) {
    return $axios({
        url: '/classroom/question/addHighlight',
        method: 'post',
        data: {questionId: questionId, newArrayString: newArrayString}
    });
}

function getQuestionBySelf() {
    return $axios({
        url: '/classroom/question/getQuestionBySelf',
        method: 'get'
    });
}

function getQuestionByIdAxios(questionId) {
    return $axios({
        url: `/classroom/question/getQuestionById/${questionId}`,
        method: 'get'
    });
}

