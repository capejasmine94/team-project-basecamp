(function() {
    let currentStep = 1;

    function activateTab(step) {
        let tabs = document.querySelectorAll('.nav-link');
        let steps = document.querySelectorAll('.step-container');

        tabs.forEach(function(tab, index) {
            tab.classList.remove('active', 'completed');
            if (index + 1 < step) {
                tab.classList.add('completed');
            } else if (index + 1 === step) {
                tab.classList.add('active', 'completed'); // 현재 활성화된 탭도 체크 표시
            }
        });

        steps.forEach(function(stepContainer, index) {
            stepContainer.classList.remove('active');
            if (index + 1 === step) {
                stepContainer.classList.add('active');
            }
        });

        updateProgressBar(step);
        currentStep = step;
    }

    function previousStep(event) {
        event.preventDefault();
        if (currentStep > 1) {
            activateTab(currentStep - 1);
        }
    }

    function nextStep(event) {
        event.preventDefault();
        if (currentStep < 5) {
            activateTab(currentStep + 1);
        }
    }

    function goToStep(step) {
        activateTab(step);
    }

    function updateProgressBar(step) {
        let progressBar = document.getElementById('progress-bar');
        if (progressBar) {
            let progress = (step / 5) * 100;
            progressBar.style.width = progress + '%';
            progressBar.innerText = step + ' / 5';
        } else {
            console.error("Progress bar element not found");
        }
    }

    // 전역 스코프에 함수 할당
    window.nextStep = nextStep;
    window.previousStep = previousStep;
    window.goToStep = goToStep;
    window.activateTab = activateTab;
    window.updateProgressBar = updateProgressBar;

    // 차량 등록 페이지가 로드될 때 초기화 작업 수행
    document.addEventListener('DOMContentLoaded', function() {
        activateTab(currentStep);
    });
})();
