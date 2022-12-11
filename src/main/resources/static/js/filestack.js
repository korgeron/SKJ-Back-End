"use strict";

const photo = document.querySelector('#addPhoto');
const client = filestack.init(FILESTACK_KEY);

const options = {
    accept: ["image/*"],
    onFileUploadFinished(file) {
        console.log(file)
        document.querySelector('.subForm').innerHTML += `<input type="hidden" th:value="${file.url}" />`;
    }
}

photo.addEventListener('click', ()=>{
    client.picker(options).open();
})