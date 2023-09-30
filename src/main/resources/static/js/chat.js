let stompClient;
connect();

function connect() {

    let socket = new SockJS("/game/room"); //add /{roomNumber} perhaps
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        console.log("connect: " + frame);
        let roomNumber = window.location.href.split("/").slice(-1)[0];
        stompClient.subscribe('/topic/room/' + roomNumber + '/chat', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        })
    });
}

    function sendMessage() {

        const roomNumber = window.location.href.split("/").slice(-1)[0];
        let messageContent = document.getElementById("messageContent");

        const content = messageContent.value;
        const message = {"from": "Mirek", "content": content};

        messageContent.value = "";

        stompClient.send("/game/room/" + roomNumber + "/chat", {},
            JSON.stringify(message)
            );
    }

    function showMessageOutput(messageOutput) {

        const chat = document.getElementById("chat");
        const message = document.createElement("p");

        message.textContent = messageOutput.from + ": " + messageOutput.content;
        chat.appendChild(message);
    }