let areaList = [];
let selectList = [];
let pointList = [];
let selectedArea = new Map;

function initArea() {
    const url ='/api/camp/getAreaInfoList';
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        areaList = response.data.areaList;
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
    const url ='/api/camp/insertPoint?id=' + selectedArea.dto.id;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        pointList = response.data.getPointList;
        initPoint();
    });
}

function select(selected) {
    selectedArea = selected;
    const detail = document.getElementById('showDetail');
    document.getElementById('name').value = selected.dto.name;
    document.getElementById('prise').value = selected.dto.prise;
    document.getElementById('size_x').value = selected.dto.size_x;
    document.getElementById('size_y').value = selected.dto.size_y;
    document.querySelector('#max_ordertime').value = selected.dto.max_ordertime;
    document.querySelector('#min_ordertime').value = selected.dto.min_ordertime;
    detail.classList.remove('d-none');
    pointList = selectedArea.points;
    initPoint();
}

function deletePoint(data) {
    const url =`/api/camp/deletePoint?id=${data.id}&area=${selectedArea.dto.id}`;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        pointList = response.data.getPointList;
        initPoint();
    });
}

function initPoint() {
    const points = document.querySelector('#pointList');
    points.innerHTML = "";
    for(let i = 0; i < pointList.length; ++i){
        const point = pointList[i];
        const container = document.createElement('div');
        container.classList.add('row','pb-2','justify-content-center');
        container.innerHTML = `
            <div class="col mx-2">
                <div class="row py-2 border-bottom align-items-center">
                    <div class="col-1 text-center ms-2 me-5">
                        <span id="pointName_${point.id}"></span>
                        <i class="bi bi-pencil-square text-primary ms-3"></i>
                    </div>
                    <div class="col-1 me-5">
                        <input class="form-control text-end p-1" type="number" id="pointCount_${point.id}">
                    </div>
                    <div class="col-2 ms-5 ps-5">
                        <i class="bi bi-trash text-danger ms-3" id="pointDelete_${point.id}"></i>
                    </div>
                </div>
            </div>
        `;
        const name = container.querySelector(`#pointName_${point.id}`);
        const count = container.querySelector(`#pointCount_${point.id}`);
        const del = container.querySelector(`#pointDelete_${point.id}`);
        name.innerText = point.name;
        count.value = point.point_count;
        del.onclick = function() {
            deletePoint(pointList[i]);
        }

        points.appendChild(container);
    }
}

function initList() {
    if(areaList == null) return;

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