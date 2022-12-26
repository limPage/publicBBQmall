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


}

else if (form['isNoticeBoard'].value === "qna"){


   const questionOpen= window.document.querySelectorAll('.questionOpen');
   const antherLi=  window.document.querySelectorAll('.anther');

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

 }

