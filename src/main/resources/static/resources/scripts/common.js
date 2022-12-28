const form = window.document.getElementById('form');

// const Cover = {
//     show : (text) => {
//         const cover = window.document.getElementById('cover');;
//         cover.querySelector('[rel="text"]').innerText = text;
//         cover.classList.add('visible');
//     },
//     hide : () => {
//         window.document.getElementById('cover').classList.remove('visible');
//     }
// };
//
// const Warning = {
//     getElement:()=> form.querySelector('[rel="warningRow"]'),
//     show : (text) => {
//         const warningRow = Warning.getElement();
//         warningRow.querySelector('.text').innerText = text;
//         warningRow.classList.add('visible');
//     },
//     hide : () => {
//         const warningRow = Warning.getElement();
//         warningRow.classList.remove('visible');
//     }
// }
//
// let categoryTitle = form.querySelectorAll('[rel = "categoryTitle"]');
// for(let i = 0; i < categoryTitle.length; i++) {
//     let item = categoryTitle.item(i);
//     item.addEventListener('click', () => {
//
//
//         const xhr = new XMLHttpRequest();
//         const formData = new FormData();
//         formData.append('cid', form['cid'].value);
//
//         xhr.open('GET', './category?cid='+cid);
//         xhr.onreadystatechange = () => {
//             if (xhr.readyState === XMLHttpRequest.DONE) {
//                 Cover.hide();
//                 if (xhr.status >= 200 && xhr.status < 300) {
//                     const responseObject = JSON.parse(xhr.responseText); // responseObject에 controller에서 받아온 값을 넣고
//                     switch (responseObject['result']) { // responseObject의 값을 switch할 때
//                         case 'success':
//                             const index = responseObject['index'];
//                             window.location.href = `category?cid=${index}&sid=${sidItem.value}`;
//                             break;
//                         default:
//                             Warning.show('알 수 없는 이유로 게시글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//                     }
//                 } else {
//                     Warning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//                 }
//             }
//         };
//         xhr.send(formData);
//     });
// }