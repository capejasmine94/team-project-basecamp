//팝업을 쉽게 만들어줍니다.
//popup : 두개의 선택지가 있는 팝업입니다
//popupYesOrYes : 하나의 선택지만 있는 팝업입니다.
//1. <div th:replace="~{common/navi::popup}"></div> 를 html에 추가합니다.
//2. 확인을 눌렀을 때 실행하는 함수를 만듭니다.
//3. popup을 onclick에 넣습니다.
//4-1. popup의 매개변수는 (제목,내용,예,아니오,function(){2번에서 만든 함수}) 순으로 넣으시면 됩니다.
//4-2. popupYesOrYes의 매개변수는 기존 popup에서 아니오만 빼면 됩니다.

function popup(title,content,yes,no,action) {
   const node = document.getElementById('popup');
   let text = node.querySelector("#popup-title");
   text.innerText = title;
   text = node.querySelector("#popup-content");
   text.innerText = content;
   text = node.querySelector("#popup-no");
   text.classList.remove('d-none');
   text.innerText = no;
   text = node.querySelector("#popup-button");
   text.onclick = action;
   text.innerText = yes;
   bootstrap.Modal.getOrCreateInstance('#popup').show();
}

function popupYesOrYes(title,content,accept,action) {
   const node = document.getElementById('popup');
   let text = node.querySelector("#popup-title");
   text.innerText = title;
   text = node.querySelector("#popup-content");
   text.innerText = content;
   text = node.querySelector("#popup-no");
   text.classList.add('d-none');
   text = node.querySelector("#popup-button");
   text.onclick = action;
   text.innerText = accept;
   bootstrap.Modal.getOrCreateInstance('#popup').show();
}

function checkLogin() {
   const url ='/api/user/checkLogin';
   fetch(url)
   .then(response => response.json())
   .then((response) => {
       if(response.data.needLogin)
       popup('로그인이 필요합니다','로그인이 필요한 서비스입니다. 로그인하시겠습니까?','예','아니오',function(){
         location.href = '/user/loginPage';
      });
   });
}
