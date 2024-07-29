let sessionInfo;
let currentCampsite;
let currentArea;

// import { checkLogin, checkMustLogin } from "/public/js/utils.js";

function init() {
    let url ='/api/user/checkLogin';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        if(!response.data.needLogin){
            sessionInfo = response.data.userInfo;
            console.log('sessionInfo : ' + sessionInfo);
        }
    });
    // 현재 URL의 쿼리 매개변수 가져오기
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    // 특정 매개변수 값 가져오기 (예: 'name' 매개변수)
    const id = urlParams.get('id');
    url = '/api/camp/getCampsiteById?id=' + id;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        currentCampsite = response.data.cmapsite;
        console.log('campsite : ' + currentCampsite);
    });
}

init();