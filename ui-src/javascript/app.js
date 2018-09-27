
// import { MDCTopAppBar } from '@material/top-app-bar/index'
// const topAppBar = new MDCTopAppBar(document.querySelector('.mdc-top-app-bar'));

import {MDCRipple} from '@material/ripple';
import {MDCTextField} from '@material/textfield';

new MDCRipple(document.querySelector('.cancel'));
new MDCRipple(document.querySelector('.next'));


const username = new MDCTextField(document.querySelector('.username'));
const password = new MDCTextField(document.querySelector('.password'));

console.log('Styles loaded');
