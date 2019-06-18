/**
 *websocket 连接 
 */

var Chat = {};
Chat.socket = null;
//根据浏览器创建相应的websocket实例，并向服务器发起连接
Chat.connect = (function(host) {
    if ('WebSocket' in window) {
        Chat.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Chat.socket = new MozWebSocket(host);
    } else {
        console.log('Error: WebSocket is not supported by this browser.');
        return;
    }
	//连接服务器后调用
	Chat.socket.onopen = function () {
    console.log('Info: WebSocket connection opened.');
   
	};
	//断开连接后调用
	Chat.socket.onclose=function(){
	console.log('WebSocket closed.');
	};
	//监听服务器消息，服务器向客户端推送消息的时候，websocket会调用onmessage    方法
    Chat.socket.onmessage = function (message) {
        console.log(message.data);
        var oldValue = $('.user>a>b').html();
        $('.user>a>b').html(parseInt(oldValue)+1);
    };

});
//初始化
Chat.initialize = function() {
    if (window.location.protocol == 'http:') {
        Chat.connect('ws://' + window.location.host + '/shiyanshi/infoWebSocketServer.do');
    } else {
        Chat.connect('wss://' + window.location.host + '/shiyanshi/infoWebSocketServer.do');
    }
};

//发送消息
Chat.sendMessage = (function() {
   console.log('client send message to server。');
});




