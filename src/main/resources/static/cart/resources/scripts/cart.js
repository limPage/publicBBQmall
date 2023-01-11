const cartForm = window.document.getElementById('cartForm');
const buyButton = window.document.querySelector('.buy-button');
const id = window.document.getElementById('id');
const name = window.document.getElementById('userName');
const contact = window.document.getElementById('contact');
const email = window.document.getElementById('email');

buyButton.addEventListener('click', () => {
    alert('결제버튼 클릭');
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('id', id.value);
    formData.append('name', name.value);
    formData.append('contact', contact.value);
    formData.append('email', email.value);

    /*
    * `index` int not null auto_increment,
`id` varchar(100) not null,
`name` varchar(20) not null,
`contact` int not null,
`address_postal` varchar(100) not null,
`address_primary` varchar(100) not null,
`address_secondary` varchar(100) not null,
`order_time` date default now(),
`product_index` int unsigned not null,
`product_name` varchar(100) not null,
`price` int not null,
`order_amount` int not null,*/

    xhr.open('POST', './cart');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText)
                switch (responseObject['result']) {
                    case 'success':
                        alert('삭제 성공');
                        break;
                    default:
                        alert("알 수 없는 이유로 상품을 삭제하지 못하였습니다\n\n잠시 후 다시 시도해 주세요.");
                        break;

                }
            } else {

            }
        }
    };
    xhr.send(formData);
});
