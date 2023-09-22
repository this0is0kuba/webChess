function joinRoomAlert() {
    let userInput = prompt("input the Room Code: ");

    if(!userInput) {
        return;
    }

    let userInputWithoutSpace = userInput.replace(/\s/g, '');

    if(!userInputWithoutSpace || isNaN(userInputWithoutSpace) || userInputWithoutSpace.length !== 4) {
        alert("You've provided wrong Room Code!");
        return;
    }

    const url = "http://localhost:8080/game/" + userInputWithoutSpace;

    fetch(url,
        {
            method: "PUT",
        })
        .then(response =>
            response.text()
        )
        .then(data =>{
            if(data === "null")
                alert("There is no room with this code");
            else
                window.location.href = data;
        });
}