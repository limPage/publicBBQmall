let editor;
ClassicEditor
    .create(form['content'],{
        simpleUpload: { //심플업로드는 오브젝트다.
            uploadUrl: './image'
        }
    })
    .then( e => editor =e); // e가 e2 > editor > form content .focus;

form['back'].addEventListener('click' , () => window.history.length <2 ? window.close() : window.history.back());


form.onsubmit = e =>{


    e.preventDefault();

    if(form['title'].value===''){
        alert('제목을 입력해 주세요.');
        form['title'].focus();
        return false;
    }
    if(editor.getData()===''){
        alert('내용을 입력해 주세요.');
        editor.focus();
        return false;
    }

    Cover.show('게시글 수정을 처리중 입니다.')
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    const index= window.document.getElementById('index').value;

    formData.append('title', form['title'].value);
    // formData.append('content', form['content'].value);
    formData.append('content', editor.getData());
    formData.append('index',index )

    xhr.open('PATCH', './modifyNotice');//  window.location.href 를쓰면 boardid bid를 안보내도댐
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
            Cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);//{resutl:suceess로 가져온것을 { \n result \t 식으로 만듬}

                switch (responseObject['result']) {
                    case 'success':

                        alert('작성 성공');

                        window.location.href=`http://localhost:8080/board/`;//자바스크립트문법


                        break;

                    case 'not_login':
                      alert('로그인이 되었는지 확인후 다시 시도해 주세요.');
                        break;

                    case 'not_allowed':
                        alert('게시글을 수정할 수 있는 권한이 없습니다.');
                        break;

                    case 'no_such_article':
                        alert('게시글이 존재하지 않습니다.');
                        break;

                    default:
                        alert('알 수 없는 이유로 게시글을 수정하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            }else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.')
            }
        }
    }
    xhr.send(formData);


};

