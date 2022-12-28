// form['back'].addEventListener('click', () => window.history.length < 2 ? window.close() : window.history.back());

const ranking = window.document.getElementById('ranking');
const cid = window.document.getElementById('cid');
const rankingSid=  window.document.getElementById('rankingSid');
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



// form['ranking'].addEventListener = () => {
//     Warning.hide();
//
//     const xhr = new XMLHttpRequest();
//     const formData = new FormData();
//     formData.append('index', form['index'].value);
//     formData.append('title', form['productTitle'].value);
//     formData.append('cid', form['cid'].value);
//     formData.append('price',form['goodsPrice'].value);
//     xhr.open('POST', './category');
//     xhr.onreadystatechange = () => {
//         if(xhr.readyState === XMLHttpRequest.DONE) {
//             Cover.hide();
//             if(xhr.status >= 200 && xhr.status < 300) {
//                 const responseObject = JSON.parse(xhr.responseText); // responseObject에 controller에서 받아온 값을 넣고
//                 switch(responseObject['result']) { // responseObject의 값을 switch할 때
//                     case 'not_allowed':
//                         Warning.show('게시글을 작성할 수 있는 권한이 없거나 로그아웃되었습니다. 확인 후 다시 시도해 주세요.');
//                         break;
//                     case 'success':
//                         Warning.hide();
//                         const index = responseObject['index'];
//                         window.location.href = `view?cid=${index}`;
//                         alert('작성 성공');
//                         break;
//                     default:
//                         Warning.show('알 수 없는 이유로 게시글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//
//                 }
//             } else {
//                 Warning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//             }
//         }
//     };
//     xhr.send(formData);
// };

