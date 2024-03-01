document.addEventListener("DOMContentLoaded", function() {
    const url = window.location.href;
    const urlArray = url.split('?');

    if(urlArray.length > 1) {

        let params = urlArray[1].split('&');
        if(params.includes("error")) {
            alert("You are already in the room!");
            window.location.href = window.location.href.split("?")[0];
        }
    }
});

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
        .then(response => {
            if(!response.ok)
                return response.text().then(errorMessage => {
                    throw new Error(errorMessage);
                })

            return response.text();
            }
        )
        .then(data => {
            if(String(data) === "null")
                alert("There is no room with this code");

            else if(String(data) === "This room is full!")
                alert("This room is already full");

            else
                window.location.href = String(data);
        })
        .catch(error => alert(error.message));
}

function joinTheRoom(ev) {

    const code = ev.target.parentNode.parentNode.children[0].textContent;

    console.log(code);

    const url = "http://localhost:8080/game/" + code;

    fetch(url,
        {
            method: "PUT",
        })
        .then(response => {
                if(!response.ok)
                    return response.text().then(errorMessage => {
                        throw new Error(errorMessage);
                    })

                return response.text();
            }
        )
        .then(data => {
            if(String(data) === "null")
                alert("There is no room with this code");

            else if(String(data) === "This room is full!")
                alert("This room is already full");

            else
                window.location.href = String(data);
        })
        .catch(error => alert(error.message));
}