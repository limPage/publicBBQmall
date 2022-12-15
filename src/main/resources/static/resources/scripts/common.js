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

