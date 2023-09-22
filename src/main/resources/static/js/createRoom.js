document.addEventListener("DOMContentLoaded", function() {

    const privateCheckBox = document.getElementById("room-private-checkbox");
    const hiddenRoomPassword = document.getElementById("room-password");

    privateCheckBox.addEventListener("change", function() {
        if(privateCheckBox.checked)
            hiddenRoomPassword.style.display = "block";
        else
            hiddenRoomPassword.style.display = "none";
    });
});