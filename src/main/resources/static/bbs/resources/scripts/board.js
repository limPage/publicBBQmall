if (form['isNoticeBoard'].value==="notice"){
//공지사항 탭일 경우

    function save ()  {

        window.localStorage.savedNoticeBoardId = form['bid'].value;

    }

    const load = () => {
        form['bid'].value = window.localStorage.savedNoticeBoardId ?? 'all';
    };
    window.addEventListener('load', () =>{
        load();
        delete window.localStorage.savedNoticeBoardId;
        // setInterval(save,1000);

    })

    //     window.addEventListener('load', function () {
    //
    //
    //    delete window.localStorage.savedNoticeBoardId
    //         form['bid'].value='all'
    //
    //
    // })





    form['bid'].oninput=()=>{
        save ();

        document.form.submit();
    }
    form.onsubmit=()=>{


        save ();
    }
    let page= window.document.querySelectorAll('.page')
    for( let i = 0; i < page.length; i++ ) {
        let item = page.item(i);

        item.addEventListener('click', save);
    }


    window.document.getElementById('sideBarN').style.cssText= `  color: rgba(0,0,0,0.9);
    font-weight: 700;`;

}

else if (form['isNoticeBoard'].value === "qna"){


   const questionOpen= window.document.querySelectorAll('.questionOpen');
   const antherLi=  window.document.querySelectorAll('.answer');

    for(let i=0 ;i< questionOpen.length; i++){

        const questionText= questionOpen.item(i);
        const antherText= antherLi.item(i);

        questionText.addEventListener('click', () => {

             if  (antherText.classList.contains('visible')){
                antherText.classList.remove('visible');
            }else {
                antherText.classList.add('visible');
                 }
            }
    )}

   const qid= form['qid'].value;
    if (qid===''){
        window.document.getElementById('all').style.cssText= `background-color: black;
        color:white; `;
    }
    if (qid==='user'){
        window.document.getElementById('user').style.cssText= `background-color: black;
        color:white; `;
    }
    if (qid==='payment'){
        window.document.getElementById('payment').style.cssText= `background-color: black;
        color:white; `;
    }
    if (qid==='shipping'){
        window.document.getElementById('shipping').style.cssText= `background-color: black;
        color:white; `;
    }
    if (qid==='productService'){
        window.document.getElementById('productService').style.cssText= `background-color: black;
        color:white; `;
    }
    if (qid==='product'){
        window.document.getElementById('product').style.cssText= `background-color: black;
        color:white; `;
    }
    if (qid==='other') {
        window.document.getElementById('other').style.cssText= `background-color: black;
        color:white; `;
    }




    form['searchButton'].addEventListener('click',()=>{

        const text= form['text'].value;

        // http://localhost:8080/board/
        alert(text)
        window.location.href='/board/?bid=qna&qid='+qid+'&keyword='+text

    })


    window.document.getElementById('sideBarQ').style.cssText= `  color: rgba(0,0,0,0.9);
    font-weight: 700;`;
}


