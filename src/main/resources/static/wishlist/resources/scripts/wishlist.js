const cartForm = window.document.getElementById('cartForm');
const deleteButton = window.document.getElementById('deleteButton');
const wid = window.document.getElementById('wid');


deleteButton.addEventListener('click', () => {
    alert('삭제버튼');
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('index', cartForm['index']); // commentIndex를 던지면 대댓글, index를 던지면 댓글
    xhr.open('DELETE', window.location.href);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText)
                switch (responseObject['result']) {
                    case 'success':
                        alert('삭제 성공');
                        break;
                    case 'no_such_comment':
                        alert('삭제하려는 댓글이 더 이상 존재하지 않습니다\n\n이미 삭제되었을 수도 있습니다.');
                        break;
                    case 'not_allowed':
                        alert('해당 댓글을 삭제할 권한이 없습니다.');
                        break;
                    default:
                        alert("알 수 없는 이유로 댓글을 삭제하지 못하였습니다\n\n잠시 후 다시 시도해 주세요.");
                        break;
                }
            } else {

            }
        }
    };
    xhr.send(formData);
});