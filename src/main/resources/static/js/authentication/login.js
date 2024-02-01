function addEvents() {

    window.addEventListener("load", resizeForm);
    window.addEventListener("resize", resizeForm);
}

function resizeForm() {

    const userNameField = document.getElementById("username");
    const passwordField = document.getElementById("password");

    const submitButtonField = document.getElementById("submit-button");
    const registerButtonField = document.getElementById("register-button");

    const windowWidth = window.innerWidth;

    if (windowWidth < 992) {
        userNameField.classList.add('form-control-lg');
        passwordField.classList.add('form-control-lg');
        registerButtonField.classList.add("btn-lg");
        submitButtonField.classList.add("btn-lg");
    } else {
        userNameField.classList.remove('form-control-lg');
        passwordField.classList.remove('form-control-lg');
        submitButtonField.classList.remove("btn-lg");
        registerButtonField.classList.remove("btn-lg");
    }
}

addEvents();