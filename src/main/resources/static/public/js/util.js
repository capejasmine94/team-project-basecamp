function onResize(){
    const topNaviSeller = document.getElementById("topNaviSeller");
    console.log(topNaviSeller);
    const height = topNaviSeller.offsetHeight;
    console.log(height);
    const leftMenu = document.getElementById("leftMenu");
    leftMenu.style.paddingTop = height + 'px';
}
// window.addEventListener("resize",function(){onResize();});