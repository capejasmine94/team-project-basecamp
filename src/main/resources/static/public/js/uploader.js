let fileSaved = new Map();

function isWebView() {
    const userAgent = navigator.userAgent || navigator.vendor || window.opera;

    // User-Agent 검사
    const webViewUserAgents = [
        'WebView',
        'iPhone',
        'iPod',
        'iPad',
        'Android',
        'Mobile',
        'Safari', // 웹뷰는 종종 모바일 사파리나 안드로이드의 기본 브라우저를 가장합니다.
        'Chrome'  // 웹뷰는 종종 크롬을 가장합니다.
    ];

    for (let agent of webViewUserAgents) {
        if (userAgent.indexOf(agent) > -1) {
            return true;
        }
    }

    // 특정 기능의 존재 여부 확인
    if (window.ReactNativeWebView || window.flutter_inappwebview) {
        return true;
    }

    return false;
}

function createUploadStructure(name, multiple) {
    const uploadNameDiv = document.createElement('div');
    uploadNameDiv.classList.add('row');

    const colDiv = document.createElement('div');
    colDiv.classList.add('col', 'upload-container','px-0');

    const previewRow = document.createElement('div');
    previewRow.classList.add('row', 'preview', 'd-flex', 'flex-wrap','ms-2');

    const fileUploadDiv = document.createElement('div');
    const mobile = isWebView() ? '1' : '3' 
    fileUploadDiv.classList.add('col-auto','my-1','ms-' + mobile,'me-2','mt-2','mb-1');
    fileUploadDiv.classList.add('file-upload');

    const inputFile = document.createElement('input');
    inputFile.name = name;
    inputFile.type = 'file';
    inputFile.id = `customFile_${name}`; // 동적으로 ID 설정
    inputFile.lang = 'en';
    inputFile.multiple = multiple;

    fileUploadDiv.appendChild(inputFile);

    previewRow.appendChild(fileUploadDiv);

    const alertRow = document.createElement('div');
    alertRow.classList.add('row', 'alert', 'alert-danger', 'mx-1', 'mt-3');
    alertRow.id = `alert_${name}`; // 동적으로 ID 설정
    alertRow.innerText = '이미지 파일만 업로드할 수 있습니다.';

    colDiv.appendChild(previewRow);
    colDiv.appendChild(alertRow);

    uploadNameDiv.appendChild(colDiv);

    document.getElementById(`upload_${name}`).appendChild(uploadNameDiv);
    return uploadNameDiv;
}

function createUploader(name) {
    const container = createUploadStructure(name, false);
    const input = container.querySelector(`#customFile_${name}`);
    const preview = container.querySelector('.preview');
    const alert = container.querySelector(`#alert_${name}`);
    const upload = container.querySelector('.upload-container');

    input.style.display = 'none'; // 파일 입력 필드를 숨깁니다.

    const button = preview.querySelector('.file-upload');
    button.addEventListener('click', function() {
        input.click(); // 버튼 클릭 시 파일 입력 필드를 트리거합니다.
    });

    input.addEventListener('change', function() {
        preview.innerHTML = ''; // 이전 미리보기를 삭제합니다
        alert.style.display = 'none'; // 경고를 숨깁니다

        Array.from(this.files).forEach(file => {
            if (file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.draggable = true; // 드래그 가능 설정
                    img.classList.add('draggable'); // 클래스 추가
                    if (isWebView()) {
                        img.classList.add('col-auto', 'm-1', 'px-0'); // 클래스 추가
                    } else {
                        img.classList.add('col-auto', 'me-2', 'my-1', 'mt-2', 'mb-3'); // 클래스 추가
                    }
                    addDragAndDropHandlers(img); // 드래그 앤 드롭 핸들러 추가

                    img.addEventListener('click', function() {
                        input.click(); // 이미지 클릭 시 파일 입력 필드를 트리거합니다.
                    });

                    preview.appendChild(img);

                    // 이미지가 버튼 위에 겹쳐지도록 스타일 조정
                    button.classList.add('d-none')
                }
                reader.readAsDataURL(file);
            } else {
                alert.style.display = 'block'; // 경고를 표시합니다
            }
        });

        preview.appendChild(button);
    });
}

function createFileList(files) {
    const dataTransfer = new DataTransfer();
    files.forEach(file => {
        dataTransfer.items.add(file);
    });
    return dataTransfer.files;
}

