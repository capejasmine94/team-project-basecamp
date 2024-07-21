//팝업을 쉽게 만들어줍니다.
//popup : 두개의 선택지가 있는 팝업입니다.
//1. <div th:replace="~{common/navi::popup}"></div> 를 html에 추가합니다.
//2. 확인을 눌렀을 때 실행하는 함수를 만듭니다.
//3. popup을 onclick에 넣습니다.
//4-1. popup의 매개변수는 (제목,내용,예,아니오,function(){2번에서 만든 함수}) 순으로 넣으시면 됩니다.
//4-2. 매개변수에서 기존 popup에서 아니오만 빼면 단일 팝업을 표시합니다.

function popup(title,content,yes,no,action) {
   const node = document.getElementById('popup');
   bootstrap.Modal.getOrCreateInstance('#popup',{backdrop: 'true', keyboard: true})  
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

function popupForce(title,content,accept,action) {
   const node = document.getElementById('popup');
   bootstrap.Modal.getOrCreateInstance('#popup',{backdrop: 'static', keyboard: false})  
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



function popupClose() {
  const c = bootstrap.Modal.getOrCreateInstance('#popup');
  c.hide();
}



//로그인이 필요한 서비스를 확인해주는 기능입니다.
//강제성이 없습니다.
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

//강제성이 있는 로그인 팝업을 띄워주는 기능입니다.
function checkMustLogin() {
   const url ='/api/user/checkLogin';
   fetch(url)
   .then(response => response.json())
   .then((response) => {
       if(response.data.needLogin)
         location.href = '/user/loginPage';
      
   });
}

function formatPhoneNumber(input) {
   let value = input.value.replace(/[^0-9]/g, ''); // 숫자 외의 문자 제거
   let formattedValue = '';

   if (value.length > 3) {
       formattedValue += value.substring(0, 3) + '-';
       if (value.length > 7) {
           formattedValue += value.substring(3, 7) + '-';
           formattedValue += value.substring(7, 11);
       } else {
           formattedValue += value.substring(3, 7);
       }
   } else {
       formattedValue += value;
   }

   input.value = formattedValue;
}

document.addEventListener('DOMContentLoaded', (event) => {
  const inputNumbers = document.getElementsByClassName('moneyInput');
  if(inputNumbers.length <= 0 || inputNumbers == null)
    return;
  for(const node of inputNumbers){
      node.addEventListener('input', function (e) {
          let input = e.target.value;

          // Remove all non-numeric characters except for period (.)
          input = input.replace(/[^0-9.]/g, '');

          // Split the input value into integer and decimal parts
          const parts = input.split('.');
          let integerPart = parts[0];
          const decimalPart = parts.length > 1 ? '.' + parts[1] : '';

          // Format the integer part with commas
          integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

          // Combine the formatted integer part and the decimal part
          e.target.value = integerPart + decimalPart;
      });
  }
});


