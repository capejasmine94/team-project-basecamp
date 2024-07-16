document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.accordion-button').forEach(button => {
        button.addEventListener('click', function() {
            const isOpen = this.getAttribute('aria-expanded') === 'true';
            if (isOpen) {
                const collapseId = this.getAttribute('data-bs-target');
                const collapseElement = document.querySelector(collapseId);
                if (collapseElement) {
                    collapseElement.classList.remove('show');
                }
            }
        });
    });

    document.querySelectorAll('.toggle-icon').forEach(button => {
        button.addEventListener('click', function() {
            const icon = this.querySelector('.material-symbols-outlined.dropdown-icon');
            const targetCollapse = document.querySelector(this.getAttribute('data-bs-target'));
            const isOpen = targetCollapse.classList.contains('show');

            document.querySelectorAll('.toggle-icon .material-symbols-outlined.dropdown-icon').forEach(icon => {
                icon.classList.remove('rotate-icon');
            });

            if (isOpen) {
                targetCollapse.classList.remove('show');
            } else {
                icon.classList.add('rotate-icon');
                targetCollapse.classList.add('show');
            }
        });
    });

    document.querySelectorAll('.subcategory').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            const page = this.getAttribute('data-page');
            const target = this.getAttribute('data-target');
            loadPage(page, target);
        });
    });

    function loadPage(page, targetId) {
        console.log(`Loading page: ${page} into target: ${targetId}`);
        const xhr = new XMLHttpRequest();
        xhr.open('GET', page);
        xhr.onload = function() {
            if (xhr.status === 200) {
                const targetElement = document.getElementById(targetId);
                targetElement.innerHTML = xhr.responseText;
                console.log('Page loaded successfully');
                
                // 로드된 HTML의 스크립트를 실행
                const scripts = targetElement.querySelectorAll('script');
                scripts.forEach(script => {
                    const newScript = document.createElement('script');
                    newScript.text = script.text;
                    document.head.appendChild(newScript).parentNode.removeChild(newScript);
                });

                // 페이지와 같은 이름의 JavaScript 파일을 동적으로 로드
                const scriptUrl = page.replace('.html', '.js');
                loadScript(scriptUrl);
            } else {
                console.error(`Failed to load page: ${xhr.status} ${xhr.statusText}`);
            }
        };
        xhr.onerror = function() {
            console.error('Request failed.');
        };
        xhr.send();
    }

    function loadScript(url) {
        const script = document.createElement('script');
        script.src = url;
        script.onload = function() {
            console.log(`Script loaded: ${url}`);
        };
        document.head.appendChild(script);
    }
});
