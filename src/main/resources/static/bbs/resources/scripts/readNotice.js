window.document.querySelector('.announce-button').addEventListener('click',()=>{

    if (!confirm('공지글로 변경 하시겠습니까?')) {

        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    // formData.append('isImportant', 'true');
    xhr.open('PATCH', window.location.href+'&isImportant=true');//메소드방식은 아무능력이 없음  window.location.href 를쓰면 boardid bid를 안보내도댐
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
            Cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);//{resut:suceess로 가져온것을 { \n result \t 식으로 만듬}
                switch (responseObject['result']) {

                    case 'success':
                        alert("삭제성공");

                        break;
                    case 'no_such_article':
                        alert("게시물이 없습니다.");

                        break;

                    case 'not_signed':
                        alert("로그인 되어 있지 않습니다.");

                        break;
                    case 'not_allowed':
                        alert("권한이 없습니다.");

                        break;

                    default :
                        alert("알 수 없는 이유로 실패하였습니다.");
                        break
                }
            } else {
                alert("서버에 이상이 있습니다. 잠시후 시도해 주세요.");
            }
        }
    }
    xhr.send();

})


