const form = window.document.getElementById('form');

const Warning = {
    show : (text) => {
        form.querySelector('[rel = "warningText"]').innerText = text;
        form.querySelector('[rel="warning"]').classList.add('visible');
    },
    hide : () => {
        form.querySelector('[rel="warning"]').classList.remove('visible');
    }
}


const Cover = {
    show : (text) => {
        const cover = window.document.getElementById('cover');;
        cover.querySelector('[rel="text"]').innerText = text;
        cover.classList.add('visible');
    },
    hide : () => {
        window.document.getElementById('cover').classList.remove('visible');
    }
};