let curFiles = new Map();
class UploaderData{ 
    constructor(file,query) {
        this.query = query;
        this.file = file;
    }
}


function createUploadStructure(name, multiple) {
    const container = document.getElementById('upload_' + name);
    const uploadMainImageDiv = document.createElement('div');
    uploadMainImageDiv.classList.add('row');

    const innerColDiv = document.createElement('div');
    innerColDiv.classList.add('col');

    const rowDiv = document.createElement('div');
    rowDiv.classList.add('row');

    const fileInput = document.createElement('input');
    fileInput.name = name;
    fileInput.type = 'file';
    fileInput.multiple = multiple;
    fileInput.accept = 'image/*';
    fileInput.id = name + '_input';
    fileInput.style.display = 'none';

    const label = document.createElement('label');
    label.classList.add('col', 'm-2', 'uploader-container');
    label.id = 'uploader';

    const innerRowDiv = document.createElement('div');
    innerRowDiv.classList.add('row', 'justify-content-center');

    const innerColAutoDiv = document.createElement('div');
    innerColAutoDiv.classList.add('col-auto', 'my-3');
    innerColAutoDiv.textContent = '클릭하여 이미지 업로드 / 이미지를 드래그하여 업로드';

    innerRowDiv.appendChild(innerColAutoDiv);
    label.appendChild(innerRowDiv);
    rowDiv.appendChild(fileInput);
    rowDiv.appendChild(label);

    const imgListDiv = document.createElement('div');
    imgListDiv.id = 'imgList_' + name;
    imgListDiv.classList.add('row');

    innerColDiv.appendChild(rowDiv);
    innerColDiv.appendChild(imgListDiv);
    uploadMainImageDiv.appendChild(innerColDiv);
    container.appendChild(uploadMainImageDiv);
    return container;
}

function setEventListener(uploader,input) {
    uploader.addEventListener('click', () => {
        input.click();
    });
    uploader.addEventListener('dragover', (e) => {
        e.preventDefault();
        e.stopPropagation();
        uploader.classList.add('dragover');
    });
    uploader.addEventListener('dragleave', (e) => {
        e.preventDefault();
        e.stopPropagation();
        uploader.classList.remove('dragover');
    });
}

function createPreviewImage(path, uploaderName, multiple) {
    const imgList = document.querySelector('#imgList_' + uploaderName);
    const preview = document.createElement('div');
    preview.classList.add('col-auto', 'px-0');

    // 드래그앤드롭/삭제버튼
    let deleteBtn;
    if(multiple){
        preview.setAttribute('draggable', true);
        preview.addEventListener('dragstart', handleDragStart);
        preview.addEventListener('dragover', handleDragOver);
        preview.addEventListener('drop', handleDrop);

        deleteBtn = document.createElement('button');
        deleteBtn.innerHTML = '&times;';
        deleteBtn.classList.add('btn', 'btn-danger', 'btn-sm', 'btn-delete');
        deleteBtn.type = 'button';
        deleteBtn.onclick = (event) => {
            event.preventDefault();
            if(!preview.classList.contains('isAlreadyUploaded')){
                const files = curFiles.get(uploaderName);
                const index = files.findIndex(data => data.query === preview);
                
                if (index !== -1) {
                    files.splice(index, 1);
                }
            }
            imgList.removeChild(preview);
        };
    }

    // 이미지 들어가는 곳
    const previewContainer = document.createElement('div');
    previewContainer.classList.add('preview-container');
    const img = document.createElement('img');
    img.src = path;
    img.classList.add('preview-image', 'm-2');
    
    // 통합
    previewContainer.appendChild(img);
    if(multiple)  previewContainer.appendChild(deleteBtn);
    preview.appendChild(previewContainer);

    return preview;
}

let dragSrcEl;

function handleDragStart(e) {
    dragSrcEl = this;
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', this.innerHTML);
}

function handleDragOver(e) {
    if (e.preventDefault) {
        e.preventDefault();
    }
    e.dataTransfer.dropEffect = 'move';
    return false;
}

function handleDrop(e) {

}



