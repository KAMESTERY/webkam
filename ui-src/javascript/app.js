
import { MDCTopAppBar } from '@material/top-app-bar/index'
const topAppBar = new MDCTopAppBar(document.querySelector('.mdc-top-app-bar'));

console.log('hello style');

import {MDCRipple} from '@material/ripple';
const ripple = new MDCRipple(document.querySelector('.foo-button'));

import {MDCTextField} from '@material/textfield';

const username = new MDCTextField(document.querySelector('.username'));
const password = new MDCTextField(document.querySelector('.password'));