function onResize(){
    const topNaviSeller = document.getElementById("topNaviSeller");
    const height = topNaviSeller.offsetHeight;
    const leftMenu = document.querySelectorAll(".resizeMenu");
    for(const item of leftMenu)
      item.style.paddingTop = height + 'px';
}
window.addEventListener('DOMContentLoaded',function(){onResize();});
window.addEventListener("resize",function(){onResize();});