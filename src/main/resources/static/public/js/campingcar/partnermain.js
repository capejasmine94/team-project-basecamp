document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.toggle-icon').forEach(button => {
        button.addEventListener('click', function() {
            const targetCollapse = document.querySelector(this.getAttribute('data-bs-target'));

            // 모든 섹션을 닫음 (클릭된 섹션 제외)
            document.querySelectorAll('.accordion-collapse').forEach(collapse => {
                const collapseButton = collapse.previousElementSibling.querySelector('.accordion-button');
                const icon = collapseButton.querySelector('.dropdown-icon');

                if (collapse !== targetCollapse) {
                    collapse.classList.remove('show');
                    collapseButton.setAttribute('aria-expanded', 'false');
                    if (icon) {
                        icon.classList.remove('rotate-icon');
                    }
                }
            });

            // 클릭한 섹션을 토글 (열려있으면 닫고, 닫혀있으면 열음)
            const isOpen = targetCollapse.classList.contains('show');
            const icon = this.querySelector('.dropdown-icon');

            if (isOpen) {
                targetCollapse.classList.remove('show');
                this.querySelector('.accordion-button').setAttribute('aria-expanded', 'false');
                if (icon) {
                    icon.classList.remove('rotate-icon');
                }
            } else {
                targetCollapse.classList.add('show');
                this.querySelector('.accordion-button').setAttribute('aria-expanded', 'true');
                if (icon) {
                    icon.classList.add('rotate-icon');
                }
            }
        });
    });
});
