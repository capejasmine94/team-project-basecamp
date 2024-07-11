let uiContents

// 사용법
// initUIContent를 스크립트 맨 마지막줄에 불러준다
// initUIContent에는 커다란 ui탭이 될 col 또는 row의 아이디를 전부 적어주면 된다
// 버튼의 onClick 부분에 onClickButton(활성화할 UI 아이디)를 적어주면 된다

function initUIContent(...contents){
    uiContents = []
    contents.forEach(function(content){
        uiContents.push(content);
    });
}

function onClickButton(content) {
    uiContents.forEach(function(node) {
        $("#" + node).fadeOut("fast");
    });
    $("#" + content).fadeIn("fast");
}