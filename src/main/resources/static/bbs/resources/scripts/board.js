const save = () => {

    window.localStorage.savedNoticeBoardId = form['bid'].value;

};

const load = () => {
    form['bid'].value = window.localStorage.savedNoticeBoardId ?? 'all';
};
window.addEventListener('load', () =>{
    load();
    setInterval(save,1000);

})

window.document.querySelector('.get-notice-board').addEventListener('click',()=>{


   delete window.localStorage.savedNoticeBoardId



})

const dateNow=  new Date();
console.log(dateNow);

let year = dateNow.getFullYear();
let month =dateNow.getMonth()+1;
let day = dateNow.getDate();

console.log("현 연도"+year);
console.log("현 달월"+month);
console.log("현 날일"+day);



const isNew =window.document.querySelectorAll('.is-new');

for (let i = 0; i < isNew.length; i++) {
    let item = isNew.item(i);

    let writtenYear=item.value.substring(0, 4);
    let writtenMonth=item.value.substring(4, 6);
    let writtenDate=item.value.substring(6);

    console.log("날짜"+item.value.substring(0, 4));
    console.log("달"+item.value.substring(4, 6));
    console.log("일"+item.value.substring(6));

    console.log(typeof(parseInt( item.value)));
    console.log( item.value);

    console.log(year-writtenYear);



    if(year>writtenYear){
        item.value=true;
        // window.document.querySelectorAll('.is-new-icon').item(i).classList.add('new')

    }else if (month>writtenMonth){
        item.value=true;
        // window.document.querySelectorAll('.is-new-icon').item(i).classList.add('new')
    }else if(day - writtenDate >1 ){
        item.value=true;
        window.document.querySelectorAll('.is-new-icon').item(i).classList.remove('new')

    }

 console.log("아이이템ㅇ"+item.value)


}