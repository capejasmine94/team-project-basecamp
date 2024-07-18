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

    // document.querySelectorAll('.subcategory').forEach(link => {
    //     link.addEventListener('click', function(event) {
    //         event.preventDefault();
    //         const page = this.getAttribute('data-page');
    //         const target = this.getAttribute('data-target');
    //         loadPage(page, target);
    //     });
    // });

});
