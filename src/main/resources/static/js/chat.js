let stompClient;


connect().then( () => {
        setAppropriateStatusConnection();
        joinRoomMessage();
    }
);

function setAppropriateStatusConnection() {
    const opponent = document.getElementById("opponent").textContent;

    if(opponent !== "") {

        const opponentStatus = document.getElementById("opponent-status");
        opponentStatus.style.visibility = "visible";
    }
}

function joinRoomMessage() {

    const connectionInfo = document.getElementById("connection-info");

    if(connectionInfo.textContent === "false") {
        const opponent = document.getElementById("opponent").textContent;

        if (opponent !== "") {

            connectionInfo.textContent = "true";

            const roomNumber = window.location.href.split("/").slice(-1)[0];
            const username = document.getElementById("user").textContent;

            const gameInfo = {"info": "userJoined", "username": username};

            stompClient.send("/game/room/" + roomNumber + "/game", {}, JSON.stringify(gameInfo))
        }
    }

}

function connect() {

    return new Promise((resolve, reject) => {
        let socket = new SockJS("/websocket");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, async function (frame) {

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
    const span = document.createElement("span");
    const message = document.createElement("span");

    message.textContent = messageOutput.from + ": " + messageOutput.content;

    span.appendChild(message)
    span.appendChild(document.createElement("hr"));

    chat.appendChild(span);
}

function startGame(username) {

    const roomNumber = window.location.href.split("/").slice(-1)[0];
    let gameInfo = {"info": "ready", "username": username};

    stompClient.send("/game/room/" + roomNumber + "/game", {}, JSON.stringify(gameInfo));
}

function processGameInfo(gameInfo) {

    if(gameInfo.info === "userJoined")
        setOpponentUsername(gameInfo.username);

    if(gameInfo.info === "ready" || gameInfo.info === "start")
        setReadyConfig();

    if(gameInfo.info === "start") {
        setStartConfig()
        console.log("the game has started");
    }
}

function setReadyConfig() {

    const startButton = document.getElementById("startButton");
    startButton.style.display = "none";


}

function setStartConfig() {

    const chatButton = document.getElementById("chatButton");
    chatButton.disabled = false;
}

function setOpponentUsername(username) {

    const opponent = document.getElementById("opponent");
    const opponentStatus = document.getElementById("opponent-status");
    const connectionInfo = document.getElementById("connection-info");
    const roomStatus = document.getElementById("room-status");

    if(opponent.textContent === "") {
        opponent.textContent = username;
        opponentStatus.style.visibility = "visible";
        connectionInfo.textContent = "true";
        roomStatus.textContent = "WAITING";
    }
}