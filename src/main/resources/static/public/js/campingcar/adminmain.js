document.addEventListener("DOMContentLoaded", function() {
    // 섹션을 두 번 클릭했을 때 해당 섹션 닫기
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

    // 아코디언 토글 기능 및 아이콘 회전
    document.querySelectorAll('.toggle-icon').forEach(button => {
        button.addEventListener('click', function() {
            const icon = this.querySelector('.material-symbols-outlined.dropdown-icon');
            const targetCollapse = document.querySelector(this.getAttribute('data-bs-target'));
            const isOpen = targetCollapse.classList.contains('show');

            // 아코디언 아이콘 회전 초기화
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

});