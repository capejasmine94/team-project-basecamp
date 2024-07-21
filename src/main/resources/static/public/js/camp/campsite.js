//강제성이 있는 로그인 팝업을 띄워주는 기능입니다.
function checkMustLoginCampsite() {
    const url ='/api/camp/checkNeedLogin';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        if(response.data.needLogin)
          location.href = '/seller/login';
    });
 }