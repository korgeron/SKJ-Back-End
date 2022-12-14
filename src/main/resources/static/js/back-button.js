const backBtn = document.querySelector('.backBtn');
if (backBtn != null) {
    backBtn.addEventListener('click', ()=>{
        window.location.assign("/shop")
    })
}

const clothingBtn = document.querySelector('.clothingBackBtn')
if (clothingBtn != null) {
    clothingBtn.addEventListener('click', ()=>{
        window.location.assign("/shop/all-products")
    })
}
