//const form = window.document.getElementById('form');
form['emailButton'].addEventListener('click', ()=>{

    alert('ddd');
})

form['codeSend'].addEventListener('click', ()=> {


    if(form['emailText'].value === '') {
        alert('이메일 주소를 입력해주세요.');
        form['emailText'].focus();
        return false;
    }
    let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

    if (!regEmail.test(form['emailText'].value)) {
        alert('이메일 주소를 바른 형식으로 입력해 주세요');

        form['emailText'].focus();
        return;

    }

    Cover.show('인증번호를 전송하고 있습니다.\n\n 잠시만 기다려 주세요.');

    const xhr = new XMLHttpRequest(); // new XHR 탭 자동완성
    const formData = new FormData(); // FormData 객체를 사용하는 formData 변수 선언
    formData.append('email', form['emailText'].value); // @RequestParam의 value, form의 input의 name

    xhr.open('POST', './email'); // 1이 찍힘
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE) { // 연결 상태가 연결완료(성공, 실패 관계없이)일 때, 4가 찍힘
            Cover.hide(); // visible 클래스 삭제
            if(xhr.status >= 200 && xhr.status < 300) { // 연결 성공. status = XMLHttpRequest 내장 Property, 문서 상태 나타냄. 200~300 :
                const responseObject = JSON.parse(xhr.responseText);
                switch(responseObject['result']) {
                    case 'success':
                        // form.querySelector('[rel="emailWarning"]').innerText = '인증 번호를 전송하였습니다.\n 전송된 인증번호는 5분간만 유효합니다.';

                        alert('인증 번호를 전송하였습니다. 전송된 인증번호는 5분간만 유효합니다.');

                        // form['email'].setAttribute('disabled', 'disabled'); //  email을 사용불가로
                        // form['emailSend'].setAttribute('disabled', 'disabled'); // emailSend 버튼을 사용불가로
                        // form['emailAuthCode'].removeAttribute('disabled'); // 인증번호 의 disalbed 삭제하여 사용가능하게
                        // form['emailAuthCode'].focus(); // 인증번호 칸에 포커스
                        form['emailAuthSalt'].value = responseObject['salt'];
                        // form['emailVerify'].removeAttribute('disabled');
                        break;

                    case 'email_duplicated':
                        // form.querySelector('[rel="emailWarning"]').innerText = '해당 이메일은 이미 사용중입니다.';
                        // form.querySelector('[rel="emailWarning"]').classList.add('visible');
                        alert('해당 이메일은 이미 사용중입니다.');

                        form['emailText'].focus();
                        form['emailText'].select(); // 이메일 내용 드래그상태
                        break;
                    default:
                        // form.querySelector('[rel="emailWarning"]').innerText = '알 수 없는 이유로 인증 번호를 전송하지 못하였습니다.\n 잠시 후 다시 시도해 주세요.';
                        // form.querySelector('[rel="emailWarning"]').classList.add('visible');
                        alert('알 수 없는 이유로 인증번호를 전송하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                        form['emailText'].focus();
                        form['emailText'].select(); // 이메일 내용 드래그상태
                }
                console.log(responseObject['result']);
                console.log(responseObject['salt']);
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
});



// const Warning = {
//     show : (text) => {
//         form.querySelector('[rel = "warningText"]').innerText = text;
//         form.querySelector('[rel="warning"]').classList.add('visible');
//     },
//     hide : () => {
//         form.querySelector('[rel="warning"]').classList.remove('visible');
//     }
// }
// const EmailWarning = {
//     show : (text) => {
//         const emailWarning = form.querySelector('[rel="emailWarning"]');
//         emailWarning.innerText = text;
//         emailWarning.classList.add('visible');
//     },
//     hide : () => {
//         form.querySelector('[rel="emailWarning"]').classList.remove('visible');
//     }
// }
//
// form.querySelector('[rel = "nextButton"]').addEventListener('click', () => { // nextButton을 클릭했을 때
//     form.querySelector('[rel = "warning"]').classList.remove('visible'); // 타입 rel이 warning인 html 클래스에 존재하는 visible을 제거한다.
//
//     if(form.classList.contains('step0')) {
//         form['registerButton'].addEventListener('click', () => {
//             form.querySelector('[rel="warning"]').classList.add('visible');
//             form.classList.remove('step0');
//             form.classList.add('step1');
//         });
//     }
//
//     if(form.classList.contains('step1')) { // 클래스가 step1을 포함하고 있을 때
//         console.log('스텝1 처리');
//         if (!form['termAgree'].checked) { // termAgree가 체크되지 않았을 때
//             form.querySelector('[rel = "warningText"]').innerText = '서비스 이용약관 및 개인정보 처리방침을 읽고 동의해 주세요.'; // rel이 warningText인 요소에 해당 문구를 추가한다.
//             form.querySelector('[rel = "warning"]').classList.add('visible'); // rel이 warning 요소의 클래스에 visible을 추가한다.
//             return;
//         }
//         form.querySelector('[rel = "stepText"]').innerText = '개인정보 입력';
//         form.classList.remove('step1'); // 다음 클릭시 step1 클래스 삭제
//         form.classList.add('step2'); // step2 클래스 추가
//     } else if(form.classList.contains('step2')) { // step2일 때
//         if(!form['emailSend'].disabled || !form['emailVerify'].disabled) {
//             Warning.show('이메일 인증을 완료해주세요.');
//             (form['email'] || form['emailAuthCode'])?.focus();
//             return;
//         }
//         if(form['password'].value === '') {
//             Warning.show('비밀번호를 입력해주세요.');
//             form['password'].focus();
//             return;
//         }
//         if(form ['password'].value !== form['passwordCheck'].value) {
//             Warning.show('비밀번호가 서로 일치하지 않습니다.');
//             form['passwordCheck'].focus();
//             form['passwordCheck'].select();
//             return;
//         }
//         if(form['nickname'].value === '') {
//             Warning.show('닉네임을 입력해 주세요.');
//             form['nickname'].focus();
//             return;
//         }
//         if(form['name'].value === '') {
//             Warning.show('이름을 입력해 주세요.');
//             form['name'].focus();
//             return;
//         }
//         if(form['contact'].value === '') {
//             Warning.show('연락처를 입력해 주세요.');
//             form['contact'].focus();
//             return;
//         }
//         if(form['addressPostal'].value === '' || form['addressPrimary'].value === '') {
//             Warning.show('주소 찾기를 통해 주소를 입력해 주세요');
//             return;
//         }
//
//         Cover.show("회원가입 진행 중입니다.\n\n잠시만 기다려 주세요.");
//
//         // 1107 회원가입 마지막 진행중
//         const xhr = new XMLHttpRequest();
//         const formData = new FormData();
//         formData.append('email', form['email'].value);
//         formData.append('code', form['emailAuthCode'].value);
//         formData.append('salt', form['emailAuthSalt'].value);
//         formData.append('password', form['password'].value);
//         formData.append('nickname', form['nickname'].value);
//         formData.append('name', form['name'].value);
//         formData.append('contact', form['contact'].value);
//         formData.append('addressPostal', form['addressPostal'].value);
//         formData.append('addressPrimary', form['addressPrimary'].value);
//         formData.append('addressSecondary', form['addressSecondary'].value);
//
//         xhr.open('POST', './register');
//         xhr.onreadystatechange = () => {
//             if(xhr.readyState === XMLHttpRequest.DONE) {
//                 Cover.hide();
//                 if(xhr.status >= 200 && xhr.status < 300) {
//                     const responseObject = JSON.parse(xhr.responseText);
//                     switch(responseObject['result']) {
//                         case 'success':
//                             form.querySelector('[rel = "stepText"]').innerText = '회원가입 완료';
//                             form.querySelector('[rel = "nextButton"]').innerText = '로그인하러 가기';
//                             form.classList.remove('step2');
//                             form.classList.add('step3');
//                             break;
//                         case 'email_not_verified':
//                             Warning.show('이메일 인증이 완료되지 않았습니다.');
//                             break;
//                         default:
//                             Warning.show('알 수 없는 이유로 회원가입에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
//                             break;
//                     }
//                 } else {
//                     Warning.show('서버와 통신하지 못하였습니다. 잠시후 다시 시도해 주세요.');
//                 }
//             }
//         };
//
//         xhr.send(formData);
//
//         // form.querySelector('[rel = "stepText"]').innerText = '회원가입 완료'; // 숫자 버튼 옆에 회원가입 완료 텍스트 추가
//         // form.querySelector('[rel = "nextButton"]').innerText = '로그인하러 가기'; // 다음 버튼에 로그인하러 가기 텍스트 추가
//         // form.classList.remove('step2'); // step2 클래스 삭제
//         // form.classList.add('step3'); // step3 클래스 추가
//     } else if(form.classList.contains('step3')) {
//         window.location.href = 'login';
//     }
// });
//
// form['emailSend'].addEventListener('click', () => { // emailSend 버튼을 클릭했을 때
//     form.querySelector('[rel = "emailWarning"]').classList.remove('visible'); // rel이 emailWarning인 태그의 visible 클래스를 삭제한다.
//     if(form['email'].value === '') { // 이메일이 비었다면
//         // form.querySelector('[rel = "emailWarning"]').innerText = '이메일 주소를 입력해주세요.'; // rel이 emailWarning인 span에 해당 문구를 전달한다.
//         // form.querySelector('[rel = "emailWarning"]').classList.add('visible'); // rel이 emailWarning인 span에 visible 클래스를 추가한다.
//         EmailWarning.show('이메일 주소를 입력해주세요.');
//         form['email'].focus();
//         return;
//     }
//
//     Cover.show('인증번호를 전송하고 있습니다.\n\n 잠시만 기다려 주세요.');
//     const xhr = new XMLHttpRequest(); // new XHR 탭 자동완성
//     const formData = new FormData(); // FormData 객체를 사용하는 formData 변수 선언
//     formData.append('email', form['email'].value); // @RequestParam의 value, form의 input의 name
//
//     xhr.open('POST', './email'); // 1이 찍힘
//     xhr.onreadystatechange = () => {
//         if(xhr.readyState === XMLHttpRequest.DONE) { // 연결 상태가 연결완료(성공, 실패 관계없이)일 때, 4가 찍힘
//             Cover.hide(); // visible 클래스 삭제
//             if(xhr.status >= 200 && xhr.status < 300) { // 연결 성공. status = XMLHttpRequest 내장 Property, 문서 상태 나타냄. 200~300 : 연결성공, 404 : 서버 호출 불가, 405 : 서버는 호출, intellij상 문제
//                 /*
//                 JSON.parse : 문자열을 오브젝트로 바꿔주는 역할
//                 {"result":"success","salt":"66b6980d..."}
//                 -> JSON.parse ->
//                 {
//                     result : 'success',
//                     salt : 'ce9ac...'
//                 }
//                  */
//                 const responseObject = JSON.parse(xhr.responseText);
//                 switch(responseObject['result']) {
//                     case 'success':
//                         // form.querySelector('[rel="emailWarning"]').innerText = '인증 번호를 전송하였습니다.\n 전송된 인증번호는 5분간만 유효합니다.';
//                         // form.querySelector('[rel="emailWarning"]').classList.add('visible'); // emailWarning의 클래스에 visible 추가
//                         EmailWarning.show('인증 번호를 전송하였습니다. 전송된 인증번호는 5분간만 유효합니다.');
//                         form['email'].setAttribute('disabled', 'disabled'); //  email을 사용불가로
//                         form['emailSend'].setAttribute('disabled', 'disabled'); // emailSend 버튼을 사용불가로
//                         form['emailAuthCode'].removeAttribute('disabled'); // 인증번호 의 disalbed 삭제하여 사용가능하게
//                         form['emailAuthCode'].focus(); // 인증번호 칸에 포커스
//                         form['emailAuthSalt'].value = responseObject['salt'];
//                         form['emailVerify'].removeAttribute('disabled');
//                         break;
//                     case 'email_duplicated':
//                         // form.querySelector('[rel="emailWarning"]').innerText = '해당 이메일은 이미 사용중입니다.';
//                         // form.querySelector('[rel="emailWarning"]').classList.add('visible');
//                         EmailWarning.show('해당 이메일은 이미 사용중입니다.');
//                         form['email'].focus();
//                         form['email'].select(); // 이메일 내용 드래그상태
//                         break;
//                     default:
//                         // form.querySelector('[rel="emailWarning"]').innerText = '알 수 없는 이유로 인증 번호를 전송하지 못하였습니다.\n 잠시 후 다시 시도해 주세요.';
//                         // form.querySelector('[rel="emailWarning"]').classList.add('visible');
//                         EmailWarning.show('알 수 없는 이유로 인증번호를 전송하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//                         form['email'].focus();
//                         form['email'].select(); // 이메일 내용 드래그상태
//                 }
//                 console.log(responseObject['result']);
//                 console.log(responseObject['salt']);
//             } else { // 연결 실패시
//                 // form.querySelector('[rel="emailWarning"]').innerText = '서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.'; // rel이 emailWarning인 span에 해당 문구를 전송한다.
//                 // form.querySelector('[rel = "emailWarning"]').classList.add('visible'); // rel이 emailWarning에 해당하는 클래스에 visible 클래스를 추가한다.
//                 EmailWarning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//             }
//         }
//     };
//     xhr.send(formData);
// });
//
//
// form['emailVerify'].addEventListener('click', () => {
//     if(form['emailAuthCode'].value === '') {
//         EmailWarning.show('인증 번호를 입력해주세요.');
//         form['emailAuthCode'].focus();
//         return;
//     }
//     if(!new RegExp('^(\\d{6})$').test(form['emailAuthCode'].value)) {
//         EmailWarning.show('올바른 인증 번호를 입력해주세요.');
//         form['emailAuthCode'].focus();
//         form['emailAuthCode'].select();
//         return;
//     }
//
//     Cover.show('인증 번호를 확인하고 있습니다. \n\n 잠시만 기다려 주세요.');
//     const xhr = new XMLHttpRequest();
//     const formData = new FormData();
//     formData.append('email', form['email'].value); // Entity의 email 이름, html의 input의 이름
//     formData.append('code', form['emailAuthCode'].value); // Entity의 code의 이름, html의 input의 이름
//     formData.append('salt', form['emailAuthSalt'].value); // Entity의 salt의 이름, html의 input의 이름
//     xhr.open('PATCH', 'email');
//     xhr.onreadystatechange = () => {
//         if(xhr.readyState === XMLHttpRequest.DONE) {
//             Cover.hide();
//             if(xhr.status >= 200 && xhr.status < 300) {
//                 const responseObject = JSON.parse(xhr.responseText);
//                 switch(responseObject['result']) {
//                     case 'expired':
//                         EmailWarning.show('인증 정보가 만료되었습니다. 다시 시도해 주세요.');
//                         form['email'].removeAttribute('disabled');
//                         form['email'].focus();
//                         form['email'].select();
//                         form['emailSend'].removeAttribute('disabled');
//                         form['emailAuthCode'].value = '';
//                         form['emailAuthCode'].setAttribute('disabled', 'disabled');
//                         form['emailAuthSalt'].value = '';
//                         form['emailVerify'].setAttribute('disabled', 'disabled');
//                         break;
//                     case 'success':
//                         EmailWarning.show('이메일이 정상적으로 인증되었습니다.');
//                         form['emailAuthCode'].setAttribute('disabled', 'disabled');
//                         form['emailVerify'].setAttribute('disabled', 'disabled');
//                         form['password'].focus();
//                         break;
//                     default:
//                         EmailWarning.show('인증 번호가 올바르지 않습니다.');
//                         form['emailAuthCode'].focus();
//                         form['emailAuthCode'].select();
//                 }
//             } else {
//                 EmailWarning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해주세요.');
//             }
//         }
//     };
//     xhr.send(formData);
// });
//
// form['addressFind'].addEventListener('click', () => {
//     new daum.Postcode({
//         oncomplete: e => {
//             form.querySelector('[rel = "addressFindPanel"]').classList.remove('visible');
//             form['addressPostal'].value = e['zonecode']; // addressPostal에 zonecode 값 전달
//             form['addressPrimary'].value = e['address']; // addressPrimary에 address 값 전달
//             form['addressSecondary'].value = ''; // 상세주소 자리 비어있음
//             form['addressSecondary'].focus(); // 상세주소 자리에 커서
//         }
//     }).embed(form.querySelector('[rel = "addressFindPanelDialog"]'));
//     form.querySelector('[rel = "addressFindPanel"]').classList.add('visible');
// });
//
// form.querySelector('[rel = "addressFindPanel"]').addEventListener('click', () => {
//     form.querySelector('[rel = "addressFindPanel"]').classList.remove('visible');
// });