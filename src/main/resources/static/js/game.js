let stompClient2;
const colour = document.getElementById('colour-info').textContent === 'true' ? 'white' : 'black';
const colourBoolean = document.getElementById('colour-info').textContent === 'true';
const board = document.getElementById("board");
let colourTour = document.getElementById('is-white-tour').textContent === 'true' ? 'white' : 'black';

let arrayOfListenerReferences = [];
let arrayOfFieldReferences = [];
let arrayOfListenerReferences2 = [];
let arrayOfFieldReferences2 = [];

let allPossibleMoves = [];

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

        const colourInfo = document.getElementById('colour-info').textContent;
        const move = {"colour": colourInfo, "from": null, "to": null};

        if(colour === colourTour)
            stompClient2.send("/game/room/" + roomNumber + "/gameMoves", {}, JSON.stringify(move));

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

                const referenceToListener2 = () => showPossibleMovesForPiece(i, j)
                const referenceToField2 = pieceElement;

                pieceElement.addEventListener("click", referenceToListener2)

                arrayOfListenerReferences2.push(referenceToListener2);
                arrayOfFieldReferences2.push(referenceToField2);
            }
        }
    }
}


function showPossibleMovesForPiece(i, j) {

    if(colourTour !== colour)
        return;

    clearBoard()

    const rows = board.children;
    rows[i].children[j].style.backgroundColor = "gray";

    let toArray;
    let from;
    for(let moves of allPossibleMoves) {
        if (moves.from.row == i && moves.from.col == j) {
            from = moves.from;
            toArray = moves.to;
        }
    }

    highlightPossibleMoves(from, toArray);
}

function clearBoard() {

    for(let i = 0; i < arrayOfFieldReferences.length; i++)
        arrayOfFieldReferences[i].removeEventListener('click', arrayOfListenerReferences[i]);

    arrayOfListenerReferences = [];
    arrayOfFieldReferences = [];

    const rows = board.children;

    // clear highlight
    for(let i = 0; i < 8; i++)
        for(let j = 0; j < 8; j++) {

            const element = rows[i].children[j];

            if (element.style.backgroundColor === 'gray') {

                if ((i + j) % 2 === 0)
                    element.style.backgroundColor = '#CAD2C5';
                else
                    element.style.backgroundColor = '#52796F';
            }

            else if(element.style.borderColor !== '')
                element.style.border = '';

            else if(element.children.length !== 0 && element.children[0].classList.contains('rounded-circle'))
                element.removeChild(element.children[0]);
        }

}

function highlightPossibleMoves(from, toArray) {

    const rows = board.children;

    for(let to of toArray) {

        const element = rows[to.row].children[to.col];

        const referenceToField = element;
        const referenceToListener = () => move(from, to);

        element.addEventListener('click', referenceToListener);
        arrayOfListenerReferences.push(referenceToListener);
        arrayOfFieldReferences.push(referenceToField);

        if(element.children.length === 1)
            element.style.border = "2px solid red";
        else {
            const circle = document.createElement('div');
            circle.style.width = '25%';
            circle.style.height = '25%';
            circle.style.margin = "auto";
            circle.style.backgroundColor = "gray";
            circle.classList.add('rounded-circle');
            element.appendChild(circle);
        }

    }
}

function move(from, to) {

    const rows = board.children;

    clearBoard();

    const fromElement = rows[from.row].children[from.col];
    const toElement = rows[to.row].children[to.col];

    if(toElement.children.length !== 0)
        toElement.removeChild(toElement.children[0]);

    toElement.appendChild(fromElement.children[0]);

    let roomNumber = window.location.href.split("/").slice(-1)[0];

    const colourInfo = document.getElementById('colour-info').textContent;
    const move = {"colour": colourInfo, "from": from, "to": to};

    if(colour === colourTour)
        stompClient2.send("/game/room/" + roomNumber + "/gameMoves", {}, JSON.stringify(move));

    changeTour();
}

function processGameMoves(theAllPossibleMoves) {

    // change the second condition to check the if it is first move

    if(theAllPossibleMoves.colour === colourBoolean && theAllPossibleMoves.lastMove.from == null) {

        console.log("FIRST");

        allPossibleMoves = theAllPossibleMoves.possibleMoves;
    }

    if(theAllPossibleMoves.colour !== colourBoolean && theAllPossibleMoves.lastMove.from !== null) {

        console.log("download move");

        const rows = board.children;

        const from = theAllPossibleMoves.lastMove.from;
        const to = theAllPossibleMoves.lastMove.to;

        const fromElement = rows[from.row].children[from.col];
        const toElement = rows[to.row].children[to.col];

        if(toElement.children.length !== 0)
            toElement.removeChild(toElement.children[0]);

        toElement.appendChild(fromElement.children[0]);


        allPossibleMoves = theAllPossibleMoves.possibleMoves;

        changeTour()
    }
}

function changeTour() {

    for(let i = 0; i < arrayOfFieldReferences2.length; i++)
        arrayOfFieldReferences2[i].removeEventListener('click', arrayOfListenerReferences2[i]);

    arrayOfListenerReferences2 = [];
    arrayOfFieldReferences2 = [];

    colourTour = colourTour === 'white' ? 'black' : 'white';

    addPossibilityToMovePieces();
}