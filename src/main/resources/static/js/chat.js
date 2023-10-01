let stompClientChat;
connect();

function connect() {

    let socket = new SockJS("/game/room");
    stompClientChat = Stomp.over(socket);

    stompClientChat.connect({}, function (frame) {

        console.log("connect: " + frame);
        let roomNumber = window.location.href.split("/").slice(-1)[0];
        stompClientChat.subscribe('/topic/room/' + roomNumber + '/chat', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        })
    });
}

    function sendMessage(username) {

        const roomNumber = window.location.href.split("/").slice(-1)[0];
        let messageContent = document.getElementById("messageContent");

        const content = messageContent.value;
        const message = {"from": username, "content": content};

        messageContent.value = "";

        stompClientChat.send("/game/room/" + roomNumber + "/chat", {},
            JSON.stringify(message)
            );
    }

    function startGame() {

    }

    function showMessageOutput(messageOutput) {

        const chat = document.getElementById("chat");
        const message = document.createElement("span");

        message.textContent = messageOutput.from + ": " + messageOutput.content;

        chat.appendChild(message);
        chat.appendChild(document.createElement("hr"));
    }