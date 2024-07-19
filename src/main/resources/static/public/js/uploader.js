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


function createUploader(name) 
{
    let curFiles = [];
    const MAX_UPLOADS = 1;
    const container = createUploadStructure(name, false);
    const uploader = container.querySelector('#uploader');
    const input = container.querySelector('#' + name + '_input');
    const imgList = container.querySelector('#imgList_' + name);

    uploader.addEventListener('click', () => {
        input.click();
    });
    input.addEventListener('change', handleFiles);

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
        curFiles = [];
        curFiles.push(file);
        const reader = new FileReader();
        reader.onload = function(e) {
            const col = document.createElement('div');
            col.classList.add('col-auto', 'px-0');

            const previewContainer = document.createElement('div');
            previewContainer.classList.add('preview-container');

            const img = document.createElement('img');
            img.src = e.target.result;
            img.classList.add('preview-image', 'm-2');
            previewContainer.appendChild(img);
            col.appendChild(previewContainer);
            imgList.appendChild(col);
        };
        reader.readAsDataURL(file);
    }

}

function createMultipleUploader(name, max) {
    let curFiles = [];
    const MAX_UPLOADS = max;
   
    const container = createUploadStructure(name, true);
    const uploader = container.querySelector('#uploader');
    const input = container.querySelector('#' + name + '_input');
    const imgList = container.querySelector('#imgList_' + name);

    uploader.addEventListener('click', () => {
        input.click();
    });

    input.addEventListener('change', handleFiles);

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

    uploader.addEventListener('drop', (e) => {
        e.preventDefault();
        e.stopPropagation();
        uploader.classList.remove('dragover');
        const files = e.dataTransfer.files;
        handleFiles({ target: { files } });
    });

    function handleFiles(event) {
        const fileList = Array.from(event.target.files);
        console.log(curFiles.length + fileList.length )
        
        if (curFiles.length + fileList.length > MAX_UPLOADS) {
            alert(`최대 ${MAX_UPLOADS}개의 이미지만 업로드할 수 있습니다.`);
            return;
        }

        fileList.forEach(file => {
            if (!file.type.startsWith('image/') || curFiles.some(f => f.name === file.name)) {
                alert('이미지 파일만 업로드할 수 있으며, 동일한 파일은 중복 업로드할 수 없습니다.');
                return;
            }

            curFiles.push(file);
            const reader = new FileReader();
            reader.onload = function(e) {
                const col = document.createElement('div');
                col.classList.add('col-auto', 'px-0');
                col.setAttribute('draggable', true);
                col.addEventListener('dragstart', handleDragStart);
                col.addEventListener('dragover', handleDragOver);
                col.addEventListener('drop', handleDrop);

                const previewContainer = document.createElement('div');
                previewContainer.classList.add('preview-container');

                const img = document.createElement('img');
                img.src = e.target.result;
                img.classList.add('preview-image', 'm-2');

                const deleteBtn = document.createElement('button');
                deleteBtn.innerHTML = '&times;';
                deleteBtn.classList.add('btn', 'btn-danger', 'btn-sm', 'btn-delete');
                deleteBtn.type = 'button';
                deleteBtn.onclick = (event) => {
                    event.preventDefault();
                    imgList.removeChild(col);
                    curFiles = curFiles.filter(f => f !== file);
                };

                previewContainer.appendChild(img);
                previewContainer.appendChild(deleteBtn);
                col.appendChild(previewContainer);
                imgList.appendChild(col);
            };
            reader.readAsDataURL(file);
        });
    }

    let dragSrcEl = null;

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
        if (e.stopPropagation) {
            e.stopPropagation();
        }

        if (dragSrcEl !== this) {
            const dragSrcIndex = Array.from(dragSrcEl.parentNode.children).indexOf(dragSrcEl);
            const dropTargetIndex = Array.from(this.parentNode.children).indexOf(this);

            const tempHTML = dragSrcEl.innerHTML;
            dragSrcEl.innerHTML = this.innerHTML;
            this.innerHTML = tempHTML;

            [curFiles[dragSrcIndex], curFiles[dropTargetIndex]] = [curFiles[dropTargetIndex], curFiles[dragSrcIndex]];

            dragSrcEl.querySelector('.btn-delete').onclick = (event) => {
                event.preventDefault();
                imgList.removeChild(dragSrcEl);
                curFiles = curFiles.filter(f => f !== curFiles[dragSrcIndex]);
            };

            this.querySelector('.btn-delete').onclick = (event) => {
                event.preventDefault();
                imgList.removeChild(this);
                curFiles = curFiles.filter(f => f !== curFiles[dropTargetIndex]);
            };
        }
        return false;
    }
}