function createMultipleUploader(name) {
    const container = createUploadStructure(name, true);
    const input = container.querySelector(`#customFile_${name}`);
    const preview = container.querySelector('.preview');
    const alert = container.querySelector(`#alert_${name}`);
    const upload = container.querySelector('.upload-container');

    input.addEventListener('change', function() {
        const button = preview.querySelector('.file-upload');
        upload.appendChild(button);
        preview.innerHTML = ''; // Clear previous previews
        alert.style.display = 'none'; // Hide alert
        if(!fileSaved.has(name)){
            fileSaved.set(name, []);
        }
        Array.from(this.files).forEach(file => {
            if (file.type.startsWith('image/')) {
                fileSaved.get(name).push(file);
            } else {
                alert.style.display = 'block'; // Show alert
            }
        });
        updatePreview(name, preview);
        updateFileInput(name, input);
        preview.appendChild(button);
    });

    function updatePreview(name, preview) {
        preview.innerHTML = ''; // Clear previous previews
        fileSaved.get(name).forEach(file => {
            if (file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const imgWrapper = document.createElement('div');
                    imgWrapper.classList.add('position-relative', 'd-inline-block','col-auto');

                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.draggable = true; // 드래그 가능 설정
                    img.classList.add('draggable'); // 클래스 추가
                    if (isWebView()) {
                        img.classList.add('col-auto', 'm-2', 'mb-1', 'px-0'); // 클래스 추가
                    } else {
                        img.classList.add('col-auto', 'me-2', 'my-1', 'mt-2', 'mb-3'); // 클래스 추가
                    }
                    addDragAndDropHandlers(img); // 드래그 앤 드롭 핸들러 추가

                    // 삭제 버튼
                    const closeButton = document.createElement('i');
                    closeButton.innerHTML = '&times;';
                    closeButton.classList.add('position-absolute', 'bi', 'bi-close','text-danger');
                    closeButton.style.cursor = 'pointer';
                    closeButton.style.bottom = '5em';
                    closeButton.style.left = '5.75em';
                    closeButton.style.translate = '(50%,50%)';
                    closeButton.style.fontSize = '1rem';
                    closeButton.addEventListener('click', () => {
                        imgWrapper.remove();
                        fileSaved.get(name).splice(fileSaved.get(name).indexOf(file), 1); // 배열에서 파일 제거
                        updateFileInput(name, input); // 파일 입력 필드 업데이트
                    });

                    imgWrapper.appendChild(img);
                    imgWrapper.appendChild(closeButton);
                    preview.appendChild(imgWrapper);
                }
                reader.readAsDataURL(file);
            }
        });
    }

    function updateFileInput(name, input) {
        const files = fileSaved.get(name);
        input.files = createFileList(files);
    }
}

function addDragAndDropHandlers(element) {
    element.addEventListener('dragstart', handleDragStart, false);
    element.addEventListener('dragover', handleDragOver, false);
    element.addEventListener('dragenter', handleDragEnter, false);
    element.addEventListener('dragleave', handleDragLeave, false);
    element.addEventListener('drop', handleDrop, false);
    element.addEventListener('dragend', handleDragEnd, false);
}

let dragSrcEl = null;

function handleDragStart(e) {
    this.style.opacity = '0.4';
    dragSrcEl = this;

    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', this.outerHTML);
}

function handleDragOver(e) {
    if (e.preventDefault) {
        e.preventDefault();
    }
    e.dataTransfer.dropEffect = 'move';
    return false;
}

function handleDragEnter(e) {
    this.classList.add('over');
}

function handleDragLeave(e) {
    this.classList.remove('over');
}

function handleDrop(e) {
    if (e.stopPropagation) {
        e.stopPropagation();
    }

    if (dragSrcEl !== this) {
        const temp = document.createElement('div');
        this.parentNode.insertBefore(temp, this);

        dragSrcEl.parentNode.insertBefore(this, dragSrcEl);
        temp.parentNode.insertBefore(dragSrcEl, temp);
        temp.parentNode.removeChild(temp);
    }
    this.classList.remove('over');
    return false;
}

function handleDragEnd(e) {
    this.style.opacity = '1'; // 드래그가 끝나면 이미지의 투명도를 복원
    const items = document.querySelectorAll('.draggable');
    items.forEach(item => {
        item.classList.remove('over');
        item.style.opacity = '1'; // 모든 드래그 가능한 이미지의 투명도를 복원
    });
}

