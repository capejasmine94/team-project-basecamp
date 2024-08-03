const currentUrl = window.location.search;
const urlQueryString = new URLSearchParams(currentUrl);
const param = urlQueryString.get('id');

document.addEventListener('DOMContentLoaded', () => {
    registerBtnEvent();
    getMeetingList();

});

function registerBtnEvent() {
    const registerClubBtn = document.getElementById("register-club-btn");
    
    registerClubBtn.onclick = () => {

        fetch(`/api/club/member?club_id=`+ param, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(response => {
            console.log(response.data.confirm);
            
            if (response.data.confirm) {
                joinClub();
                joinSuccessShowModal();
            } else {
                joinFailShowModal();
            }
        });
    }
}

function getMeetingList() {

    fetch(`/api/club/meeting?club_id=${param}`, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(response => {
            const meetingWrapperBox = document.getElementById("meeting-wrapper-box")
            meetingWrapperBox.innerHTML = '';
            
            
            for(const meetingData of response.data.clubMeetingDataList){
                const meetingTemplate = document.getElementById('meeting-template');
                const meetingWrapper = meetingTemplate.querySelector('.meeting-wrapper');

                const newMeetingWrapper = meetingWrapper.cloneNode(true);

                const meetingDate = newMeetingWrapper.querySelector(".meetingDate");
                const meetingDate2 = formatDate(meetingData.clubMeetingDto.meeting_date);
                meetingDate.innerText = meetingDate2;

                const meetingDetailDate = newMeetingWrapper.querySelector(".meetingDetailDate");
                const meetingDetailDate2 = formatDateDetail(meetingData.clubMeetingDto.meeting_date);
                meetingDetailDate.innerText = meetingDetailDate2;

                const totalJoinMember = newMeetingWrapper.querySelector(".totalJoinMember");
                console.log(totalJoinMember);
                
                const capacity = meetingData.clubMeetingDto.capacity;
                console.log(capacity);
                const joinMember = meetingData.totalJoinMember;
                console.log(joinMember);

               const capacityTag = newMeetingWrapper.querySelector(".capacity");
               console.log(capacityTag);
               capacityTag.innerText = meetingData.clubMeetingDto.capacity;

               const remainCapacityTag = newMeetingWrapper.querySelector(".remainCapacity");
               console.log(remainCapacityTag);
               console.log(meetingData.clubMeetingDto.capacity-meetingData.totalJoinMember);
               
               remainCapacityTag.innerText = `(${(meetingData.clubMeetingDto.capacity)-(meetingData.totalJoinMember)}자리 남음)`
               

               
                
                totalJoinMember.innerText = joinMember;
                
                const a = newMeetingWrapper.querySelector('.capacity');
                

                const meetingName = newMeetingWrapper.querySelector(".meetingName");
                meetingName.innerText = meetingData.clubMeetingDto.name;

                const meetingDescription = newMeetingWrapper.querySelector(".meetingDescription");
                meetingDescription.innerText = meetingData.clubMeetingDto.description;

                const meetingLocation = newMeetingWrapper.querySelector(".meetingLocation");
                meetingLocation.innerText = meetingData.clubMeetingDto.location;

                const meetingFee = newMeetingWrapper.querySelector(".meetingFee");
                meetingFee.innerText = `${meetingData.clubMeetingDto.fee}`;

                const meetingImage = newMeetingWrapper.querySelector(".meetingImage");
                meetingImage.src = `/images/${meetingData.clubMeetingDto.main_image}`;

                meetingWrapperBox.appendChild(newMeetingWrapper);
            }
        })
    
}

function formatDateDetail(inputDate) {
    const date = new Date(inputDate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0'); // 일은 그대로 가져옴

    return `${year}.${month}.${day}`;
}


function formatDate(inputDate) {
    const date = new Date(inputDate);
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0'); // 일은 그대로 가져옴

    return `${month}/${day}`;
}

function joinFailShowModal () {
    const joinModal = bootstrap.Modal.getOrCreateInstance("#joinModal");
    joinModal.show();
}

function joinFailHideModal() {
    const joinModal = bootstrap.Modal.getOrCreateInstance("#joinModal");
    joinModal.hide();
}

function joinSuccessShowModal(){
    const joinSuccessModal = bootstrap.Modal.getOrCreateInstance('#joinSuccessModal');
    joinSuccessModal.show();
}

function joinSuccessHideModal(){
    const joinSuccessModal = bootstrap.Modal.getOrCreateInstance('#joinSuccessModal');
    joinSuccessModal.hide();
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

