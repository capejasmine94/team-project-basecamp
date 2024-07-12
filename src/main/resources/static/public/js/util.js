function onResize(){
    const topNaviSeller = document.getElementById("topNaviSeller");
    console.log(topNaviSeller);
    const height = topNaviSeller.offsetHeight;
    console.log(height);
    const leftMenu = document.getElementById("leftMenu");
    leftMenu.style.paddingTop = height + 'px';
}

let timeoutId;

function handleScroll() {
  clearTimeout(timeoutId);

  if (!document.body.classList.contains('scroll-visible')) {
    document.body.classList.add('scroll-visible');
  }

  timeoutId = setTimeout(() => {
    document.body.classList.remove('scroll-visible');
  }, 1000); // 1초 동안 스크롤이 없으면 스크롤바를 숨김
}

window.addEventListener('scroll', handleScroll);
// window.addEventListener("resize",function(){onResize();});