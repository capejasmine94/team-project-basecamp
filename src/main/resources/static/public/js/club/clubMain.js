document.addEventListener('DOMContentLoaded', function() {
   getWeek();
  
   
});

//
function getWeek() {
    const weekDates = getWeekDates();
    const dayElements = document.querySelectorAll('.meeting-day');
    const dateElements = document.querySelectorAll('.meeting-date');
    const dateCircles = document.querySelectorAll('.date-circle');
    const today = new Date().getDate();

    weekDates.forEach((dateInfo, index) => {
        if (dayElements[index] && dateElements[index]) {
            dayElements[index].innerText = dateInfo.day;
            dateElements[index].innerText = dateInfo.dateNumber;

            // dateElements에 날짜와 관련된 data-date 속성 설정
            const fullDate = new Date();

            fullDate.setDate(fullDate.getDate() + index);
            const formattedDate = formatDateDetail(fullDate);

            dateElements[index].setAttribute('data-date', formattedDate);

            // 오늘 날짜와 일치하는 요소에 'selected' 클래스를 추가
            if (dateInfo.dateNumber === today) {
                dateCircles[index].classList.add('selected');
                getUpcomingMeetingList(formattedDate); // 페이지 로드 시 오늘 날짜의 데이터를 가져옴
            }

            // 클릭 이벤트 설정: 클릭한 날짜에 선택 상태를 적용
            dateCircles[index].addEventListener('click', function() {
                dateCircles.forEach(c => c.classList.remove('selected'));
                this.classList.add('selected');

                const selectedDate = dateElements[index].getAttribute('data-date');
                getUpcomingMeetingList(selectedDate); // 클릭한 날짜의 데이터를 가져옴
            });
        } else {
            console.log('Element not found:', dayElements[index], dateElements[index]);
        }
    });

    const cols = document.querySelectorAll('.row.py-3 .col');
    cols.forEach(col => {
        col.addEventListener('click', function() {
            cols.forEach(c => c.classList.remove('selected'));
            this.classList.add('selected');
        });
    });

}

// 함수: 오늘 날짜부터 일주일 간의 날짜와 요일을 계산하여 반환
function getWeekDates() {
    const today = new Date();
    const weekDates = [];
    const days = ['일', '월', '화', '수', '목', '금', '토'];

    for (let i = 0; i < 7; i++) {
        const date = new Date(today);
        date.setDate(today.getDate() + i);
        const day = days[date.getDay()];
        const dateNumber = date.getDate();
        weekDates.push({ day, dateNumber });
    }

    return weekDates;
}

