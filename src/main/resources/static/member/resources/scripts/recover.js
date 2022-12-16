 const form = window.document.getElementById('form');
const content = window.document.getElementById('content');
 let emailAuthIndex = null;




form['recoverId'].addEventListener('click',()=>{
    if(form.classList.contains('recover-mode-password')){
    form.classList.add('recover-mode-id');
    form.classList.remove('recover-mode-password');




        form['name'].removeAttribute('disabled');
        form['email'].removeAttribute('disabled');

        form.querySelector('.result-button-row').classList.remove('visible');



        form.querySelector('.result-row').classList.remove('visible');
        form.querySelector('.next-button-row').classList.add('visible');
        form.querySelector('.icon-row').classList.add('visible');



    }

})



 form['recoverPassword'].addEventListener('click',()=>{
     if(form.classList.contains('recover-mode-id')) {


         form.classList.remove('recover-mode-id');
         form.classList.add('recover-mode-password');


         form['name'].removeAttribute('disabled');
         form['email'].removeAttribute('disabled');

         form.querySelector('.result-button-row').classList.remove('visible');



         form.querySelector('.result-row').classList.remove('visible');
         form.querySelector('.next-button-row').classList.add('visible');
         form.querySelector('.icon-row').classList.add('visible');



     }
 })

 form['next'].addEventListener('click',()=>{

//아이디 찾기 전송
     if(form.classList.contains('recover-mode-id')){


         if(form['name'].value==='' || form['email'].value===''){
             alert("이름, 이메일을 모두 입력해주세요.");
             return;
         }

         Cover.show('데이터 처리 중입니다. 잠시만 기다려주세요');

         const xhr = new XMLHttpRequest();
         const formData = new FormData();


         formData.append('name',form['name'].value);
         formData.append('email',form['email'].value);

         xhr.open('POST', '/member/recover');
         xhr.onreadystatechange = () => {
             if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
                 Cover.hide();
                 if (xhr.status >= 200 && xhr.status < 300) {
                     const responseObject = JSON.parse(xhr.responseText);
                     switch (responseObject['result']){
                         case 'success':
                             alert('이메일 찾기 성공');

                             form['name'].setAttribute('disabled','disabled');
                             form['email'].setAttribute('disabled','disabled');

                             form.querySelector('.result-button-row').classList.add('visible');

                             let resultId= (responseObject['id']);
                             form.querySelector('[rel="result-id"]').innerHTML='<b style="color: yellow; background-color: black" >'  +  resultId +'</b>' ;
                             form.querySelector('.result-row').classList.add('visible');
                             form.querySelector('.next-button-row').classList.remove('visible');
                             form.querySelector('.icon-row').classList.remove('visible');
                             form['id'].value=resultId;

                             break;

                         default:
                             alert('입력한 정보와 일치하는 회원이 없습니다.')
                     }
                 } else {
                     alert('서버와 통신하지 못하였습니다. 잠시후 다시 시도해 주세요.');
                 }
             }
         };
         xhr.send(formData);




     // 비밀번호찾기 전송
     }else {

     if(form['name'].value==='' || form['id'].value==='' || form['email'].value==='') {
         alert("이름, 아이디, 이메일을 모두 입력해주세요.");
         return;

     }

         Cover.show('데이터 처리 중입니다. 잠시만 기다려주세요');

         const xhr = new XMLHttpRequest();
         const formData = new FormData();


         formData.append('name',form['name'].value);
         formData.append('email',form['email'].value);
         formData.append('id',form['id'].value);


         xhr.open('POST', '/member/recoverPassword');
         xhr.onreadystatechange = () => {
             if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
                 Cover.hide();
                 if (xhr.status >= 200 && xhr.status < 300) {
                     const responseObject = JSON.parse(xhr.responseText);
                         switch (responseObject['result']) {
                             case 'success':
                             // alert('됨!');
                             emailAuthIndex = responseObject['index'];
                             form['email'].setAttribute('disabled','disabled');
                             form['next'].setAttribute('disabled','disabled');
                             // form.querySelector('[rel="messageRow"]').classList.add('visible');
                             alert("이메일로 메일을 보냈습니다.")
                             break;

                         default:
                           alert('해당 이메일을 사용하는 계정을 찾을 수 없습니다.');
                             form['email'].focus();
                             form['email'].select();

                     }
                 } else {
                     alert('서버와 통신하지 못하였습니다. 잠시후 다시 시도해 주세요.');
                 }
             }
         };
         xhr.send(formData);









         alert("비밀번호 찾기모드");
     }
 })

