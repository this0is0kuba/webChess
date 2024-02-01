function addEvents() {

    window.addEventListener("load", resizeForm);
    window.addEventListener("resize", resizeForm);
}

function resizeForm() {

    const userNameField = document.getElementById("username");
    const passwordField = document.getElementById("password");
    const firstNameField = document.getElementById("firstname");
    const lastNameField = document.getElementById("lastname");
    const emailField = document.getElementById("email");

    const submitButtonField = document.getElementById("submit-button");
    const loginButtonField = document.getElementById("login-button");

    const windowWidth = window.innerWidth;

    if (windowWidth < 992) {

        userNameField.classList.add('form-control-lg');
        passwordField.classList.add('form-control-lg');
        firstNameField.classList.add('form-control-lg');
        lastNameField.classList.add('form-control-lg');
        emailField.classList.add('form-control-lg');
        loginButtonField.classList.add("btn-lg");
        submitButtonField.classList.add("btn-lg");
    } else {

        userNameField.classList.remove('form-control-lg');
        passwordField.classList.remove('form-control-lg');
        firstNameField.classList.remove('form-control-lg');
        lastNameField.classList.remove('form-control-lg');
        emailField.classList.remove('form-control-lg');
        loginButtonField.classList.remove("btn-lg");
        submitButtonField.classList.remove("btn-lg");
    }
}

addEvents()