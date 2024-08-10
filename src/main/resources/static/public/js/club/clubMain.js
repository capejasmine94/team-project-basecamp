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
            const formattedDate = fullDate.toISOString().split('T')[0]; // YYYY-MM-DD 형식

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
    fetch(`/api/club/upcomingMeeting?meeting_date=${selectedDate}`)
        .then(response => response.json())
        .then(response => {
            console.log(response); // API로부터 받은 데이터를 처리
            // 여기에 데이터를 DOM에 추가하는 코드를 작성합니다.
        })
        .catch(error => {
            console.error('Error fetching upcoming meetings:', error);
        });
}
