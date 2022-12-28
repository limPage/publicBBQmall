//const form = window.document.getElementById('form');
const cartButton = window.document.getElementById('cart');
const productForm = window.document.getElementById('productsForm');

let addButton = window.document.getElementById('add');
let minButton = window.document.getElementById('min');

let num = parseInt(productForm.querySelector('.quantity-number').value);

cartButton.addEventListener('click', ()=> {
    alert('장바구니');
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