let areaList = [];
let selectList = [];
let pointList = [];
let selectedArea = new Map;
let areaCategoryList = [];
function initArea() {
    const url ='/api/camp/getAreaInfoList';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        areaList = response.data.areaList;
        areaCategoryList = response.data.areaCategory; 
        initList();
    });
}

function insertArea() {
    const url ='/api/camp/insertArea';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        selectedArea = response.data.selectedArea;
        initList();
        select(selectedArea);
    });
}

function insertPoint() {
    if(pointList.length >= 10) {
        
    }
    const url ='/api/camp/insertPoint?id=' + selectedArea.dto.id;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        pointList = response.data.getPointList;
        initArea();
        initPoint();
    });
}

function select(selected) {
    selectedArea = selected;
    console.log(selectedArea);
    const selectCategory = selectedArea.selectAreaCategory;
    const detail = document.getElementById('showDetail');
    document.querySelector('#description').value = selected.dto.description;
    document.getElementById('name').value = selected.dto.name;
    document.getElementById('prise').value = selected.dto.prise;
    document.getElementById('size_x').value = selected.dto.size_x;
    document.getElementById('size_y').value = selected.dto.size_y;
    document.querySelector('#max_ordertime').value = selected.dto.max_ordertime;
    document.querySelector('#min_ordertime').value = selected.dto.min_ordertime;
    document.querySelector('#request_delete_area').placeholder = `'${selected.dto.name}' 구역을 삭제하겠습니다`;
    detail.classList.remove('d-none');
    pointList = selectedArea.points;
    const list = document.getElementById('areaCategoryList');
    list.innerHTML = '';
    for(let category of areaCategoryList){
        const categoryTemplate = document.createElement('div');
        categoryTemplate.classList.add("col-auto","mt-2","px-0");
        const contain = selectCategory.includes(category.id);
        const html = 
        `<input name="area_category" type="checkbox" class="btn-check" id="category_${category.id}" value="${category.id}" autocomplete="off" ${contain ? "checked" : ""}>
        <label class="btn ms-2 mb-2 rounded-5 btn-outline-primary" for="category_${category.id}">${category.name}</label>`
        categoryTemplate.innerHTML = html;
        list.appendChild(categoryTemplate);
    }
    initPoint();
}

function deletePoint(data) {
    const url =`/api/camp/deletePoint?id=${data.id}&area=${selectedArea.dto.id}`;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        pointList = response.data.getPointList;
        initArea();
        initPoint();
    });
}

function checkUpdatedContent(input,text) {
    if(input.value != text.innerText){
        document.getElementById('saveStateButton').disabled = false;   
    }
}

function initPoint() {
    const points = document.querySelector('#pointList');
    points.innerHTML = "";
    for(let i = 0; i < pointList.length; ++i){
        const point = pointList[i];
        const container = document.createElement('div');
        container.classList.add('row','pb-2','justify-content-center');
        container.innerHTML = `
            <form id="updatePointForm">
                <div class="col mx-2">
                    <div class="row py-2 border-bottom align-items-center">
                            <input type="hidden" name="id" value="${point.id}">
                            <input type="hidden" name="area_id" value="${point.area_id}">
                            <div class="col-1 text-center ms-2 me-5">
                                <span id="pointNameText">${point.name}</span>
                                <input class="form-control text-end p-1 d-none" name="name" type="text" id="pointName">
                            </div>
                            <div class="col-1 me-5">
                                <div class="text-center" id="pointCountText">${point.point_count}</div>
                                <input class="form-control text-end p-1 d-none" name="point_count" type="number" id="pointCount">
                            </div>
                        <div class="col-2 ms-5 ps-5">
                            <i class="bi bi-pencil-square text-primary ms-3" id="pointUpdate_${point.id}"></i>
                            <i class="bi bi-trash text-danger ms-3" id="pointDelete_${point.id}"></i>
                        </div>
                    </div>
                </div>
            </form>
        `;
        const name = container.querySelector(`#pointName`);
        const count = container.querySelector(`#pointCount`);
        const edit = container.querySelector(`#pointUpdate_${point.id}`);
        const del = container.querySelector(`#pointDelete_${point.id}`);
        name.value = point.name;
        count.value = point.point_count;
        del.onclick = function() {
            deletePoint(pointList[i]);
        }
        edit.onclick = function() {
            updatePoint(container);
        }
        points.appendChild(container);
    }
}

function updatePoint(container) {
    const name = container.querySelector(`#pointName`);
    const count = container.querySelector(`#pointCount`);
    const nameText = container.querySelector(`#pointNameText`);
    const countText = container.querySelector(`#pointCountText`);
    if(name.classList.contains('d-none')) {
        name.classList.remove('d-none');
        count.classList.remove('d-none');
        nameText.classList.add('d-none');
        countText.classList.add('d-none');
    }
    else{
        const form = container.querySelector('#updatePointForm');
        const formData = new FormData(form);
        const url = "/api/camp/updatePoint";
        fetch(url, {
             method: "post",
             body: formData
        })
        .then(response => response.json())
        .then(response => {
            initArea();
            nameText.innerText = name.value;
            countText.innerText = count.value;
            name.classList.add('d-none');
            count.classList.add('d-none');
            nameText.classList.remove('d-none');
            countText.classList.remove('d-none');
        });
    }
}

function initList() {
    const list = document.getElementById('areaList');
    list.innerHTML ='';
    selectList = [];
    for(let i = 0; i < areaList.length; i++) {
        areaInfo = areaList[i];
        const container = document.createElement('div');
        container.classList.add('row', 'py-2','border-bottom','align-items-center');
        container.id='area_' + areaInfo.dto.id;
        const name = document.createElement('div');
        name.classList.add('col-4', 'fw-semibold');
        name.innerText = areaInfo.dto.name;
        container.appendChild(name);
        
        const prise = document.createElement('div');
        prise.classList.add('col-2','me-5','text-end');
        const priseValue = document.createElement('span');
        priseValue.innerText = areaInfo.dto.prise; 
        const priseName = document.createElement('span');
        priseName.classList.add('ms-1');
        priseName.innerText = '원';
        prise.appendChild(priseValue);
        prise.appendChild(priseName);
        container.appendChild(prise);

        const point = document.createElement('div');
        point.classList.add('col-2','me-5','text-end');
        const pointValue = document.createElement('span');
        pointValue.innerText = areaInfo.pointCount; 
        const pointName = document.createElement('span');
        pointName.classList.add('ms-1');
        pointName.innerText = '개';
        point.appendChild(pointValue);
        point.appendChild(pointName);
        container.appendChild(point);
    
        const buttonCtn = document.createElement('div');
        buttonCtn.classList.add('col','text-end','ms-5','ps-5');
        const button = document.createElement('button');
        button.setAttribute('type','button');
        button.classList.add('btn','btn-outline-primary','px-4');
        button.innerText = '관리';
        button.onclick = function(){ 
            for(let con of selectList) {
                con.classList.remove('bg-light');
            }
            select(areaList[i]);
         }
        buttonCtn.appendChild(button);
        container.appendChild(buttonCtn);
        container.onclick = function(){ 
            for(let con of selectList) {
                con.classList.remove('bg-light');
            }
            container.classList.add('bg-light');
            select(areaList[i]); 
        }
        selectList.push(container);
        list.appendChild(container);
    }
   
}

initArea();