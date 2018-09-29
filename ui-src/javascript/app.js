
import {MDCTopAppBar} from '@material/top-app-bar/index';

// Instantiation
const topAppBarElement = document.querySelector('.mdc-top-app-bar');
const topAppBar = new MDCTopAppBar(topAppBarElement);

console.log('Starting import');
import {MDCRipple} from '@material/ripple';
import {MDCTextField} from '@material/textfield';

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
new MDCRipple(document.querySelector('.register'));
console.log('register page Styles loaded');
