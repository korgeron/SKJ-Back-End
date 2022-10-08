"use strict";

    document.querySelectorAll('.panels').forEach((panel, i) => {
        panel.addEventListener('click', () => {
            panel.style.background = "rgba(211, 211, 211, 0.9)"

            switch (true) {
                case i === 0:
                    window.location.assign("/shop/all-products")
                    break;
                case i === 1:
                    window.location.assign("/shop/add-clothing")
                    break;
                case i === 2:
                    window.location.assign("/shop/add-equipment")
                    break;
            }

        })
    })

document.querySelector('.backBtn').addEventListener('click', ()=>{
    window.location.assign("/")
})