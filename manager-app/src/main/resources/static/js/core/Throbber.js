let Throbber = {
    show() {
        let body = document.querySelector('body');
        let throbber = document.createElement('div');
        throbber.classList.add('spinner-border');
        body.appendChild(throbber);
    }
}