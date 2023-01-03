const cartForm = window.document.getElementById('cartForm');

ranking.addEventListener('click', (e) => {
    e.preventDefault();
    alert('랭킹순');
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('cid', cid.value);
    formData.append('sid', rankingSid.value);
    xhr.open('GET', window.location.href);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                // const responseObject = JSON.parse(xhr.responseText); // responseObject에 controller에서 받아온 값을 넣고
                // switch (responseObject['result']) { // responseObject의 값을 switch할 때
                //     case 'success':
                //          const index = responseObject['index'];
                //          window.location.href = `category?cid=${index}`;
                //         break;
                //         default:
                //             break;
                //              alert('알 수 없는 이유로 게시글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                // }
            } else {

            }
        }
    };
    xhr.send(formData);
});