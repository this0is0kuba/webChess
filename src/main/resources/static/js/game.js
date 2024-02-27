let stompClient2;
const colour = document.getElementById('colour-info').textContent === 'true' ? 'white' : 'black';
const board = document.getElementById("board");
let allPossibleMoves;

addListener();

function addListener() {

    const roomStatus = document.getElementById("room-status");

    if(checkIfGameStarted(roomStatus.textContent)) {
        setConnection();
        addPossibilityToMovePieces();
    }

    else {

        const config = { attributes: true, subtree: true, childList: true};

        const callback = (mutationList, observer) => {
            for (let mutation of mutationList) {

                if(checkIfGameStarted(mutation.target.textContent)) {
                    setConnection();
                    addPossibilityToMovePieces();
                }
            }
        };

        const observer = new MutationObserver(callback);
        observer.observe(roomStatus, config);
    }

}

function setConnection() {

    let socket = new SockJS("http://localhost:8080/websocket");
    stompClient2 = Stomp.over(socket);

    stompClient2.connect({}, function (frame) {

        console.log("connect: " + frame);
        let roomNumber = window.location.href.split("/").slice(-1)[0];

        stompClient2.subscribe("topic/room/" + roomNumber + "/gameMoves", function (move) {
            processGameMoves(JSON.parse(move.body));
        })

        stompClient2.subscribe("topic/room/" + roomNumber + "/firstMoves", function (move) {
            processFirstMoves(JSON.parse(move.body));
        })

        const username = document.getElementById("user").textContent;
        const gameInfo = {"info": `${colour}`, "username": username};

        stompClient2.send("/game/room/" + roomNumber + "/firstMoves", {}, JSON.stringify(gameInfo));
    })
}

function checkIfGameStarted(text) {

    return text === "PLAYING";
}

function addPossibilityToMovePieces() {

    const rows = board.children;

    for(let i = 0; i < rows.length; i++) {

        let row = rows[i];

        for (let j = 0; j < row.children.length; j++) {

            let pieceElement = row.children[j];

            if (pieceElement.children.length === 1 && pieceElement.children[0].src.includes(`${colour}`)) {

                pieceElement.addEventListener("click", () => showPossibleMovesForPiece(i, j))
            }
        }
    }
}

// TODO
// iterate through all possible moves and display them. Highlight the clicked field and turn off
// highlight of the other clicked fields (use allPossibleMoves)
function showPossibleMovesForPiece(i, j) {

    const rows = board.children;

    rows[i].children[j].style.backgroundColor = "gray";

    console.log(i);
}

function processGameMoves() {
    console.log("moveee");
}

function processFirstMoves(theAllPossibleMoves) {
    allPossibleMoves = theAllPossibleMoves;
}