//이름  컨트롤러이름  맵이름   데이터이름  단일 여부
function setUploadedData(uploaderName, request, listName, dataName, multiple) {
    const url = '/api/' + request;
    fetch(url)
    .then(response => response.json())
    .then((response) => {
        const keys = listName.split('.');
        let dataList = response.data;
        
        keys.forEach(key => { dataList = dataList[key]; });
        console.log(dataList);
        
        if(!multiple){
            const location = '/images/' + dataList[dataName];
    
            // location 경로에 있는 이미지를 파일 객체로 생성
            fetch(location)
            .then(response => response.blob())
            .then(blob => {
                // 미리보기 이미지 요소 생성
                const preview = createPreviewImage(location, uploaderName, false);
                preview.classList.add('isAlreadyUploaded');
                // imgList에 미리보기 이미지 추가
                const imgList = document.getElementById('imgList_' + uploaderName);
                imgList.appendChild(preview);
                
                // // 경로 이미지를 file화 하여 curFiles에 추가
                // console.log("map : " + dataList[dataName]);
                // const file = new File([blob], dataList[dataName], { type: blob.type });
                // if(curFiles.has(uploaderName)){
                //     curFiles.get(uploaderName).push(new UploaderData(file,preview));
                // }
                // else {
                //     curFiles.set(uploaderName,[new UploaderData(file,preview)]);
                // }
            })
            .catch(error => console.error('Error fetching the image:', error));
        }
        else {
            console.log(dataList);
            //listName : campsiteInfo.mainImages
            const imgList = document.getElementById('imgList_' + uploaderName);
            for(const mainImage of dataList) {
                const location = '/images/' + mainImage[dataName];// location 경로에 있는 이미지를 파일 객체로 생성
                fetch(location)
                .then(response => response.blob())
                .then(blob => {
                    // 미리보기 이미지 요소 생성
                    const preview = createPreviewImage(location, uploaderName, false);
                    preview.classList.add('isAlreadyUploaded');
                    // imgList에 미리보기 이미지 추가
                    imgList.appendChild(preview);
                    
                    // 경로 이미지를 file화 하여 curFiles에 추가
                    // const file = new File([blob], mainImage[dataName], { type: blob.type });
                    // if(curFiles.has(uploaderName)){
                    //     curFiles.get(uploaderName).push(new UploaderData(file,preview));
                    //     console.error(curFiles);
                    // }
                    // else {
                    //     curFiles.set(uploaderName,[new UploaderData(file,preview)]);
                    //     console.error(curFiles);
                    // }
                })
                .catch(error => console.error('Error fetching the image:', error));
            }
        }
    })
    .then(()=>{onSubmitSendUploader();})
    .catch(error => {
        console.error('Error fetching data:', error);
    });
}
                            


function createUploader(name) {
    const container = createUploadStructure(name, false);
    const uploader = container.querySelector('#uploader');
    const input = container.querySelector('#'+ name + '_input');
    const imgList = container.querySelector('#imgList_' + name);

    //드래그 / 드롭으로 이미지 첨부
    setEventListener(uploader,input);
    input.addEventListener('change', handleFiles);
    uploader.addEventListener('drop', (e) => {
        e.preventDefault();
        e.stopPropagation();
        uploader.classList.remove('dragover');
        const files = e.dataTransfer.files;
        handleFiles({ target: { files } });
    });

    function handleFiles(event) {
        const fileList = Array.from(event.target.files);
        imgList.innerHTML = '';
        const file = fileList[0];
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = createPreviewImage(e.target.result, name, false);
            imgList.appendChild(preview);
            const uploaderData = new UploaderData(file,preview);
            curFiles.set(name, [uploaderData]);
        };
        reader.readAsDataURL(file);
    }
}





function createMultipleUploader(name, max) {
    const MAX_UPLOADS = max;
   
    const container = createUploadStructure(name, true);
    const uploader = container.querySelector('#uploader');
    const input = container.querySelector('#'+ name + '_input');
    const imgList = container.querySelector('#imgList_' + name);

    //드래그 / 드롭으로 이미지 첨부
    setEventListener(uploader,input);
    input.addEventListener('change', handleFiles);
    uploader.addEventListener('drop', (e) => {
        e.preventDefault();
        e.stopPropagation();
        uploader.classList.remove('dragover');
        const files = e.dataTransfer.files;
        handleFiles({ target: { files } });
    });
    
    function handleFiles(event) {
        const uploadedList = Array.from(event.target.files);
        const currentList = curFiles.has(name) ? curFiles.get(name) : [];
        const uploaderDataList = currentList;
        if (currentList.length + uploadedList.length > MAX_UPLOADS) {
            alert(`최대 ${MAX_UPLOADS}개의 이미지만 업로드할 수 있습니다.`);
            return;
        }
        uploadedList.forEach((file) => {
            if (!file.type.startsWith('image/') || currentList.some(f => f.name === file.name)) {
                alert('이미지 파일만 업로드할 수 있으며, 동일한 파일은 중복 업로드할 수 없습니다.');
                return;
            }
            const reader = new FileReader();
            reader.onload = function(e) {
                const preview = createPreviewImage(e.target.result, name, true);
                const isAlreadyUploadedElement = imgList.getElementsByClassName('isAlreadyUploaded');
                for(const node of isAlreadyUploadedElement){
                    imgList.removeChild(node);
                }
                imgList.appendChild(preview);
                const uploaderData = new UploaderData(file, preview);
                uploaderDataList.push(uploaderData);
            };
            reader.readAsDataURL(file);
        });
        curFiles.set(name, uploaderDataList);
        console.log(curFiles.get(name));
    }
}


function onSubmitSendUploader() {
    for (const key of curFiles.keys()) {
        const input = document.getElementById(key + '_input');
        const uploaderData = curFiles.get(key);
        
        // DataTransfer 객체 생성
        const dataTransfer = new DataTransfer();

        for (const data of uploaderData) {
           // 파일을 DataTransfer 객체에 추가
           dataTransfer.items.add(data.file);
        }

        // input 요소의 files 속성을 DataTransfer 객체로 설정
        input.files = dataTransfer.files;
        console.log(input.files);
    }
}