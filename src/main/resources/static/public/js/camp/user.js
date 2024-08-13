let campsiteList;
let curArea;
let userInfo;

function initSession() {
    const url ='/api/camp/initSession';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        console.log(response);
        campsiteList = response.data.campsiteList;
        refreshCampsite();
    });
}

function getCampsiteList(searchWord, category) {
    console.log(searchWord);
    console.log(category);
    const url =`/api/camp/searchCampsite?searchWord=${searchWord}&category=${category}`;
    console.log(url);
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        console.log(response);
        campsiteList = response.data.campsiteList;
        refreshCampsite();
    });
}

function refreshCampsite() {
    const campList = document.getElementById('campsiteList');
    campList.innerHTML = '';
    for(let campsite of campsiteList) {
        const template = document.createElement('div');
        template.classList.add('row','mx-1','py-4','border-bottom');
        template.innerHTML = `
        <a href="/camp/campsite?campsite_id=${campsite.dto.id}">
            <div class="col">
                <div class="row">
                    <div class="col">
                        <img class="img-fluid px-0 rounded-3" src="/images/${campsite.mainImages[0].location}" alt="캠핑장 사진" style="width: 100%; height: 20em;">
                    </div>
                </div>
            <div class="row justify-content-between align-items-center">
                    <div class="col-8 ms-1">
                        <div class="row mt-2">
                            <span class="col fs-4 fw-semibold ellipsis">${campsite.dto.camp_name}</span> 
                        </div>
                    </div>
                    <div class="col-auto">
                        <div class="row mt-2 justify-content-end">
                            <div class="col-auto p-0">
                                <i class="bi bi-star fs-5"></i>
                            </div>
                            <!-- <div class="col-auto p-0 mx-2 text-start">
                                0
                            </div> -->
                        </div>
                    </div>
                </div>
                <div class="row ps-1 mt-1">
                    <div class="col-12 new-fs-9 fw-semibold ellipsis text-secondary">
                        ${campsite.dto.address} ${campsite.dto.detail_address}
                    </div>
                </div>
                <div class="row ps-1 mt-1 flex-column">
                    <div class="col pe-0 badgeList">
                    </div>
                    <div class="col-auto fw-semibold mt-4 text-end fs-4">
                        <span class="moneyInput text-primary">${campsite.minPrise}</span> <span class="fs-5">원</span>
                    </div>
                </div>
            </div>
        </a>
        `;
        for(let showCategory of campsite.showCategory) {
            const badgeList = template.querySelector('.badgeList');

            const badge = document.createElement('span');
            badge.classList.add('badge','text-bg-secondary','me-1');
            badge.innerText = showCategory.name;
            badgeList.appendChild(badge);
        }
        campList.appendChild(template);
    }
    if(document.getElementById('campsiteList').innerHTML==''){
        document.getElementById('emptyCampList').classList.remove('d-none');
    }else
        document.getElementById('emptyCampList').classList.add('d-none');
    addColonMoneyInput();
}

function selectCamp() {
    const id = parseInt(new URLSearchParams(window.location.search).get('campsite_id'));
    for(const camp of campsiteList){
        if(camp['dto']['id'] == id){
            const curCampsite = camp;
            return curCampsite;
        }
    }
}

function back() {
    console.log('clicked'); 
    history.back();
}

function initUser() {
    url = '/api/camp/initUser';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        //로그인 체크
        userInfo = response.data.sessionUser;
    });
}

function currentUser() {
    return userInfo;
}
function currentArea() {
    return curArea;
}

function selectArea(id) {
    const url ='/api/camp/selectArea?area_id=' + id;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        curArea = response.data.curAreaUser;
        refreshSelectedArea(curArea);
    });
}

function showArea(id) {
    const url ='/api/camp/selectArea?area_id=' + id;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        refreshSelectedArea(response.data.curAreaUser);
    });
}

function randomNumber() {
    return Math.round((Math.random() * 3) + 1);
}

function refreshSelectedArea(area) {
    const area_mainImages = document.getElementById('area_mainImages');
    if(area_mainImages == null) return;
    let active = false;
    area_mainImages.innerHTML = '';
    for(const mainImage of area.mainImages){
        const container = document.createElement('div');
        container.classList.add('carousel-item');
        if(!active) {
            container.classList.add('active');
            active = true;
        }
        const mainImageTemplate = document.createElement('img');
        mainImageTemplate.classList.add("img-fluid",'p-0');
        mainImageTemplate.style.height='15em';
        mainImageTemplate.style.width='100%';
        mainImageTemplate.setAttribute('src',`/images/${mainImage.location}`);
        container.appendChild(mainImageTemplate);
        area_mainImages.appendChild(container);
    }
    const area_name = document.getElementById('area_name');
    const popupSite = document.getElementById('popupSite-title');
    area_name.innerText = area.dto.name;
    popupSite.innerText = area.dto.name;

    const area_size = document.getElementById('area_size');
    area_size.innerText = area.dto.size_x + "(m) x " + area.dto.size_y + "(m)";

    const area_description = document.getElementById('area_description');
    area_description.innerText = area.dto.description;

    const area_people = document.getElementById('area_people');
    area_people.innerText = area.dto.max_people + ' 명';

    // const area_adult = document.getElementById('area_adult');
    // area_adult.innerText = area.dto.adult_count + ' 명';

    // const area_kid = document.getElementById('area_kid');
    // area_kid.innerText = area.dto.kid_count + ' 명';

    // const area_car = document.getElementById('area_car');
    // area_car.innerText = area.dto.car_count + ' 대';

    const area_prise = document.getElementById('area_prise');
    area_prise.innerText = area.dto.prise;

    const area_categoryList = document.getElementById('area_categoryList');
    area_categoryList.innerHTML = '';
    for(const category of area.category) {
        const template = document.createElement('span');
        template.classList.add('badge','text-bg-secondary','me-1');
        template.innerText = category.name;
        area_categoryList.appendChild(template);
    }

    const area_mapImage = document.getElementById('area_mapImage');
    area_mapImage.setAttribute('src','/images/' + area.dto.map_image);
    area_mapImage.onclick = function(){ openOriginalInNewWindow('/images/' + area.dto.map_image); };

    const curCampsite = selectCamp();

    const area_add_adult = document.getElementById('area_add_adult');
    area_add_adult.innerText = curCampsite.dto.adult_pay;

    const area_add_kid = document.getElementById('area_add_kid');
    area_add_kid.innerText = curCampsite.dto.kid_pay;

    const area_add_car = document.getElementById('area_add_car');
    area_add_car.innerText = curCampsite.dto.car_pay;

    addColonMoneyInput();
}
