const dealHightLight = {
    excute(question,element) {
        if (question.highlightFlag === 1) {
             this.preElement(question.highlightJsonArray, 'highLight',element)
        }
        if (question.addhighlightFlag === 1) {
             this.preElement(question.addhighlightJsonArray, 'highLight-add',element)
        }
        if (question.underlineFlag === 1) {
             this.preElement(question.underlineJsonArray, 'underline',element)
        }
    },
    preElement(jsonArray, type,element) {
        let array = JSON.parse(jsonArray);
        //去重
        array = array.filter((obj, index, self) =>
            index === self.findIndex(other => JSON.stringify(obj) === JSON.stringify(other)) // 去除重复对象
        );
        for (let string of array) {
            for (let element_t of string) {
                 this.traverse(element,element_t,type)
            }
        }
    },

    traverse(node, keyword,type) {
    const stack = [node];
    while (stack.length > 0) {
        const curNode = stack.pop();
        // 遍历当前节点的孩子节点
        for (let i = curNode.childNodes.length - 1; i >= 0; i--) {
            const child = curNode.childNodes[i];
            // 如果孩子节点是元素节点，则插入到栈中
            if (child.nodeType === Node.ELEMENT_NODE) {
                stack.push(child);
            }
            // 如果孩子节点是文本节点，并且它的内容包含关键字，则创建并插入包含关键字的节点
            else if (child.nodeType === Node.TEXT_NODE && child.textContent.includes(keyword)) {
                const parts = child.textContent.split(keyword);
                const parentNode = child.parentNode;
                for (let j = 0; j < parts.length; j++) {
                    const part = parts[j];
                    parentNode.insertBefore(document.createTextNode(part), child);
                    if (j < parts.length - 1) {
                        const keywordNode = document.createElement('span');
                        keywordNode.textContent = keyword;
                        if (type==='underline'){
                            keywordNode.classList.add('underline');
                        }else if (type==='highLight-add'){
                            keywordNode.classList.add('highLight-add');
                        }else if (type==='highLight'){
                            keywordNode.classList.add('highLight');
                        }
                        parentNode.insertBefore(keywordNode, child);
                    }
                }
                // 移除当前节点
                parentNode.removeChild(child);
            }
        }
    }
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

