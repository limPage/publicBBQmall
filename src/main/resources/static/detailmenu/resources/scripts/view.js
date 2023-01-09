//const form = window.document.getElementById('form');
const cartButton = window.document.getElementById('cart');
const productForm = window.document.getElementById('productsForm');
const pid = window.document.getElementById('pid');
const cartIndex = window.document.getElementById('cartIndex');
const quantityNumber = window.document.getElementById('quantityNumber');
const price = window.document.getElementById('price');
const totalPrice = window.document.getElementById('totalPrice');

let addButton = window.document.getElementById('add');
let minButton = window.document.getElementById('min');

let buyButton = window.document.getElementById('buy');

let num = parseInt(productForm.querySelector('.quantity-number').value);

cartButton.addEventListener('click', ()=> {
    alert('장바구니');
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('productIndex', pid.value);
    formData.append('quantity', quantityNumber.value);
    xhr.open('POST', './wishlist');
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE) {
            if(xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText); // responseObject에 controller에서 받아온 값을 넣고
                switch(responseObject['result']) { // responseObject의 값을 switch할 때
                    case 'success':
                        const pid = responseObject['pid'];
                        window.location.href='./wishlist?pid='+pid.value+'&quantity='+quantityNumber.value;
                        alert('작성 성공');
                        break;
                    default:

                }
            } else {

            }
        }
    };
    xhr.send(formData);
    window.location.href='./wishlist?pid='+pid.value+'&quantity='+quantityNumber.value;
});

addButton.addEventListener('click', () => {
    productForm['quantityNumber'].value =  ++num;
    totalPrice.innerText = price.value * productForm['quantityNumber'].value;
    return false;
});

minButton.addEventListener('click', () => {
    if(num <= 0) {
        return false;
    }
    productForm['quantityNumber'].value = --num;
    totalPrice.innerText = price.value * productForm['quantityNumber'].value;
    return false;
});

buyButton.addEventListener('click', () => {
    window.location.href='./cart?pid='+pid.value+'&quantity='+quantityNumber.value;
});

