console.log('linked!')

document.querySelectorAll('.panels').forEach((panel, i) => {
    panel.addEventListener('click', () => {
        panel.style.background = "rgba(211, 211, 211, 0.9)"
        console.log(i)
        if (i === 0) {
            window.location.assign("/employee/create")
        }

    })

})

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
