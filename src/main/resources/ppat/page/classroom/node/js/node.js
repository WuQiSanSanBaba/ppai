function loadNodeDataEcharts() {
    return $axios({
        url: `/classroom/echarts/getEcharts`,
        method: 'get'
    })
}

function generateChart(data, this_) {
    var chart = echarts.init(document.getElementById('main'));
    var option = {
        title: {
            text: '关系图'
        },
        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series: [{
            type: 'graph',
            layout: 'force',
            label: {
                show: true,
                position: 'inside' // 设置标签位置
            },
            symbolSize: 50,
            roam: true,
            edgeSymbol: ['none', 'arrow'],
            edgeSymbolSize: [4, 10],
            edgeLabel: {
                normal: {
                    textStyle: {
                        fontSize: 20
                    }
                }
            },
            data: data.nodeList,
            links: data.linkList,
            emphasis: {
                focus: 'adjacency',
                lineStyle: {
                    width: 10
                }
            },
            force: {
                repulsion: 1000
            }
        }]
    };
    chart.on('click', function (params) {
        // 判断是否是节点
        if (params.data.type === 'root') {
            // 获取节点名字
            var nodeName = params.data.name;
            // 执行节点点击事件
            this_.$message.success('双击进去问题详情：标题：' + nodeName);
        }
    });
    chart.on('dblclick', function (params) {
        // 判断是否是节点
        if (params.data.type === 'root') {
            // 获取节点名字
            const questionId = params.data.questionId;
            // 执行节点点击事件
            if (window.parent.iframUrlChange) {
                window.parent.iframUrlChange('question',  '/ppat/page/classroom/question/question.html?questionId=' + questionId);
            } else {
                window.location.href = '/ppat/page/classroom/question/question.html?questionId=' + questionId
            }
        }
    });
    chart.setOption(option);
}