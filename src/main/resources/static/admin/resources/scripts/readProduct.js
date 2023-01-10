

let imageButton= window.document.querySelectorAll('.image-select-button');
let images= window.document.querySelectorAll('.images');
let imageContainer=window.document.querySelectorAll('.image-container');
let noImage= window.document.querySelectorAll('.no-image');
let detailIndex;
form['menuIndex'].addEventListener('change', ()=>{



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
//상세 메뉴를 골랐을때



form['detailIndex1'].addEventListener('change', ()=>{

    if(form['detailIndex1'].value!=='0'){
        detailIndex=form['detailIndex1'].value}
    window.location.href=`/admin/read/?detailIndex=${detailIndex}`

})
form['detailIndex2'].addEventListener('change', ()=>{


    if(form['detailIndex2'].value!=='0'){
        detailIndex=form['detailIndex2'].value}
    window.location.href=`/admin/read/?detailIndex=${detailIndex}`

})
form['detailIndex3'].addEventListener('change', ()=>{


    if(form['detailIndex3'].value!=='0'){
        detailIndex=form['detailIndex3'].value}
    window.location.href=`/admin/read/?detailIndex=${detailIndex}`

})






form['detailIndex1'].addEventListener("input",()=>{



        // const xhr = new XMLHttpRequest();
        //
        // xhr.open('GET', `./data/review?detailIndex=${detailIndex}`);
        // xhr.onreadystatechange = () => {
        //     if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
        //         if (xhr.status >= 200 && xhr.status < 300) {
        //             const responseArray = JSON.parse(xhr.responseText);
        //             for (const reviewObject of responseArray) {
        //
        //                 const itemHtml = `
        //     <li class="item" rel="item">
        //         <span class="nickname" rel="nickname">${reviewObject['userNickname']}</span>
        //         <div class="image-container" rel="imageContainer"></div>
        //         <span class="content" rel="content">${reviewObject['content']}</span>
        //     </li>`;
        //                 const itemElement = new DOMParser().parseFromString(itemHtml, 'text/html').querySelector('[rel="item"]');
        //                 const imageContainerElement = itemElement.querySelector('[rel="imageContainer"]');
        //                 if (reviewObject['imageIndexes'].length > 0) {
        //                     for (const imageIndex of reviewObject['imageIndexes']) {
        //                         const imageElement = document.createElement('img');
        //                         imageElement.setAttribute('alt', '');
        //                         imageElement.setAttribute('src', `./data/reviewImage?index=${imageIndex}`);
        //                         imageElement.classList.add('image');
        //                         imageContainerElement.append(imageElement);
        //                     }
        //                 } else {
        //                     imageContainerElement.remove();
        //                 }
        //                 reviewContainer.append(itemElement);
        //             }
        //
        //         } else {
        //             alert('서버와 통신하지 못하였습니다. 잠시후 다시 시도해 주세요.')
        //
        //         }
        //     }
        //
        // };
        // xhr.send();


})



