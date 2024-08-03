const currentUrl = window.location.search;
const urlQueryString = new URLSearchParams(currentUrl);
const param = urlQueryString.get('id');

document.addEventListener('DOMContentLoaded', () => {
    const registerClubBtn = document.getElementById("register-club-btn");
    
    registerClubBtn.onclick = () => {

        fetch(`/api/club/member?club_id=`+ param, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(response => {
            if (response.data.confirm) {
                joinClub();
            } else {
                joinFailShowModal();
            }
        });
    }
});

function joinFailShowModal () {
    const joinModal = bootstrap.Modal.getOrCreateInstance("#joinModal");
    joinModal.show();
}

function joinFailHideModal() {
    const joinModal = bootstrap.Modal.getOrCreateInstance("#joinModal");
    joinModal.hide();
}

function joinClub(){
    fetch(`/api/club/member?club_id=${param}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(response => {
            const registerBtn = document.getElementById("registerBtn");
            registerBtn.remove();
        })
}

