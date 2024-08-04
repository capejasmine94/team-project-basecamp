// shared.js

// 수정 완료 후 상태를 저장하는 함수
function setUpdateStatus(isUpdated) {
    localStorage.setItem('isUpdated', isUpdated ? 'true' : 'false');
}

// 수정 완료 후 메인 페이지 또는 이전 페이지 이동
function handleBackButton() {
    if (localStorage.getItem('isUpdated') === 'true') {
        localStorage.removeItem('isUpdated'); // 상태 초기화
        window.location.href = '/secondhandProduct/mainPage';
    } else {
        history.back();
    }
}