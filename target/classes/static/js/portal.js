"use strict";



//Todo: CHECK TO SEE IF USER IS ADMIN OR EMPLOYEE
const getUsername = document.querySelector('.greeting').innerHTML

const username = getUsername.split(" ")
// console.log(username);
let isAdmin = false

if (username[1] === "ADMIN") {
    isAdmin = true
}



//Todo: SET BUTTON EVENTS (DIFFERENT EVENTS FOR ADMIN) FOR EMPLOYEE NAVIGATION
if (isAdmin === true){
    document.querySelectorAll('.panels').forEach((panel, i) => {
        panel.addEventListener('click', () => {
            panel.style.background = "rgba(211, 211, 211, 0.9)"

            switch (true){
                case i === 0:
                    window.location.assign("/employee/manager")
                    break;
                case i === 1:
                    window.location.assign("/shop")
                    break;
                case i === 2:
                    window.location.assign("/messages")
                    break;
            }

        })

    })
} else {
    document.querySelectorAll('.panels').forEach((panel, i) => {
        panel.addEventListener('click', () => {
            panel.style.background = "rgba(211, 211, 211, 0.9)"

            switch (true){
                case i === 0:
                    window.location.assign("/shop")
                    break;
                case i === 1:
                    window.location.assign("/messages")
                    break;
            }


        })

    })
}



let logout = document.querySelector('.logoutBtn')
if (logout != null) {
    logout.addEventListener('click', () => {
        logout.style.background = "rgba(132,132,135,0.55)"
        window.location.assign("/logout")
    })
}


let back = document.querySelector('.backBtn')
if (back != null) {
    back.addEventListener('click', () => {
        back.style.background = "rgba(132,132,135,0.55)"
        window.location.assign("/")
    })
}
