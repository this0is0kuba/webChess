
function setPieces() {

    const myColour = document.getElementById("colour-info").textContent === "true" ? "white" : "black";
    const opponentColour = document.getElementById("colour-info").textContent === "true" ? "black" : "white";

    const board = document.getElementById("board");

    for(let i = 0; i < 16; i++) {

        const pawn = document.createElement("img");
        const colour = i >= 8 ? myColour : opponentColour;

        pawn.src = "../images/chess-pieces/" + colour + "-pawn.png";
        pawn.classList.add("img-fluid");
        pawn.classList.add("p-2");

        const k = i >= 8 ? 1 : 0

        board.children[1 + 5 * k].children[i % 8].appendChild(pawn);
    }

    for(let i = 0; i < 4; i ++) {

        const rook = document.createElement("img");
        const colour = i >= 2 ? myColour : opponentColour;

        rook.src = "../images/chess-pieces/" + colour + "-rook.png";
        rook.classList.add("img-fluid");
        rook.classList.add("p-2");

        const k = i >= 2 ? 1 : 0

        board.children[7 * k].children[(i % 2) * 7].appendChild(rook);
    }

    for(let i = 0; i < 4; i ++) {

        const knight = document.createElement("img");
        const colour = i >= 2 ? myColour : opponentColour;

        knight.src = "../images/chess-pieces/" + colour + "-knight.png";
        knight.classList.add("img-fluid");
        knight.classList.add("p-2");

        const k = i >= 2 ? 1 : 0

        board.children[7 * k].children[1 + (i % 2) * 5].appendChild(knight);
    }

    for(let i = 0; i < 4; i ++) {

        const bishop = document.createElement("img");
        const colour = i >= 2 ? myColour : opponentColour;

        bishop.src = "../images/chess-pieces/" + colour + "-bishop.png";
        bishop.classList.add("img-fluid");
        bishop.classList.add("p-2");

        const k = i >= 2 ? 1 : 0

        board.children[7 * k].children[2 + (i % 2) * 3].appendChild(bishop);
    }

    if(myColour === "white") {

        const opponentKing = document.createElement("img");

        opponentKing.src = "../images/chess-pieces/" + opponentColour + "-king.png";
        opponentKing.classList.add("img-fluid");
        opponentKing.classList.add("p-2");

        board.children[0].children[4].appendChild(opponentKing);

        const king = document.createElement("img");

        king.src = "../images/chess-pieces/" + myColour + "-king.png";
        king.classList.add("img-fluid");
        king.classList.add("p-2");

        board.children[7].children[4].appendChild(king);

        const opponentQueen = document.createElement("img");

        opponentQueen.src = "../images/chess-pieces/" + opponentColour + "-queen.png";
        opponentQueen.classList.add("img-fluid");
        opponentQueen.classList.add("p-2");

        board.children[0].children[3].appendChild(opponentQueen);

        const queen = document.createElement("img");

        queen.src = "../images/chess-pieces/" + myColour + "-queen.png";
        queen.classList.add("img-fluid");
        queen.classList.add("p-2");

        board.children[7].children[3].appendChild(queen);
    }

    if(myColour === "black") {

        const opponentKing = document.createElement("img");

        opponentKing.src = "../images/chess-pieces/" + opponentColour + "-king.png";
        opponentKing.classList.add("img-fluid");
        opponentKing.classList.add("p-2");

        board.children[0].children[3].appendChild(opponentKing);

        const king = document.createElement("img");

        king.src = "../images/chess-pieces/" + myColour + "-king.png";
        king.classList.add("img-fluid");
        king.classList.add("p-2");

        board.children[7].children[3].appendChild(king);

        const opponentQueen = document.createElement("img");

        opponentQueen.src = "../images/chess-pieces/" + opponentColour + "-queen.png";
        opponentQueen.classList.add("img-fluid");
        opponentQueen.classList.add("p-2");

        board.children[0].children[4].appendChild(opponentQueen);

        const queen = document.createElement("img");

        queen.src = "../images/chess-pieces/" + myColour + "-queen.png";
        queen.classList.add("img-fluid");
        queen.classList.add("p-2");

        board.children[7].children[4].appendChild(queen);
    }
}

setPieces();