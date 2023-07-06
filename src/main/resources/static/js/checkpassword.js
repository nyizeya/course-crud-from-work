let currentPassword = document.getElementById('currentPassword')
let currentPasswordError = document.getElementById('currentPasswordError')

let newPassword = document.getElementById('newPassword')
let newPasswordError = document.getElementById('newPasswordError')

let confirmPassword = document.getElementById('confirmPassword')
let confirmPasswordError = document.getElementById('confirmPasswordError')

let saveBtn = document.getElementById("saveBtn")
let closeBtn = document.getElementById("closeBtn")
let passwordChangeForm = document.getElementById("passwordChangeForm")

let error = {
    cpE: true,
    npE: true,
    conpE: true
}

saveBtn.addEventListener('click', () => {
    closeBtn.click();
    passwordChangeForm.submit()
})

currentPassword.addEventListener('keyup', () => {
    checkCurrentP()
})

async function checkCurrentP() {
    if (currentPassword.value.length === 0) {
        currentPasswordError.innerText = 'Enter your current password'
        error.cpE = true
        saveBtn.disabled = true
    } else {
        currentPasswordError.innerText = ''
        error.cpE = false

        if (checkErrors()) {
            saveBtn.disabled = false
        }
    }

    if (currentPassword.value.length > 0) {
        let flag = await checkPassword()
        if (!flag) {
            currentPasswordError.innerText = 'Your password is wrong';
            error.cpE = true;
        } else {
            currentPasswordError.innerText = '';
            error.cpE = false
            if (checkErrors()) {
                saveBtn.disabled = false
            }
        }

    }
}

newPassword.addEventListener('keyup', (e) => {
    checkNewP()
    checkConPE()
})

function checkNewP() {
    if (newPassword.value.length < 8) {
        newPasswordError.innerText = 'At least 8 characters required'
        error.npE = true
        saveBtn.disabled = true
    } else {
        newPasswordError.innerText = ''
        error.npE = false

        if (checkErrors()) {
            saveBtn.disabled = false
        }
    }
}

confirmPassword.addEventListener('keyup', (e) => {
    checkConPE()
    checkNewP()
})

function checkConPE() {
    if (newPassword.value !== confirmPassword.value) {
        confirmPasswordError.innerText = "Passwords don't match"
        error.conpE = true
        saveBtn.disabled = true
    } else {
        confirmPasswordError.innerText = ""
        error.conpE =false

        if (checkErrors()) {
            saveBtn.disabled = false
        }
    }
}

function checkErrors() {
    return error.cpE === false && error.npE === false && error.conpE === false
}

/*
        Check Password Functionality
 */

let theInstructorId = document.getElementById("theInstructorId")

async function checkPassword() {
    let theResult = false;
    let response = await fetch(`http://localhost:8080/instructors/check-password
        ?id=${theInstructorId.value}&password=${currentPassword.value}`)

    if (response.ok) {
        let data = await response.json()
        theResult = data['theResult']
    }

    return theResult

}
