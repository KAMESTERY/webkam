// ES6
import {MDCRipple} from '@material/ripple';
import {MDCTopAppBar} from '@material/top-app-bar/index';
import {MDCTextField} from '@material/textfield/component';
import {MDCList} from '@material/list';
import {MDCDrawer} from '@material/drawer';
import {MDCSelect} from '@material/select';
import {MDCChipSet} from '@material/chips';
import {MDCTextFieldIcon} from '@material/textfield/icon';

function loadScript(url, callback) {
  const script = document.createElement('script');
  script.type = 'text/javascript';
  if (script.readyState) {  // only required for IE <9
    script.onreadystatechange = function() {
      if (script.readyState === 'loaded' || script.readyState === 'complete') {
        script.onreadystatechange = null;
        callback();
      }
    };
  } else {  //Others
    script.onload = function() {
      callback();
    };
  }

  script.src = url;
  Array.from(document.getElementsByTagName('head')).map(el => el.appendChild(script));
}

document.addEventListener('DOMContentLoaded', () => {

  // Instantiation
  let topBarEl = document.getElementById('app-bar');
  if (topBarEl) {
    const topAppBar = new MDCTopAppBar(topBarEl);
    topAppBar.setScrollTarget(document.getElementById('main-content'));

    let drawerEl = document.querySelector('.mdc-drawer');
    if (drawerEl) {
      const drawer = MDCDrawer.attachTo(drawerEl);
      topAppBar.listen('MDCTopAppBar:nav', () => {
        drawer.open = !drawer.open;
      });
    }

  }

  const foos = [].map.call(document.querySelectorAll('.mdc-text-field'),
      function(el) {
        return new MDCTextField(el);
      });

  const chipSetEl = document.querySelector('.mdc-chip-set');
  if (chipSetEl) {
    return new MDCChipSet(chipSetEl);
  }

  const ripples_selector = '.mdc-button, .mdc-icon-button, .mdc-card__primary-action';
  const ripples = [].map.call(document.querySelectorAll(ripples_selector),
      function(el) {
        return new MDCRipple(el);
      });

  const listEl = document.querySelector('.mdc-list');
  if (listEl) {
    const list = MDCList.attachTo(listEl);
    list.wrapFocus = true;
  }

  const dropdowns = [].map.call(document.querySelectorAll('.mdc-select'),
      function(el) {
        return new MDCSelect(el);
      });

  let textFieldIconEl = document.querySelector('.mdc-text-field-icon');
  if (textFieldIconEl) {
    const icon = new MDCTextFieldIcon(textFieldIconEl);
  }
  // BLM Badge
  loadScript('https://makerbadge.s3.amazonaws.com/blmbadge.js', () => {
    BLMBadge.init({
      layout: 1,
      theme: 'dark',
      promoText: 'Send a donation ' + String.fromCodePoint(0x2192),
      promoLink: 'https://minnesotafreedomfund.org/',
      message: 'To be silent is to be complicit. Black lives matter.',
      title: '#BlackLivesMatter',
    });
  });
});
