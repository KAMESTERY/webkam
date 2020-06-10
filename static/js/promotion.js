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
