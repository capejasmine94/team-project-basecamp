let campsite = null;

let curArea = null;
let curPoint = null;

let camp_category = null;
let area_category = null;

//=====================================================================================
// 세션 초기화
//=====================================================================================
function initSession() {
    url = '/api/campsiteCenter/initSession';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        //로그인 체크
        if(response.data.campsiteInfo == null) {
            loginPopup();
            return;
        }

        //캠핑장 데이터 삽입
        campsite = response.data.campsiteInfo;

        //캠핑장 등록 체크
        if(campsite.dto.is_authenticated == 'F') {
            registerCampPopup();
            return;
        }
        
        //카테고리 데이터 삽입
        camp_category = response.data.category.camp;
        area_category = response.data.category.area;

        //카테고리 버튼
        initCategoryButtons();
    });
}
function getCampsite() { return campsite; }

function initRegisterCamp() {
    url = '/api/campsiteCenter/initSession';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        //로그인 체크
        if(response.data.campsiteInfo == null) {
            loginPopup();
            return;
        }

        //캠핑장 데이터 삽입
        campsite = response.data.campsiteInfo;
        
        //카테고리 데이터 삽입
        camp_category = response.data.campCategory;
    });
}

function initCategoryButtons(){
    for(const category of camp_category) {
        const button = document.getElementById('campCategory' + category.id);
        if(button == null) return;
        for(const selected of campsite.campCategory) {
            if(selected.id == category.id) {
                button.checked = true;
                break;
            }
        }
    }
}
//=====================================================================================
// 팝업
//=====================================================================================
// 팝업 실행
function popup(title,content,accept,action) {
    const node = document.getElementById('popup');
    bootstrap.Modal.getOrCreateInstance('#popup',{backdrop: 'static', keyboard: false})  
    let text = node.querySelector("#popup-title");
    text.innerText = title;
    text = node.querySelector("#popup-content");
    text.innerText = content;
    text = node.querySelector("#popup-no");
    text.classList.add('d-none');
    text = node.querySelector("#popup-button");
    text.onclick = action;
    text.innerText = accept;
    text = node.querySelector("#popup-three");
    text.classList.add('d-none');
    bootstrap.Modal.getOrCreateInstance('#popup').show();
 }

 // 로그인 팝업
 function loginPopup() {
    popup('로그인','로그인이 필요합니다.','예',function() {location.href = '/seller/login'; });
 }

// 로그인 팝업
function registerCampPopup() {
    popup('캠핑장 등록','우선 캠핑장을 등록해야합니다.','캠핑장 등록',function() { location.href = '/campsiteCenter/registerCamp'; });
 }