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

    const roomStatus = document.getElementById("room-status");

    if(roomStatus.textContent === "PLAYING") {

        const chatButton = document.getElementById("chatButton");
        const startButton = document.getElementById("startButton");

        chatButton.disabled = false;
        startButton.style.display = "none";
    }

    const startButton = document.getElementById("startButton");
    const userStatus = document.getElementById('user-status').style.backgroundColor;

    if(roomStatus.textContent !== "SEARCHING")
        startButton.style.visibility = "visible";

    if(roomStatus.textContent === "WAITING" && userStatus === 'green')
        startButton.style.visibility = "hidden";
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
        let socket = new SockJS("http://localhost:8080/websocket");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, async function (frame) {

            console.log("connect: " + frame);
            let roomNumber = window.location.href.split("/").slice(-1)[0];


            stompClient.subscribe("topic/room/" + roomNumber + "/chat", function (messageOutput) {
                showMessageOutput(JSON.parse(messageOutput.body));
            })

            stompClient.subscribe("topic/room/" + roomNumber + "/game", function (gameInfo) {
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

    const startButton = document.getElementById("startButton");
    startButton.style.display = "none";

    const userStatusInfo = document.getElementById("user-status");
    userStatusInfo.style.backgroundColor = "green";
}

async function processGameInfo(gameInfo) {

    const username = document.getElementById('user').textContent;

    if(gameInfo.info === "userJoined")
        setOpponentUsername(gameInfo.username);

    if(gameInfo.info === "ready" && gameInfo.username !== username)
        setReadyConfig();

    if(gameInfo.info === "start") {
        setReadyConfig();
        setStartConfig();
        console.log("the game has started");
    }

    console.log(gameInfo.info);

    if(gameInfo.info === "guestLeaved") {
        await new Promise(resolve => setTimeout(resolve, 300));
        location.reload();
    }

    if(gameInfo.info === "ownerLeaved") {
        await new Promise(resolve => setTimeout(resolve, 300));
        window.location.href = 'http://localhost:8080/lobby/';
    }
}

function setReadyConfig() {

    document.getElementById("opponent-status").style.backgroundColor = "green";
}

function setStartConfig() {

    const chatButton = document.getElementById("chatButton");
    chatButton.disabled = false;

    const roomStatus = document.getElementById("room-status");

    roomStatus.textContent = "PLAYING";
}

function setOpponentUsername(username) {

    const opponent = document.getElementById("opponent");
    const opponentStatus = document.getElementById("opponent-status");
    const connectionInfo = document.getElementById("connection-info");
    const roomStatus = document.getElementById("room-status");
    const startButton = document.getElementById("startButton")

    if(opponent.textContent === "") {
        opponent.textContent = username;
        opponentStatus.style.visibility = "visible";
        connectionInfo.textContent = "true";
        roomStatus.textContent = "WAITING";
        startButton.style.visibility = "visible";
    }
}

async function leaveRoom() {

    const roomNumber = window.location.href.split("/").slice(-1)[0];
    const username = document.getElementById("user").textContent;
    const myBody = {
        "username": username,
        "roomNumber": roomNumber
    }

    const gameInfo = {"info": "userLeaved", "username": username};
    stompClient.send("/game/room/" + roomNumber + "/game", {}, JSON.stringify(gameInfo))

    await new Promise(resolve => setTimeout(resolve, 200));

    const url = "http://localhost:8080/game/processLeavingRoom";

    fetch(url, {
        method: "PUT",
        headers: { "Content-Type": "application/json"},
        body: JSON.stringify(myBody)
    })
        .then( response => {
            if(response.status === 200)
                window.location.href = "http://localhost:8080/lobby/";
        });
}