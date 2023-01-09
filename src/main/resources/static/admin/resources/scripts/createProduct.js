

let imageButton= window.document.querySelectorAll('.image-select-button');
let images= window.document.querySelectorAll('.images');
let imageContainer=window.document.querySelectorAll('.image-container');
let noImage= window.document.querySelectorAll('.no-image');

form['menuIndex'].addEventListener('change', ()=>{
//     const menu = ["튀김류","구이류","순살","윙&봉","닭갈비","패키지",
//     "훈제&수비드","소세지&핫바","만두&육포","닭가슴살","스테이크&큐브","즉석간편식",
//     "안주","탕찜","혼합세트","간식류"]
//
//     const value= [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16];
//
//     let optionElement1=document.createElement('option');
//     let optionElement2=document.createElement('option');
//     let optionElement3=document.createElement('option');
//     let optionElement4=document.createElement('option');
//     let optionElement5=document.createElement('option');
//     let optionElement6=document.createElement('option');
//     let optionElement7=document.createElement('option');
//     let optionElement8=document.createElement('option');
//     let optionElement9=document.createElement('option');
//     let optionElement10=document.createElement('option');
//     let optionElement11=document.createElement('option');
//     let optionElement12=document.createElement('option');
//     let optionElement13=document.createElement('option');
//     let optionElement14=document.createElement('option');
//     let optionElement15=document.createElement('option');
//     let optionElement16=document.createElement('option');
//
//     const array= [optionElement1,optionElement2,optionElement3,optionElement4,
//         optionElement5,optionElement6,optionElement7,optionElement8,optionElement9,
//         optionElement10,optionElement11,optionElement12,optionElement13,optionElement14,
//         optionElement15,optionElement16]
//
//
//     for (let i = 0; i < menu.length ; i++) {
//         // array[i].createElement('option');
//         array[i].setAttribute("value",value[i]);
//         array[i].classList.add("detail"+i);
//         array[i].innerText=menu[i];
//     }



    if(form['menuIndex'].value==='1') {
        form['detailIndex1'].style.display="inline-block"
        form['detailIndex2'].style.display="none"
        form['detailIndex2'].value=0;
        form['detailIndex3'].style.display="none"
        form['detailIndex3'].value=0;

}else if(form['menuIndex'].value==='2') {
        form['detailIndex2'].style.display="inline-block"
        form['detailIndex1'].style.display="none"
        form['detailIndex1'].value=0;
        form['detailIndex3'].style.display="none"
        form['detailIndex3'].value=0;




    }else if(form['menuIndex'].value==='3') {
        form['detailIndex3'].style.display="inline-block"
        form['detailIndex2'].style.display="none"
        form['detailIndex2'].value=0;
        form['detailIndex1'].style.display="none"
        form['detailIndex1'].value=0;


    }




})



    for (let i=0; i< imageButton.length; i++){
        imageButton.item(i).addEventListener("click",(e)=>{

            e.preventDefault();
            images.item(i).click();
        })



        images.item(i).addEventListener('input', () => {
            const imageContainerElement = imageContainer.item(i);
            imageContainerElement.querySelectorAll('img.image').forEach(x => x.remove());
            if (images.item(i).files.length > 0) {
                noImage.item(i).setAttribute("hidden",1);
            } else {
                noImage.item(i).removeAttribute('hidden');
            }

            for (let file of images.item(i).files) {
                const imageSrc = URL.createObjectURL(file);
                const imgElement = document.createElement('img');
                imgElement.classList.add('image');
                imgElement.setAttribute('src', imageSrc);
                imageContainerElement.append(imgElement);
            }

        });

}




