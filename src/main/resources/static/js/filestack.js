"use strict";

const photo = document.querySelector('#addPhoto');
const client = filestack.init(FILESTACK_KEY);

photo.addEventListener('click', ()=>{

    client.picker().open();
})