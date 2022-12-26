const important= window.document.getElementById('important').value;
const announceButton= window.document.querySelector('.announce-button');
const index= window.document.getElementById('index').value;

if(important==='false'){
    announceButton.innerText="공지해제"
}else announceButton.innerText="공지등록"



announceButton.addEventListener('click',()=>{

    if (!confirm('공지글로 변경 하시겠습니까?')) {

        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();

    formData.append('index', index );
    formData.append('important', important);

    // formData.append('isImportant', 'true');
    //http://localhost:8080/board/modifyNotice?nid=12
    xhr.open('PATCH', '/board/modifyNotice');
    //메소드방식은 아무능력이 없음  window.location.href 를쓰면 boardid bid를 안보내도댐
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
            Cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);//{resut:suceess로 가져온것을 { \n result \t 식으로 만듬}
                switch (responseObject['result']) {

                    case 'success':
                        if(important==='false'){
                            alert("공지를 해제하였습니다.");
                        }else {announceButton.innerText="공지등록"
                        alert("공지로 등록하였습니다.");}


                        window.location.href="/board/"

                        break;
                    case 'no_such_article':
                        alert("게시물이 없습니다.");
                        window.location.href = `/board/`;

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
    xhr.send(formData);

})


window.document.getElementById('deleteButton').addEventListener('click',e=>{
    e.preventDefault();

    if (!confirm('정말로 삭제하시겠습니까?')) {

        return;
    }

    Cover.show('처리중 입니다.\n잠시만 기다려 주세요.');

    const xhr = new XMLHttpRequest();
    // const formData = new FormData();
    // formData.append('index', index);
    //주소로 보내게 되면 폼데이터가 필요없다.
    xhr.open('DELETE', "/board/deleteNotice?nid="+index);//  window.location.href 를쓰면 boardid bid를 안보내도댐
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {//4 성공인게 아니고 작업의끝
            Cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);//{resut:suceess로 가져온것을 { \n result \t 식으로 만듬}

                switch (responseObject['result']) {
                    case 'success':
                        alert('삭제성공');
                        const bid = responseObject['bid'];
                        // window.location.href = `list?bid=` + commentForm['board'].value;
                        window.location.href = `/board/`;
                        break;

                    case 'not_allowed':
                        alert('로그아웃되었거나 권한이 없습니다.');
                        break;

                    case 'no_such_board':
                        alert('없는 게시물입니다.');
                        window.location.href = `/board/`;

                        break;

                    default:
                        alert('알 수 없는 이유로 실패 하였습니다. \n\n잠시후 다시 시도해 주세요.')
                        break;
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.')

            }

        }
    }
    xhr.send();//데이터가 없음


})



