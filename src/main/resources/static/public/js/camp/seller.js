let campsite = null;

let curArea = null;
let curPoint = null;

let camp_category = null;
let area_category = null;

let userInfo = null;
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
function currentUserInfo(){
    return userInfo;
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
    if(camp_category != null){
        for(const category of camp_category) {
            const button = document.getElementById('campCategory' + category.id);
            if(button == null) break;
            for(const selected of campsite.campCategory) {
                if(selected.id == category.id) {
                    button.checked = true;
                    break;
                }
            }
        }
    }
}

function sendSelectAreaToSession() {
    sessionStorage.setItem('curArea',curArea);
}

//=====================================================================================
// 팝업
//=====================================================================================
// 팝업 실행
function popupAccept(title,content,accept,action) {
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
 function popup(title,content,yes,no,action,noAction) {
    const node = document.getElementById('popup');
    bootstrap.Modal.getOrCreateInstance('#popup',{backdrop: 'true', keyboard: true})  
    let text = node.querySelector("#popup-title");
    text.innerText = title;
    text = node.querySelector("#popup-content");
    text.innerText = content;
    text = node.querySelector("#popup-no");
    text.classList.remove('d-none');
    text.innerText = no;
    text.onclick = noAction;
    text = node.querySelector("#popup-three");
    text.classList.add('d-none');
    text = node.querySelector("#popup-button");
    text.onclick = action;
    text.innerText = yes;
    bootstrap.Modal.getOrCreateInstance('#popup').show();
  }
  function popupClose() {
    bootstrap.Modal.getOrCreateInstance('#popup').hide();
  }
 // 로그인 팝업
 function loginPopup() {
    popup('로그인','로그인이 필요합니다.','예',function() {location.href = '/seller/login'; });
 }

// 로그인 팝업
function registerCampPopup() {
    popup('캠핑장 등록','우선 캠핑장을 등록해야합니다.','캠핑장 등록',function() { location.href = '/campsiteCenter/registerCamp'; });
 }

//=====================================================================================
// 선택
//=====================================================================================
// 구역 선택
function selectArea(id, onRefresh) {
    if(curArea != null) {
        popup('구역 저장','변경 내용을 저장하지 않았습니다. 저장하지 않고 페이지를 나가시겠습니까?','예','아니오',function(){
            const list = document.getElementById('area_' + curArea.dto.id);
            list.classList.remove('bg-light');
            for(const area of campsite.area){
                if(area.dto.id == id) {
                    curArea = area;
                    sendSelectAreaToSession();
                    const list = document.getElementById('area_' + curArea.dto.id);
                    list.classList.add('bg-light');
                    
                    onRefresh();

                    const id = document.getElementById('setID');
                    id.value = curArea.dto.id;
                    console.log(id.value);
                
                    if(area_category != null) {
                        for(const category of area_category) {
                            const button = document.getElementById('areaCategory' + category.id);
                            if(button == null) return;
                            for(const selected of curArea.category) {
                                if(selected.id == category.id) {
                                    button.checked = true;
                                    break;
                                }
                            }
                        }
                    }

                    refreshPointList();

                    const button = document.querySelector('#deleteAreaButton');
                    const request_delete = document.querySelector('#request_delete_area');
                    button.onclick = function(){
                        if(request_delete.innerText == request_delete.getAttribute('placeholder'))
                            location.href='/campsiteCenter/deleteAreaProcess?id=' + curArea.dto.id;
                        else
                        popupAccept('삭제 키워드 불일치', '삭제 키워드가 일치하지 않습니다.','예',popupClose);
                    }
                    const updateAreaView = document.getElementById('updateAreaView');
                    updateAreaView.classList.remove('d-none');
                    break;
                }
            }
            if(curArea == null) {
                const updateAreaView = document.getElementById('updateAreaView');
                updateAreaView.classList.add('d-none');
            }
            popupClose();
        },function() {
            popupClose();
        })
    }
    else{

        for(const area of campsite.area){
            if(area.dto.id == id) {
                curArea = area;
                sendSelectAreaToSession();
                const list = document.getElementById('area_' + curArea.dto.id);
                list.classList.add('bg-light');
    
                onRefresh();

                const id = document.getElementById('setID');
                id.value = curArea.dto.id;
                console.log(id.value);
            
                if(area_category != null) {
                    for(const category of area_category) {
                        const button = document.getElementById('areaCategory' + category.id);
                        if(button == null) return;
                        for(const selected of curArea.category) {
                            if(selected.id == category.id) {
                                button.checked = true;
                                break;
                            }
                        }
                    }
                }

                refreshPointList();

                const button = document.querySelector('#deleteAreaButton');
                const request_delete = document.querySelector('#request_delete_area');
                button.onclick = function(){
                    if(request_delete.value == request_delete.getAttribute('placeholder'))
                        location.href='/campsiteCenter/deleteAreaProcess?id=' + curArea.dto.id;
                    else
                    popupAccept('삭제 키워드 불일치', '삭제 키워드가 일치하지 않습니다.','확인',popupClose);
                }
                const updateAreaView = document.getElementById('updateAreaView');
                updateAreaView.classList.remove('d-none');
                break;
            }
        }
        if(curArea == null) {
            const updateAreaView = document.getElementById('updateAreaView');
            updateAreaView.classList.add('d-none');
        }
    }
    
}

function refreshUpdateAreaView() {
    const updateAreaView = document.getElementById('updateAreaView');

    // 이름
    let value = updateAreaView.querySelector('#update_name');
    value.value = curArea.dto.name;

    // 가격
    value = updateAreaView.querySelector('#prise');
    value.value = curArea.dto.prise;

    // 크기
    value = updateAreaView.querySelector('#size_x');
    value.value = curArea.dto.size_x;

    value = updateAreaView.querySelector('#size_y');
    value.value = curArea.dto.size_y;

    // 설명
    value = updateAreaView.querySelector('#description');
    value.value = curArea.dto.description;

    // 이미지
      
    // 구역 정보

    // 포인트

    // 삭제문구
    value = updateAreaView.querySelector('#request_delete_area');
    value.setAttribute('placeholder',`샤르르 뿡뿡!! 뿍짝뿍짝 사람이 되라~~ 얍!!`);

    updateAreaView.classList.remove('d-none');
}


function setUploadedMapImage() {
    const location = '/images/' + curArea.dto.map_image;
    // 미리보기 이미지 요소 생성
    const preview = createPreviewImage(location, 'mapImage', false);
    preview.classList.add('isAlreadyUploaded');
    // imgList에 미리보기 이미지 추가
    const imgList = document.getElementById('imgList_mapImage');
    imgList.innerHTML='';
    imgList.appendChild(preview);
}
function setUploadedMainImages() {
    const dataList = curArea.mainImages;
    const imgList = document.getElementById('imgList_mainImage');
    imgList.innerHTML='';
    for(const image of dataList) {
        const location = '/images/' + image.location;// location 경로에 있는 이미지를 파일 객체로 생성
        // 미리보기 이미지 요소 생성
        const preview = createPreviewImage(location, 'mainImage', false);
        preview.classList.add('isAlreadyUploaded');
        // imgList에 미리보기 이미지 추가
        imgList.appendChild(preview);
    }
}
                            
//=====================================================================================
// 포인트
//=====================================================================================
// 포인트 생성
function registerPoint() {
    let url = '/api/campsiteCenter/registerPointProcess?';

    const form = document.getElementById('registerPointView');
    url = url + `point_name=${form.querySelector('#point_name').value}`
    url = url + `&point_count=${form.querySelector('#point_count').value}`
    url = url + `&area_id=${curArea.dto.id}`;

    fetch(url)
    .then(response => response.json())
    .then((response) => {
        for(const area of response.data.campsite.area){
            if(area.dto.id == curArea.dto.id) {
                curArea = area;
                refreshPointList();
                return;
            }
        }
        
    });
}

// 포인트 하나 삭제
function deletePoint(point_id) {
    popup('포인트 삭제','해당 포인트를 삭제합니다. 계속하시겠습니까?','취소','삭제',popupClose, function(){deletePointPorcess(point_id)});
}

function deletePointPorcess(point_id){
    let url = '/api/campsiteCenter/deletePointProcess?point_id=' + point_id;
        fetch(url)
        .then(response => response.json())
        .then((response) => {
            for(const area of response.data.campsite.area){
                if(area.dto.id == curArea.dto.id) {
                    curArea = area;
                    refreshPointList();
                    break;
                }
            }
            
            popupClose();
            return;
        });
}

function refreshPointList() {
    console.log(curArea);
    const pointList = document.getElementById('pointList');
    pointList.innerHTML = '';
    for(const point of curArea.pointInfo.point){
        const pointView = document.createElement('div');
        pointView.classList.add('row','ps-4','py-1','border-bottom');
        pointView.innerHTML = 
        `<div class="col-1">
            <span id="text_name_point_${point.point_id}">${point.name}</span>
            <input id="editable_name_point_${point.point_id}" class="form-control d-none" name="update_point_name" type="text" maxlength="7" value="${point.name}">
         </div>
        </div>
        <div class="col-1 ms-2 me-5 text-end">
            <span id="text_countpoint_${point.point_id}">${point.point_count}</span>
            <input id="editable_countpoint_${point.point_id}" class="form-control d-none" name="update_point_count" type="number"  value="${point.point_count}">
        </div>
        <div class="col-3 ms-2 me-1 text-end text-primary"><i class ="bi bi-tools" id="button_${point.point_id}"></i></div>
        <div class="col-auto ms-2 me-5 text-end text-danger"><i class ="bi bi-trash" onclick="deletePoint(${point.point_id})"></i></div>`;
        const button = pointView.querySelector('#button_' + point.point_id);
        button.onclick = ()=>{ 
            editableText(`name_point_${point.point_id}`);
            editableText(`countpoint_${point.point_id}`);
        };
        pointList.appendChild(pointView);
    }
}