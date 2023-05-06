const dealHightLight = {
    excute(question) {
        if (question.highLightFlag === 1) {
            this.preElement(question.highLightJsonArray,'highlight')
        }
        if (question.highLightFlagAdd ===1){
            this.preElement(question.highLightJsonArrayAdd,'blue-underline')
        }
        if (question.underlineFlag === 1) {
            this.preElement(question.underlineJsonArray,'highlight-add')
        }
    },
    preElement(jsonArray,type) {
        let array = JSON.parse(jsonArray);
        //去重
        array = array.filter((obj, index, self) =>
            index === self.findIndex(other => JSON.stringify(obj) === JSON.stringify(other)) // 去除重复对象
        );
        for (let string in array) {
            for (let element in string) {
                this.elementChange(element,type)
            }
        }
    },
    elementChange(keyword, type) {
        // 创建正则表达式
        var regexStr = '(\\b\\S*' + keyword.split('').join('\\S*') + '\\S*\\b)';
        var regex = new RegExp(regexStr, 'g');
        element = document.getElementById('question-content')
        // 遍历所有文本节点，查找并高亮匹配到的文本
        var textNodes = this.getTextNodesIn(element);
        for (var i = 0; i < textNodes.length; i++) {
            var textNode = textNodes[i];
            var text = textNode.nodeValue;
            // 匹配文本内容
            var match = text.match(regex);
            if (match) {
                // 创建一个包含匹配到的文本的文本节点
                var newNode = document.createElement('span');
                if (type === 'hightLight') {
                    newNode.innerHTML = match[0].replace(new RegExp(keyword, 'g'), '<span class="highlight">$&</span>');
                } else if (type === 'note') {
                    newNode.innerHTML = match[0].replace(new RegExp(keyword, 'g'), '<u class="blue-underline">$&</u>');
                } else if (type === 'addHighLight') {
                    newNode.innerHTML = match[0].replace(new RegExp(keyword, 'g'), '<span class="highlight-add">$&</span>');
                }
                // 插入新节点，替换原文本节点
                textNode.parentNode.insertBefore(newNode, textNode);
                textNode.parentNode.removeChild(textNode);
            }
        }
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


function addHighLight_(questionId,newArrayString){
    return $axios({
        url: '/classroom/question/addHighlight',
        method: 'post',
        data: {questionId:questionId,newArrayString:newArrayString}
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

