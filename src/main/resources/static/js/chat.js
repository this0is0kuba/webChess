let stompClient;

async function connect() {

    return new Promise((resolve, reject) => {
        let socket = new SockJS("/websocket");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {

            console.log("connect: " + frame);
            let roomNumber = window.location.href.split("/").slice(-1)[0];


            stompClient.subscribe("/topic/room/" + roomNumber + "/chat", function (messageOutput) {
                showMessageOutput(JSON.parse(messageOutput.body));
            })

            stompClient.subscribe("/topic/room/" + roomNumber + "/game", function (gameInfo) {
                processGameInfo(JSON.parse(gameInfo.body));
            })

            resolve();
        });
    });
}

function sendMessage(username) {

    const roomNumber = window.location.href.split("/").slice(-1)[0];
    let messageContent = document.getElementById("messageContent");

    const content = messageContent.value;
    const message = {"from": username, "content": content};

    messageContent.value = "";

    stompClient.send("/game/room/" + roomNumber + "/chat", {},
        JSON.stringify(message)
        );
}

function showMessageOutput(messageOutput) {

    const chat = document.getElementById("chat");
    const message = document.createElement("span");

    message.textContent = messageOutput.from + ": " + messageOutput.content;

    chat.appendChild(message);
    chat.appendChild(document.createElement("hr"));
}

function startGame() {

    connect()
        .then( () => {
            const roomNumber = window.location.href.split("/").slice(-1)[0];
            let gameInfo = {"info": "start"};

            stompClient.send("/game/room/" + roomNumber + "/game", {}, JSON.stringify(gameInfo));
        });
}

function processGameInfo(gameInfo) {

    if(gameInfo.info === "start");
        console.log("the game has started");
}