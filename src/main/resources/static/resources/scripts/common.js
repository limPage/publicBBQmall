const Cover = {
    show:(text) =>{
        const cover=window.document.getElementById('cover');
        cover.querySelector('[rel="text"]').innerText= text;
        cover.classList.add('visible');

    },
    hide:()=>{
        window.document.getElementById('cover').classList.remove('visible');
    }
};
window.document.getElementById('searchButton').addEventListener("click",()=>{

    alert("저희 BBQ몰을 사랑해주셔서 감사합니다.\n항상 고객님을 생각하는 마음으로 서비스 하겠습니다.");

    window.document.getElementById('searchText').value=null;
})