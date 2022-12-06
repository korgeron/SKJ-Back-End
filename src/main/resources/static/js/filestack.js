"use strict";

const photo = document.querySelector('#addPhoto');
photo.addEventListener('click', ()=>{
    const client = filestack.init(YOUR_API_KEY);
    client.picker().open();
})