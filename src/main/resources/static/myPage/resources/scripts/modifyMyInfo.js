

window.document.getElementById('passwordCheckButton').addEventListener("click",()=> {


    if (window.document.getElementById('passwordCheck').value === '') {

        alert("비밀번호를 입력해주세요.");
        // form['id'].focus();
        form['password'].select();

        return;
    }

    Cover.show('비밀번호 확인중 입니다.');

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('id', window.document.getElementById('id').value);
    formData.append('password',  window.document.getElementById('passwordCheck').value);
    xhr.open('POST', './modifyMyInfo');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
            Cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);//{resutl:suceess로 가져온것을 { \n result \t 식으로 만듬}

                switch (responseObject['result']) {
                    case 'success':

                        alert('로그인 성공');
                        break;

                    default:
                        alert('로그인 하지 못하였습니다.\n이메일과 비밀번호를 확인해주세요.')
                }
            } else {
                alert('서버와 통신하지 못하였습니다.')
            }
        }
    }
    xhr.send(formData);


})