// 다가오는 미팅 목록을 가져오는 함수
function getUpcomingMeetingList(selectedDate) {
    fetch(`/api/club/upcomingMeeting?meeting_date=${selectedDate}`, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(response => {
            console.log(response); // API로부터 받은 데이터를 처리
            const upcomingMeetingWrapperBox = document.getElementById('upcoming-meeting-wrapper-box')
            upcomingMeetingWrapperBox.innerHTML = '';

            for(const upcomingMeetingData of response.data.upcomingMeetingList){
                const upcomingMeetingTemplate = document.getElementById('upcoming-meeting-template');
                const upcomingMeetingWrapper = upcomingMeetingTemplate.querySelector('.upcoming-meeting-wrapper');

                const newUpcomingMeetingWrapper = upcomingMeetingWrapper.cloneNode(true);

                newUpcomingMeetingWrapper.onclick = () => {
                    showMeetingModal(upcomingMeetingData);
                };

                const upcomingMeetingImage = newUpcomingMeetingWrapper.querySelector('.upcoming-meeting-image');
                upcomingMeetingImage.src = `/images/${upcomingMeetingData.upcomingMeetingDto.main_image}`;

                const upcomingMeetingDate = newUpcomingMeetingWrapper.querySelector('.upcoming-meeting-date');

                // meetingDateString 변수 선언
                const meetingDateString = upcomingMeetingData.upcomingMeetingDto.meeting_date;

                // Date 객체로 변환 (로컬 시간대 고려)
                const meetingDate = new Date(meetingDateString);

                // 날짜 형식을 8/11 형태로 변환
                const month = meetingDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
                const day = meetingDate.getDate(); // 날짜를 가져옴

                // 요일을 숫자로 가져옴 (0: 일요일, 1: 월요일, ..., 6: 토요일)
                const dayOfWeekNumber = meetingDate.getDay();

                // 숫자를 요일 이름으로 변환하기 위한 배열
                const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

                // 요일 이름 가져오기
                const dayOfWeek = daysOfWeek[dayOfWeekNumber];

                // 형식: 8/11 (목)
                const formattedDate = `${month}/${day} (${dayOfWeek})`;

                upcomingMeetingDate.innerText = formattedDate;


                const upcomingMeetingName = newUpcomingMeetingWrapper.querySelector('.upcoming-meeting-name');
                upcomingMeetingName.innerText = upcomingMeetingData.upcomingMeetingDto.name;

                const upcomingMeetingLocation = newUpcomingMeetingWrapper.querySelector('.upcoming-meeting-location');
                upcomingMeetingLocation.innerText = upcomingMeetingData.upcomingMeetingDto.location;

                const upcomingMeetingClub = newUpcomingMeetingWrapper.querySelector('.upcoming-meeting-club');
                upcomingMeetingClub.innerText = upcomingMeetingData.clubDto.name;

                const upcomingMeetingMemberCounts = newUpcomingMeetingWrapper.querySelectorAll('.upcoming-member-count');
                for(const upcomingMeetingMemberCount  of upcomingMeetingMemberCounts) {
                    upcomingMeetingMemberCount.innerText = upcomingMeetingData.meetingMemberCount;
                }
               

                const upcomingMeetingCapacity = newUpcomingMeetingWrapper.querySelector('.upcoming-capacity');
                upcomingMeetingCapacity.innerText = upcomingMeetingData.upcomingMeetingDto.capacity;

                upcomingMeetingWrapperBox.appendChild(newUpcomingMeetingWrapper);
            }
               
        })
        
}

function formatDateDetail(inputDate) {
    const date = new Date(inputDate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0'); // 일은 그대로 가져옴

    return `${year}-${month}-${day}`;
}


function formatDate(inputDate) {
    const date = new Date(inputDate);
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0'); // 일은 그대로 가져옴

    return `${month}/${day}`;
}

function showMeetingModal(upcomingMeetingData){
    const modal = bootstrap.Modal.getOrCreateInstance("#meeting-modal-for-main");

    console.log(upcomingMeetingData);
    
    const meetingModalForMain = document.getElementById('meeting-modal-for-main')
    const upcomingMeetingWrapper = meetingModalForMain.querySelector('.upcoming-meeting-wrapper')
    const upcomingMeetingName = upcomingMeetingWrapper.querySelector('.upcoming-meeting-name')
    upcomingMeetingName.innerText = upcomingMeetingData.upcomingMeetingDto.name;
    
    const upcomingMeetingDescription = upcomingMeetingWrapper.querySelector('.upcoming-meeting-description')
    upcomingMeetingDescription.innerText = upcomingMeetingData.upcomingMeetingDto.description;

   const upcomingMeetingDate = upcomingMeetingWrapper.querySelector('.upcoming-meeting-date')
    upcomingMeetingDate.innerText = upcomingMeetingData.upcomingMeetingDto.meeting_date;
    // meetingDateString 변수 선언
    const meetingDateString = upcomingMeetingData.upcomingMeetingDto.meeting_date;

    // Date 객체로 변환 (로컬 시간대 고려)
    const meetingDate = new Date(meetingDateString);

    // 날짜 형식을 8/11 형태로 변환
    const month = meetingDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
    const day = meetingDate.getDate(); // 날짜를 가져옴

    // 요일을 숫자로 가져옴 (0: 일요일, 1: 월요일, ..., 6: 토요일)
    const dayOfWeekNumber = meetingDate.getDay();

    // 숫자를 요일 이름으로 변환하기 위한 배열
    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

    // 요일 이름 가져오기
    const dayOfWeek = daysOfWeek[dayOfWeekNumber];

    // 형식: 8/11 (목)
    const formattedDate = `${month}/${day} (${dayOfWeek})`;

    upcomingMeetingDate.innerText = formattedDate;

    const upcomingMeetingLocation = upcomingMeetingWrapper.querySelector('.upcoming-meeting-location')
    upcomingMeetingLocation.innerText = upcomingMeetingData.upcomingMeetingDto.location;

    const upcomingMeetingFee = upcomingMeetingWrapper.querySelector('.upcoming-meeting-fee')
    upcomingMeetingFee.innerText = `${upcomingMeetingData.upcomingMeetingDto.fee.toLocaleString()}`;

    // const upcomingMemberCount = upcomingMeetingWrapper.querySelector('.upcoming-member-count')
    // upcomingMemberCount.innerText = upcomingMeetingData.upcomingMeetingDto.meetingMemberCount;


    const upcomingMeetingMemberCount = upcomingMeetingWrapper.querySelector('.upcoming-member-count');
    upcomingMeetingMemberCount.innerText = upcomingMeetingData.meetingMemberCount;
   

    const upcomingMeetingCapacity = upcomingMeetingWrapper.querySelector('.upcoming-capacity');
    upcomingMeetingCapacity.innerText = upcomingMeetingData.upcomingMeetingDto.capacity;

    const upcomingMeetingImage = upcomingMeetingWrapper.querySelector('.upcoming-meeting-image');
    upcomingMeetingImage.src = `/images/${upcomingMeetingData.upcomingMeetingDto.main_image}`;

    const goClub = upcomingMeetingWrapper.querySelector('.go-club');
    goClub.href = `/club/home?id=${upcomingMeetingData.clubDto.id}`;

    const joinMemberProfileBox = document.getElementById('join-member-profile-box');
    joinMemberProfileBox.innerHTML = '';
    for(const userDto of upcomingMeetingData.userDtoList){
       const upcomingMeetingUserDtoWrapper = document.querySelector('#join-member-profile-template .wrapper');
       const upcomingMeetingUserDto = upcomingMeetingUserDtoWrapper.cloneNode(true);

       if(userDto.profile_image === 'default') {
        upcomingMeetingUserDto.querySelector('.join-member-profile').src= `/public/img/common/default_profile.png`;  
       } else {
        upcomingMeetingUserDto.querySelector('.join-member-profile').src= `/images/${userDto.profile_image}`;
       }
       joinMemberProfileBox.appendChild(upcomingMeetingUserDto);
    }
    
    modal.show();
}

