window.addEventListener('load', function () {
  let leftMenuButton = document.querySelector('.left-menu-link');

  leftMenuButton.addEventListener('click', function () {
    let leftMenu = document.querySelector('.main-content-block-left-info-panel');
    let cl = leftMenu.classList;
    if (cl.contains('open')) {
      let animation = leftMenu.animate([
        {width: '240px'},
        {width: 0}
      ], {duration: 100});

      animation.addEventListener('finish', function () {
        cl.remove('open');
      });
    } else {
      cl.add('open');

      leftMenu.animate([
        {width: 0},
        {width: '240px'}
      ], {duration: 100});
    }
  })
  let headerLeftMenu = document.querySelector('.left-menu');
  delegate(headerLeftMenu, '.left-menu-item', 'click', function () {
    let elem = this.closest('.left-menu');
    // hideAllDropdown();
    dropdown(this);
  })
  let boardMenu = document.querySelector('.main-board-menu-list');
  delegate(boardMenu, '.main-board-menu-list-item', 'click', function () {
    let elem = this.closest('.main-board-menu-list');
    // hideAllDropdown();
    dropdown(this);
  })
});

function hideAllDropdown() {
  let menuItems = document.querySelectorAll('.dropdown-menu');
  menuItems.forEach(el => {
    el.classList.remove('active');
    el.classList.add('hide');
  });
}

function showCurrentDropdown(elem) {
  let dropdownMenu = elem.querySelector('.dropdown-menu');
  let cl = dropdownMenu.classList;
  console.log(cl);
  if (dropdownMenu) {
    let cl = dropdownMenu.classList;
    console.log(cl)
    cl.add('active');
    cl.remove('hide');
  }
}

function dropdown(elem) {
  let dropdownMenu = elem.querySelector('.dropdown-menu');
  let showed;
  let cl;
  if (dropdownMenu) {
    cl = dropdownMenu.classList;
    showed = cl.contains('active');
  }
  hideAllDropdown();
  if(showed) {
    cl.add('hide');
    cl.remove('active');
  }
  if(!showed && cl !== null) {
    cl.add('active');
    cl.remove('hide');
  }
}

function delegate(box, selector, eventName, handler) {
  box.addEventListener(eventName, function (e) {
    let elem = e.target.closest(selector);

    if (elem !== null && box.contains(elem)) {
      handler.call(elem, e);
    }
  });
}



