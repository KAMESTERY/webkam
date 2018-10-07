window.onbeforeunload = function () {
    window.scrollTo(0, 0);
};

// Instantiation
console.log('Starting import');
import {MDCRipple} from '@material/ripple';
import {MDCTextField} from '@material/textfield';
import {MDCTopAppBar} from "@material/top-app-bar";
import {MDCDrawer} from "@material/drawer";

// global
let topBarEl = document.getElementById('app-bar');
const drawer = MDCDrawer.attachTo(document.querySelector('.mdc-drawer'));
const topAppBar = new MDCTopAppBar(topBarEl);

topAppBar.setScrollTarget(document.getElementById('main-content'));
topAppBar.listen('MDCTopAppBar:nav', () => {
    drawer.open = !drawer.open;
});
console.log('Global Styles loaded');

// home
new MDCRipple(document.querySelector('.cancel'));
console.log('Starting load');

// login/register
const email = new MDCTextField(document.querySelector('.email'));
const password = new MDCTextField(document.querySelector('.password'));
const confirmPassword = new MDCTextField(document.querySelector('.confirm-password'));

new MDCRipple(document.querySelector('.register'));
new MDCRipple(document.querySelector('.login'));

console.log('login/register page Styles loaded');
