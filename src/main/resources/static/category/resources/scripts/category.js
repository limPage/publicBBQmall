const form = window.document.getElementById('form');

const Warning = {
    show : (text) => {
        form.querySelector('[rel = "warningText"]').innerText = text;
        form.querySelector('[rel="warning"]').classList.add('visible');
    },
    hide : () => {
        form.querySelector('[rel="warning"]').classList.remove('visible');
    }
}

//form['back'].addEventListener('click', () => window.history.length < 2 ? window.close() : window.history.back());

form['ranking'].addEventListener('click', ()=>{
    console.log(form['ranking'].value);
    alert('ddd');

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('title', form['title'].value);
    formData.append('content', editor.getData());
    xhr.open('POST', window.location.href);
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE) {
            Cover.hide();
            if(xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText); // responseObject에 controller에서 받아온 값을 넣고
                switch(responseObject['result']) { // responseObject의 값을 switch할 때
                    case 'no_such_article':
                        Warning.show('게시글을 수정할 수 없습니다. 게시글이 존재하지 않습니다.');
                        break;
                    case 'not_allowed':
                        Warning.show('게시글을 수정할 수 있는 권한이 없거나 로그아웃되었습니다. 확인 후 다시 시도해 주세요.');
                        break;
                    case 'success':
                        const aid = responseObject['aid'];
                        window.location.href = `read?aid=${aid}`;
                        break;
                    default:
                        Warning.show('알 수 없는 이유로 게시글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');

                }
            } else {
                Warning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
});