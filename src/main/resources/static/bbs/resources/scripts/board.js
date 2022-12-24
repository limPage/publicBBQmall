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