  // url에서 ?id=9 까지 가져옴(웹브라우저의 현재 주소의 쿼리스트링을 가져온다)
  const currentUrl = window.location.search;
  // console.log(a);

  // 위에서 가져온 ?id=9에서 9만 가져온다 (id -> 쿼리스트링 키값으로)
  const urlQueryString = new URLSearchParams(currentUrl);
  
  const param = urlQueryString.get('club_id');
  const param2 = urlQueryString.get('user_id');
//   console.log(param, param2);

document.addEventListener('DOMContentLoaded', () => {

    getUserData();

   document.getElementById('role-update').addEventListener('click', () => {
    roleUpdate();
   });
  
   
});

function getUserData(){
    

    fetch(`/api/club/user?club_id=${param}&user_id=${param2}`, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(response => {
        console.log(response.data.clubMemberData);

        const profileImage = document.getElementById('profile-image');
        console.log(profileImage);
        
        if (response.data.clubMemberData.userDto.profile_image === 'default') {
            profileImage.src = '/public/img/clubImage/profile.jpg';
        } else {
            profileImage.src = `/images/${response.data.clubMemberData.userDto.profile_image}`;
        }

        const nickname = document.getElementById('nickname')
        nickname.innerText = response.data.clubMemberData.userDto.nickname;
        console.log(nickname);
        

        const account = document.getElementById('account')
        account.innerText = response.data.clubMemberData.userDto.account;

        const updatedAt = document.getElementById('updated-at')
        updatedAt.innerText = formatDateDetail(response.data.clubMemberData.clubMemberDto.updated_at);

        const joinDate = document.getElementById('join-date')
        joinDate.innerText = formatDateDetail(response.data.clubMemberData.clubMemberDto.joined_at);
        
        const roleId = document.getElementById('role-id')
        roleId.value = response.data.clubMemberData.clubMemberDto.role_id;

        const toastTrigger = document.getElementById('role-update')
        const toastLiveExample = document.getElementById('liveToast')

        if (toastTrigger) {
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
        toastTrigger.addEventListener('click', () => {
        toastBootstrap.show()
  })
}
    })
}

function roleUpdate(){

        const roleIdTag = document.getElementById('role-id')
        const roleId = roleIdTag.value;

        fetch(`/api/club/role?club_id=${param}&user_id=${param2}&role_id=${roleId}`, {
            method: 'PATCH'
        })
        .then(response => response.json())
        .then(response => {
            // console.log(response.data.result);
            getUserData();

        })
    
}

function formatDateDetail(inputDate) {
    const date = new Date(inputDate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0'); // 일은 그대로 가져옴

    return `${year}.${month}.${day}`;
}


