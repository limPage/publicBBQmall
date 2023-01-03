//const form = window.document.getElementById('form');
const cartButton = window.document.getElementById('cart');
const productForm = window.document.getElementById('productsForm');
const pid = window.document.getElementById('pid');
const cartIndex = window.document.getElementById('cartIndex');
const quantityNumber = window.document.getElementById('quantityNumber');

let addButton = window.document.getElementById('add');
let minButton = window.document.getElementById('min');

let buyButton = window.document.getElementById('buy');

let num = parseInt(productForm.querySelector('.quantity-number').value);

cartButton.addEventListener('click', ()=> {
    alert('장바구니');
    // const xhr = new XMLHttpRequest();
    // const formData = new FormData();
    // formData.append('cid', cartIndex.value);
    // xhr.open('GET', window.location.href);
    // xhr.onreadystatechange = () => {
    //     if (xhr.readyState === XMLHttpRequest.DONE) {
    //         if (xhr.status >= 200 && xhr.status < 300) {
    //             const responseObject = JSON.parse(xhr.responseText); // responseObject에 controller에서 받아온 값을 넣고
    //             switch (responseObject['result']) { // responseObject의 값을 switch할 때
    //                 case 'success':
    //                      const index = responseObject['cid'];
    //                      window.location.href = `cart?cid=${index}`;
    //                     break;
    //                     default:
    //                         break;
    //             }
    //         } else {
    //
    //         }
    //     }
    // };
    // xhr.send(formData);
});

addButton.addEventListener('click', () => {
    productForm['quantityNumber'].value =  ++num;
    return false;
});

minButton.addEventListener('click', () => {
    if(num <= 0) {
        return false;
    }
    productForm['quantityNumber'].value = --num;
    return false;
});

buyButton.addEventListener('click', () => {
    window.location.href='./cart?pid='+pid.value+'&quantity='+quantityNumber.value;

});