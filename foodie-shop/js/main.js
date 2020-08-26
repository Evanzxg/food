//创建全局GoEasy对象
Vue.prototype.$goEasy = new GoEasy({
    host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
    appkey: "BC-7e86cb1cbc6e4381b5deb649d3261103", //替换为您的应用appkey
    onConnected: function() {
        console.log('连接成功！')
    },
    onDisconnected: function() {
        console.log('连接断开！')
    },
    onConnectFailed: function(error) {
        console.log('连接失败或错误！')
    }
});
