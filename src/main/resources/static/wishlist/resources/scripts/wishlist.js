const cartForm = window.document.getElementById('cartForm');
const deleteButton = window.document.getElementById('deleteButton');
let wishlistIndex = window.document.querySelectorAll('.wishlist-index');
let button = window.document.querySelectorAll('.delete-button');

for (let i = 0; i < button.length; i++) {
    button.item(i).addEventListener('click', () => {
        const xhr = new XMLHttpRequest();
        const formData = new FormData();
        formData.append('wishlistIndex', wishlistIndex.item(i).value);

        xhr.open('DELETE', './wishlist');
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseObject = JSON.parse(xhr.responseText)
                    switch (responseObject['result']) {
                        case 'success':
                            alert('삭제 성공');
                            location.reload()
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
}

