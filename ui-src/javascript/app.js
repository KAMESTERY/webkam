window.onbeforeunload = function () {
    alert('function-called');
    window.scrollTo(0, 0);
};

// Instantiation
console.log('Starting import');
import {MDCTopAppBar} from '@material/top-app-bar/index';
import {MDCRipple} from '@material/ripple';
import {MDCTextField} from '@material/textfield';

const topAppBarElement = document.querySelector('.mdc-top-app-bar');
const topAppBar = new MDCTopAppBar(topAppBarElement);


// home
// global
console.log('Starting load');

new MDCRipple(document.querySelector('.cancel'));
const username = new MDCTextField(document.querySelector('.username'));
const password = new MDCTextField(document.querySelector('.password'));
const confirmPassword = new MDCTextField(document.querySelector('.confirm-password'));
console.log('Global Styles loaded');

// login
new MDCRipple(document.querySelector('.login'));
console.log('login page Styles loaded');

// register
const email = new MDCTextField(document.querySelector('.email'));
new MDCRipple(document.querySelector('.register'));
console.log('register page Styles loaded');
