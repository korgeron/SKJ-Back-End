"use strict";

const photo = document.querySelector('#addPhoto');
const client = filestack.init(FILESTACK_KEY);

const options = {
    accept: ["image/*"],
    disableTransformer: true,
    onFileUploadFinished(file) {
        console.log(file)
        // document.querySelector('.subForm').innerHTML += `<input type="hidden" th:value="${file.url}" th:name="photo" />`;
        document.querySelector('#test').value = file.url
        document.querySelector('.subForm').submit();
    }
}

photo.addEventListener('click', ()=>{
    client.picker(options).open();
})