const currentUrl = window.location.search;
const urlQueryString = new URLSearchParams(currentUrl);
const param = urlQueryString.get('id');

document.addEventListener('DOMContentLoaded', () => {
    registerBtnEvent();
    getMeetingList();

    document.getElementById('register-club-btn').addEventListener('click', () => {
        getMeetingList();
    });
});

function registerBtnEvent() {
    const registerClubBtn = document.getElementById("register-club-btn");
    
    if(registerClubBtn !== null) {
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

                // const meetingDate = newMeetingWrapper.querySelector(".meetingDate");
                // const meetingDate2 = formatDate(meetingData.clubMeetingDto.meeting_date);
                // meetingDate.innerText = meetingDate2;
                

                const meetingDetailDate = newMeetingWrapper.querySelector(".meetingDetailDate");
                const meetingDetailDate2 = formatDateDetail(meetingData.clubMeetingDto.meeting_date);
                meetingDetailDate.innerText = meetingDetailDate2;

                const dDay = newMeetingWrapper.querySelector(".d-day");
                console.log(dDay);
                
                dDay.innerText = meetingData.dDay;
                

                const totalJoinMember = newMeetingWrapper.querySelector(".totalJoinMember");
                
                const capacity = meetingData.clubMeetingDto.capacity;
                const joinMember = meetingData.totalJoinMember;

               const capacityTag = newMeetingWrapper.querySelector(".capacity");
               capacityTag.innerText = meetingData.clubMeetingDto.capacity;

               const joinMeetingBtn = newMeetingWrapper.querySelector(".join-meeting-btn");
               
                if(joinMember >= capacity) {
                    joinMeetingBtn.classList.add('disabled');
                    joinMeetingBtn.innerText='마감';
                    joinMeetingBtn.classList.remove('btn-outline-secondary');
                    joinMeetingBtn.classList.add('btn-danger');
                    
                }

                joinMeetingBtn.onclick = () => {
                    joinMeeting(meetingData.clubMeetingDto.id);
                }

                if(meetingData.clubMeetingMemberDto != null){
                    joinMeetingBtn.innerText='불참';
                    joinMeetingBtn.classList.remove('btn-outline-secondary');
                    joinMeetingBtn.classList.add('btn-outline-danger');

                    joinMeetingBtn.onclick = () => {
                        declineMeeting(meetingData.clubMeetingDto.id);
                    }
                }

                meetingWrapperBox.appendChild(newMeetingWrapper);


               const remainCapacityTag = newMeetingWrapper.querySelector(".remainCapacity");
               
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
                meetingFee.innerText = `${meetingData.clubMeetingDto.fee.toLocaleString()}`;

                const meetingImage = newMeetingWrapper.querySelector(".meetingImage");
                meetingImage.src = `/images/${meetingData.clubMeetingDto.main_image}`;

                if(response.data.isMemberInClub == 0){
                    joinMeetingBtn.classList.add('d-none');
                }
            
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

function joinMeeting(meetingId) {

    fetch(`/api/club/meeting?meeting_id=${meetingId}`, {
        method: 'POST'
    })

        .then(response => response.json())
        .then(response => {
            console.log(response);
            
            getMeetingList();
        })
    
}

function declineMeeting(meetingId) {
    fetch(`/api/club/meeting?meeting_id=${meetingId}`, {
        method: 'DELETE'
    })

        .then(response => response.json())
        .then(response => {
            console.log(response);

            getMeetingList();
            
        })
}


