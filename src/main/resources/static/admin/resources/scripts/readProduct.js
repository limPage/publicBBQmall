

let imageButton= window.document.querySelectorAll('.image-select-button');
let images= window.document.querySelectorAll('.images');
let imageContainer=window.document.querySelectorAll('.image-container');
let noImage= window.document.querySelectorAll('.no-image');
let detailIndex;
form['menuIndex'].addEventListener('change', ()=>{
    if(form['menuIndex'].value==='99') {
        form['detailIndex1'].style.display = "none"
        form['detailIndex2'].style.display = "none"
        form['detailIndex3'].style.display = "none"

        window.location.href=`/admin/read/?menuIndex=99`

    }

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
let nowDetailIndex= parseInt(form['nowDetailIndex'].value);
let nowMenuIndex= parseInt(form['nowMenuIndex'].value);

if (nowMenuIndex===99){
    form['menuIndex'].value=99;

}

if (!isNaN(nowMenuIndex) && nowMenuIndex===1){
    form['detailIndex1'].style.display="inline-block";
    form['detailIndex1'].value=nowDetailIndex;
    form['menuIndex'].value='1';

    form['detailIndex3'].style.display="none"
    form['detailIndex2'].style.display="none"
}
if (nowMenuIndex===2){

    form['detailIndex2'].style.display="inline-block";
    form['detailIndex2'].value=nowDetailIndex;
    form['menuIndex'].value='2';

    form['detailIndex1'].style.display="none"
    form['detailIndex3'].style.display="none"
}

// parseInt(form['detailIndex'].value) >=12
if (nowMenuIndex===3 ){

    form['detailIndex3'].style.display="inline-block";
    form['detailIndex3'].value=nowDetailIndex;
    form['menuIndex'].value='3';
    form['detailIndex1'].style.display="none"
    form['detailIndex2'].style.display="none"

}



form['detailIndex1'].addEventListener('change', ()=>{

    if(form['detailIndex1'].value!=='0') {
        detailIndex = form['detailIndex1'].value

        window.location.href = `/admin/read/?menuIndex=1&detailIndex=${detailIndex}`
    }
})
form['detailIndex2'].addEventListener('change', ()=>{


    if(form['detailIndex2'].value!=='0') {
        detailIndex = form['detailIndex2'].value
        window.location.href = `/admin/read/?menuIndex=2&detailIndex=${detailIndex}`
    }
})
form['detailIndex3'].addEventListener('change', ()=>{


    if(form['detailIndex3'].value!=='0') {
        detailIndex = form['detailIndex3'].value
        window.location.href = `/admin/read/?menuIndex=3&detailIndex=${detailIndex}`
    }
})









