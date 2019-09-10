// ES6
import {MDCRipple} from '@material/ripple';
import {MDCTopAppBar} from '@material/top-app-bar/index';
import {MDCTextField} from "@material/textfield/component";
import {MDCList} from "@material/list";
import {MDCSelect} from '@material/select';

document.addEventListener("DOMContentLoaded", () => {
    // Instantiation
    let topAppBarElement = document.getElementById('.mdc-top-app-bar');

    if (topAppBarElement) {
        const topAppBar = new MDCTopAppBar(topAppBarElement);
    }

    const foos = [].map.call(document.querySelectorAll('.mdc-text-field'), function (el) {
        return new MDCTextField(el);
    });


    const ripples_selector = '.mdc-button, .mdc-icon-button, .mdc-card__primary-action';
    const ripples = [].map.call(document.querySelectorAll(ripples_selector), function (el) {
        return new MDCRipple(el);
    });

    const listEl = document.querySelector('.mdc-list');
    if (listEl) {
        const list = MDCList.attachTo(listEl);
        list.wrapFocus = true;
    }

    const dropdowns = [].map.call(document.querySelectorAll('.mdc-select'), function (el) {
        return new MDCSelect(el);
    });

});
