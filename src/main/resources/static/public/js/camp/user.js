let campsiteList;
let curArea;

function initSession() {
    const url ='/api/camp/initSession';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        console.log(response);
        campsiteList = response.data.campsiteList;
    });
}

function selectCamp() {
    const id = parseInt(new URLSearchParams(window.location.search).get('campsite_id'));
    for(const camp of campsiteList){
        if(camp['dto']['id'] == id){
            curCampsite = camp;
            return curCampsite;
        }
    }
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
    area_mainImages.innerHTML = '';
    for(const mainImage of area.mainImages){
        const mainImageTemplate = document.createElement('img');
        mainImageTemplate.classList.add("d-block","w-100","p-0");
        mainImageTemplate.setAttribute('src',`images/${mainImage.location}`);
        area_mainImages.appendChild(mainImageTemplate);
    }
    const area_name = document.getElementById('area_name');
    const popupSite = document.getElementById('popupSite-title');
    area_name.innerText = area.dto.name;
    popupSite.innerText = area.dto.name;

    const area_size = document.getElementById('area_size');
    area_size.innerText = area.dto.size_x + "(m) x " + area.dto.size_y + "(m)";

    const area_description = document.getElementById('area_description');
    area_description.innerText = area.dto.description;


    const area_adult = document.getElementById('area_adult');
    area_adult.innerText = randomNumber() + ' 명';

    const area_kid = document.getElementById('area_kid');
    area_kid.innerText = randomNumber() + ' 명';

    const area_car = document.getElementById('area_car');
    area_car.innerText = randomNumber() + ' 대';

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

    const area_add_adult = document.getElementById('area_add_adult');
    area_add_adult.innerText = area.dto.adult_pay;

    const area_add_kid = document.getElementById('area_add_kid');
    area_add_kid.innerText = area.dto.kid_pay;

    const area_add_car = document.getElementById('area_add_car');
    area_add_car.innerText = area.dto.car_pay;

    addColonMoneyInput();
}