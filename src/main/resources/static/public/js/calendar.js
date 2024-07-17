//캘린더 사용법
//1. 캘린더가 들어가야 하는 col 에 아이디로 'calendar_(달력이름)' 을 넣습니다
//2. html body 마지막 부근에 script를 추가합니다
//      => createCalender(달력이름, 선택불가날짜시작일) : 하나의 날만 결정 가능한 달력입니다.
//      => createRangeCalender(달력이름, 최대 범위, 선택불가날짜시작일) : 여러 날을 범위로 지정 가능한 달력입니다.


//범위 달력에서 날짜 불러오기

//1. getStartDate(달력이름) : 해당 이름의 달력에서 선택된 시작일을 가져옵니다.
//2. getEndDate(달력이름) : 해당 이름의 달력에서 선택된 마지막일을 가져옵니다.



class CalendarData{ 
    constructor(length,limit) {
        this.length = length;
        this.limit = limit;
        this.currentDate = new Date();
    }
}

const calendarDataList = new Map();

function getCalendarData(calendarName){
    const data = calendarDataList.get(calendarName);
    if(data == null) {
        console.log(`${calendarName}의 이름을 가진 캘린더가 존재하지 않습니다.`);
        return null;
    }
    return data;
}
function getStartDate(calendarName) {
    const data = getCalendarData(calendarName);
    if(data == null) return;
    return data.startDate;
}
function getEndDate(calendarName) {
    const data = getCalendarData(calendarName);
    if(data == null) return;
    return data.endDate;
}
function getDate(calendarName) {
    const data = getCalendarData(calendarName);
    if(data == null) return;
    return data.startDate;
}
function createCalendarStructure(name) {
    const container = document.getElementById('calendar_' + name);

    // Create navigation row
    const navRow = document.createElement('div');
    navRow.className = 'row';

    const navCol = document.createElement('div');
    navCol.className = 'col text-center';
    
    const prevIcon = document.createElement('i');
    prevIcon.className = 'bi bi-caret-left-fill';
    prevIcon.id = 'prevMonth';
    
    const monthYearSpan = document.createElement('span');
    monthYearSpan.id = 'monthYear';
    
    const nextIcon = document.createElement('i');
    nextIcon.className = 'bi bi-caret-right-fill';
    nextIcon.id = 'nextMonth';
    
    const startDateData = document.createElement('input');
    startDateData.type ='hidden';
    startDateData.classList.add("startDate");
    startDateData.name = 'startDate';
    getCalendarData(name)

    const endDateData = document.createElement('input');
    endDateData.type ='hidden';
    endDateData.classList.add("endDate");
    endDateData.name = 'endDate';

    navCol.appendChild(startDateData);
    navCol.appendChild(endDateData);
    navCol.appendChild(prevIcon);
    navCol.appendChild(monthYearSpan);
    navCol.appendChild(nextIcon);
    navRow.appendChild(navCol);
    container.appendChild(navRow);
    
    const table = document.createElement('table');
    table.className = 'table table-bordered text-center';

    const thead = document.createElement('thead');
    const theadRow = document.createElement('tr');
    const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    days.forEach(day => {
      const th = document.createElement('th');
      th.textContent = day;
      theadRow.appendChild(th);
    });
    thead.appendChild(theadRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');
    tbody.id = 'calendarBody';
    table.appendChild(tbody);
    container.appendChild(table);


    const selectedRow = document.createElement('div');
    selectedRow.className = 'row';
    
    const selectedCol = document.createElement('div');
    selectedCol.className = 'col text-center';

    const selectedDatesP = document.createElement('p');
    selectedDatesP.id = 'selectedDates';

    selectedCol.appendChild(selectedDatesP);
    selectedRow.appendChild(selectedCol);
    container.appendChild(selectedRow);

    return container;
}
function createRangeCalendar(name, length, limit) {
    console.log(limit);
    const calendarDocument = createCalendarStructure(name);
    const calendarBody = calendarDocument.querySelector('#calendarBody');
    const monthYear = calendarDocument.querySelector('#monthYear');
    const prevMonth = calendarDocument.querySelector('#prevMonth');
    const nextMonth = calendarDocument.querySelector('#nextMonth');

    const data = new CalendarData(length,limit);
    calendarDataList.set(name, data);
    const today = new Date(); // 오늘 날짜 객체

    function renderCalendar(date) {
        calendarBody.innerHTML = ''; // 이전 달력 내용을 비웁니다.
        const currentYear = date.getFullYear(); // 현재 연도
        const currentMonth = date.getMonth(); // 현재 월 (0-11)

        // 해당 월의 첫 번째 날짜와 마지막 날짜 계산
        const firstDay = new Date(currentYear, currentMonth, 1).getDay(); // 해당 월의 첫 번째 날짜의 요일 (0-6)
        const lastDate = new Date(currentYear, currentMonth + 1, 0).getDate(); // 해당 월의 마지막 날짜 (28-31)

        // 이전 월의 마지막 날짜 계산
        const prevLastDate = new Date(currentYear, currentMonth, 0).getDate(); // 이전 월의 마지막 날짜 (28-31)

        // 월과 년도 텍스트 업데이트
        monthYear.innerText = `${date.toLocaleString('default', { month: 'long' })} ${currentYear}`;

        // 일 선택 이벤트 추가 함수
        function addCellSelectEvent(cell, cellDate) {
            if(cellDate.getTime() > data.limit.getTime())
            return;
            cell.addEventListener('click', function() {
                if (!data.startDate || (data.startDate && data.endDate)) {
                data.startDate = cellDate; // 시작 날짜 설정
                data.endDate = null; // 종료 날짜 초기화
                } else if (cellDate.getTime() === data.startDate.getTime()) {
                    cell.classList.remove('selected-start');
                    data.startDate = null;
                    return;
                } 
                else if (cellDate < data.startDate) {
                data.startDate = cellDate; // 클릭한 날짜가 시작 날짜보다 이전인 경우
                data.endDate = null; // 종료 날짜 초기화
                } 
                else {
                const tempEndDate = cellDate; // 임시 종료 날짜 설정
                const rangeLength = Math.ceil((tempEndDate - data.startDate) / (1000 * 60 * 60 * 24)) + 1; // 범위 길이 계산
    
                // 범위 길이가 최대 길이를 초과하는 경우 경고 메시지 표시
                if (rangeLength > data.length) {
                    // import 
                    alert(`날짜 범위는 최대 ${data.length}일을 초과할 수 없습니다.`);
                    return; // 범위 지정 중단
                }
    
                data.endDate = cellDate; // 종료 날짜 설정
                }
                renderCalendar(data.currentDate); // 달력 다시 렌더링
                //데이터 정보 입력
            });
        }

        let row = document.createElement('tr'); // 달력의 새로운 행 생성
        // 첫 번째 행을 이전 월의 날짜로 채웁니다.
        for (let i = 0; i < firstDay; i++) {
        let cell = document.createElement('td');
        cell.innerText = prevLastDate - firstDay + 1 + i;
        const cellDate = new Date(currentYear, currentMonth - 1, prevLastDate - firstDay + 1 + i);
        if (data.startDate && data.endDate && cellDate >= data.startDate && cellDate <= data.endDate) {
            cell.classList.add('selected-range');
        } else if (data.startDate && cellDate.getTime() === data.startDate.getTime()) {
            cell.classList.add('selected-start');
        } else if (data.endDate && cellDate.getTime() === data.endDate.getTime()) {
            cell.classList.add('selected-end');
        }
        else
            cell.classList.add('inactive');
        
        addCellSelectEvent(cell, cellDate);
        row.appendChild(cell);
        }

        // 각 날짜를 셀로 생성하여 달력에 추가합니다.
        let dayCount = 0; // 총 날짜 개수를 세기 위한 변수
        for (let day = 1; day <= lastDate; day++) {
        if ((firstDay + dayCount) % 7 === 0 && day !== 1) {
            calendarBody.appendChild(row);
            row = document.createElement('tr');
        }
        let cell = document.createElement('td');
        cell.innerText = day;
        const cellDate = new Date(currentYear, currentMonth, day);

        // 주말에 대한 스타일을 추가합니다.
        if ((firstDay + dayCount) % 7 === 6) {
            cell.classList.add('saturday');
        } else if ((firstDay + dayCount) % 7 === 0) {
            cell.classList.add('sunday');
        }

        // 제한되는 예약일을 추가합니다.
        if (cellDate.getTime() > data.limit.getTime()) {
            cell.classList.add('inactive');
        }

        // 선택된 날짜 범위를 강조합니다.
        if (data.startDate && data.endDate && cellDate >= data.startDate && cellDate <= data.endDate) {
            cell.classList.add('selected-range');
        } else if (data.startDate && cellDate.getTime() === data.startDate.getTime()) {
            cell.classList.add('selected-start');
        } else if (data.endDate && cellDate.getTime() === data.endDate.getTime()) {
            cell.classList.add('selected-end');
        }
        addCellSelectEvent(cell, cellDate);

        row.appendChild(cell); // 행에 셀 추가
        dayCount++;
        }

        // 마지막 행을 다음 월의 날짜로 채웁니다.
        let nextMonthDay = 1;
        while (row.children.length < 7) {
        let cell = document.createElement('td');
        cell.innerText = nextMonthDay++;
        const cellDate = new Date(currentYear, currentMonth + 1, nextMonthDay-1);
        if (data.startDate && data.endDate && cellDate >= data.startDate && cellDate <= data.endDate) {
            cell.classList.add('selected-range');
        } else if (data.startDate && cellDate.getTime() === data.startDate.getTime()) {
            cell.classList.add('selected-start');
        } else if (data.endDate && cellDate.getTime() === data.endDate.getTime()) {
            cell.classList.add('selected-end');
        }
        else
            cell.classList.add('inactive');
        addCellSelectEvent(cell, cellDate);
        row.appendChild(cell);
        }
        calendarBody.appendChild(row);

        // 총 날짜 개수가 35(5주)보다 큰 경우에만 6줄로 만듭니다.
        if (dayCount + firstDay > 35) {
        // 추가 행을 다음 월의 날짜로 채워 6줄이 되도록 합니다.
        while (calendarBody.children.length < 6) {
            row = document.createElement('tr');
            for (let i = 0; i < 7; i++) {
            let cell = document.createElement('td');
            cell.innerText = nextMonthDay++;
            cell.classList.add('inactive');
            row.appendChild(cell);
            }
            calendarBody.appendChild(row);
        }
        }
    }

    // 이전 달로 이동하는 버튼 이벤트 리스너 추가
    prevMonth.addEventListener('click', function() {
        const tempDate = new Date(data.currentDate);
        tempDate.setMonth(tempDate.getMonth() - 1); // 임시 날짜를 이전 달로 설정

        // 임시 날짜가 오늘보다 이전인 경우 이동 불가
        if (tempDate.getFullYear() < today.getFullYear() || 
            (tempDate.getFullYear() === today.getFullYear() && tempDate.getMonth() < today.getMonth())) {
        return;
        }

        data.currentDate.setMonth(data.currentDate.getMonth() - 1); // 현재 월을 한 달 감소
        renderCalendar(data.currentDate); // 달력 다시 렌더링
    });

    // 다음 달로 이동하는 버튼 이벤트 리스너 추가
    nextMonth.addEventListener('click', function() {
        const tempDate = new Date(data.currentDate);
        tempDate.setMonth(tempDate.getMonth() + 1); // 임시 날짜를 다음 달로 설정

        // 임시 날짜가 최대 날짜보다 이후인 경우 이동 불가
        // if (tempDate.getTime() > data.limit.getTime()) {
        //     return;
        // }

        data.currentDate.setMonth(data.currentDate.getMonth() + 1); // 현재 월을 한 달 증가
        renderCalendar(data.currentDate); // 달력 다시 렌더링
    });

    renderCalendar(data.currentDate); // 초기 달력 렌더링
}
function createCalendar(name, limit) {
    console.log(limit);
    const calendarDocument = createCalendarStructure(name);
    const calendarBody = calendarDocument.querySelector('#calendarBody');
    const monthYear = calendarDocument.querySelector('#monthYear');
    const prevMonth = calendarDocument.querySelector('#prevMonth');
    const nextMonth = calendarDocument.querySelector('#nextMonth');

    const data = new CalendarData(1,limit);
    calendarDataList.set(name, data);
    const today = new Date(); // 오늘 날짜 객체

    function renderCalendar(date) {
        calendarBody.innerHTML = ''; // 이전 달력 내용을 비웁니다.
        const currentYear = date.getFullYear(); // 현재 연도
        const currentMonth = date.getMonth(); // 현재 월 (0-11)

        // 해당 월의 첫 번째 날짜와 마지막 날짜 계산
        const firstDay = new Date(currentYear, currentMonth, 1).getDay(); // 해당 월의 첫 번째 날짜의 요일 (0-6)
        const lastDate = new Date(currentYear, currentMonth + 1, 0).getDate(); // 해당 월의 마지막 날짜 (28-31)

        // 이전 월의 마지막 날짜 계산
        const prevLastDate = new Date(currentYear, currentMonth, 0).getDate(); // 이전 월의 마지막 날짜 (28-31)

        // 월과 년도 텍스트 업데이트
        monthYear.innerText = `${date.toLocaleString('default', { month: 'long' })} ${currentYear}`;

        // 일 선택 이벤트 추가 함수
        function addCellSelectEvent(cell, cellDate) {
            if(cellDate.getTime() > data.limit.getTime()){
                return;
            }
            cell.addEventListener('click', function() {
                data.startDate = cellDate; // 시작 날짜 설정
                //calendarBody.querySelector('startDate').value = cellDate;
                renderCalendar(data.currentDate); // 달력 다시 렌더링
                //데이터 정보 입력
            });
        }

        let row = document.createElement('tr'); // 달력의 새로운 행 생성
        // 첫 번째 행을 이전 월의 날짜로 채웁니다.
        for (let i = 0; i < firstDay; i++) {
        let cell = document.createElement('td');
        cell.innerText = prevLastDate - firstDay + 1 + i;
        const cellDate = new Date(currentYear, currentMonth - 1, prevLastDate - firstDay + 1 + i);
        if (data.startDate && cellDate.getTime() === data.startDate.getTime())
            cell.classList.add('selected-start');
        else
            cell.classList.add('inactive');
        
        addCellSelectEvent(cell, cellDate);
        row.appendChild(cell);
        }

        // 각 날짜를 셀로 생성하여 달력에 추가합니다.
        let dayCount = 0; // 총 날짜 개수를 세기 위한 변수
        for (let day = 1; day <= lastDate; day++) {
        if ((firstDay + dayCount) % 7 === 0 && day !== 1) {
            calendarBody.appendChild(row);
            row = document.createElement('tr');
        }
        let cell = document.createElement('td');
        cell.innerText = day;
        const cellDate = new Date(currentYear, currentMonth, day);

        // 주말에 대한 스타일을 추가합니다.
        if ((firstDay + dayCount) % 7 === 6) {
            cell.classList.add('saturday');
        } else if ((firstDay + dayCount) % 7 === 0) {
            cell.classList.add('sunday');
        }

        // 제한되는 예약일을 추가합니다.
        if (cellDate.getTime() > data.limit.getTime()) {
            cell.classList.add('inactive');
        }

        // 선택된 날짜 범위를 강조합니다.
        if (data.startDate && cellDate.getTime() === data.startDate.getTime()) {
            cell.classList.add('selected-start');
        }
        addCellSelectEvent(cell, cellDate);

        row.appendChild(cell); // 행에 셀 추가
        dayCount++;
        }

        // 마지막 행을 다음 월의 날짜로 채웁니다.
        let nextMonthDay = 1;
        while (row.children.length < 7) {
        let cell = document.createElement('td');
        cell.innerText = nextMonthDay++;
        const cellDate = new Date(currentYear, currentMonth + 1, nextMonthDay-1);
        if (data.startDate && cellDate.getTime() === data.startDate.getTime()) {
            cell.classList.add('selected-start');
        } 
        else
            cell.classList.add('inactive');
        addCellSelectEvent(cell, cellDate);
        row.appendChild(cell);
        }
        calendarBody.appendChild(row);

        // 총 날짜 개수가 35(5주)보다 큰 경우에만 6줄로 만듭니다.
        if (dayCount + firstDay > 35) {
        // 추가 행을 다음 월의 날짜로 채워 6줄이 되도록 합니다.
        while (calendarBody.children.length < 6) {
            row = document.createElement('tr');
            for (let i = 0; i < 7; i++) {
            let cell = document.createElement('td');
            cell.innerText = nextMonthDay++;
            cell.classList.add('inactive');
            row.appendChild(cell);
            }
            calendarBody.appendChild(row);
        }
        }
    }

    // 이전 달로 이동하는 버튼 이벤트 리스너 추가
    prevMonth.addEventListener('click', function() {
        const tempDate = new Date(data.currentDate);
        tempDate.setMonth(tempDate.getMonth() - 1); // 임시 날짜를 이전 달로 설정

        // 임시 날짜가 오늘보다 이전인 경우 이동 불가
        if (tempDate.getFullYear() < today.getFullYear() || 
            (tempDate.getFullYear() === today.getFullYear() && tempDate.getMonth() < today.getMonth())) {
        return;
        }

        data.currentDate.setMonth(data.currentDate.getMonth() - 1); // 현재 월을 한 달 감소
        renderCalendar(data.currentDate); // 달력 다시 렌더링
    });

    // 다음 달로 이동하는 버튼 이벤트 리스너 추가
    nextMonth.addEventListener('click', function() {
        const tempDate = new Date(data.currentDate);
        tempDate.setMonth(tempDate.getMonth() + 1); // 임시 날짜를 다음 달로 설정

        // 임시 날짜가 최대 날짜보다 이후인 경우 이동 불가
        // if (tempDate.getTime() > data.limit.getTime()) {
        //     return;
        // }

        data.currentDate.setMonth(data.currentDate.getMonth() + 1); // 현재 월을 한 달 증가
        renderCalendar(data.currentDate); // 달력 다시 렌더링
    });

    renderCalendar(data.currentDate); // 초기 달력 렌더링
}