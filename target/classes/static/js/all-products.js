document.querySelectorAll('.panels').forEach((button , i) => {
    button.addEventListener('click', ()=>{
        console.log(i);
        if (i === 0){
            window.location.assign('/shop/clothing-page')
        }
        if (i === 1){
            window.location.assign('/shop/equipment-page')
        }
    })
})