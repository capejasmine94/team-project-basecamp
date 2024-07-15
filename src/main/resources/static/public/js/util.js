//1. <div th:replace="~{common/navi::popup}"></div> 를 html에 추가합니다.
//2. popup을 onclick에 넣으면 됩니다
//3. popup의 매개변수는 (제목,내용,예,아니오,function(){예 눌렀을 떄 액션}) 순으로 넣으시면 됩니다.

function popup(title,content,yes,no,action) {
   const node = document.getElementById('popup');
   let text = node.querySelector("#popup-title");
   text.innerText = title;
   text = node.querySelector("#popup-content");
   text.innerText = content;
   text = node.querySelector("#popup-no");
   text.innerText = no;
   text = node.querySelector("#popup-button");
   text.onclick = action;
   text.innerText = yes;
   bootstrap.Modal.getOrCreateInstance('#popup').show();
}

function checkLogin() {
   
}