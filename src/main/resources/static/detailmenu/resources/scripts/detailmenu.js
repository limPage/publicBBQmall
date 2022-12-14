const form = window.document.getElementById('productsForm');

const Warning = {
    show : (text) => {
        form.querySelector('[rel = "warningText"]').innerText = text;
        form.querySelector('[rel="warning"]').classList.add('visible');
    },
    hide : () => {
        form.querySelector('[rel="warning"]').classList.remove('visible');
    }
}

form['cart'].addEventListener('click', ()=> {
    alert('장바구니');
});
