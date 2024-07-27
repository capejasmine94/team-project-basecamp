function getCommentList() {
    // 여기서부터 시작

    // html파일 전체 태그에서 아이디로 하나의 태그를 가져온다(document.getElementById)
    // const commentTemplate = document.getElementById('comment-template');
    // console.log(commentTemplate);

    // 쿼리셀렉터는 #아이디 혹은 .클래스 이름으로 태그를 가져오는 선택자
    // const commentWrapper = commentTemplate.querySelector(".comment-wrapper");
    // console.log(commentWrapper);

    // commentWrapper라는 태그를 복사한다(괄호 안의 true -> 자식태그까지 복사-> 아직 붙여넣기는 안 한 상태 / true를 빼먹으면 자식태그 복사 안됨)
    // const newCommentWrapper = commentWrapper.cloneNode(true);
    // console.log(newCommentWrapper);

    // 내가 붙일 태그를 document 전체에서 ID로 가져온다
    // const commentWrapperBox = document.getElementById('comment-wrapper-box');
    // console.log(commentWrapperBox);

    // commentWrapperBox에 newCommentWrapper(복사해둔 것)을 하위태그(자식태그)로 붙여넣기 한다
    // commentWrapperBox.appendChild(newCommentWrapper);


    // url에서 ?id=9 까지 가져옴(웹브라우저의 현재 주소의 쿼리스트링을 가져온다)
    const a = window.location.search;
    // console.log(a);

    // 위에서 가져온 ?id=9에서 9만 가져온다 (id -> 쿼리스트링 키값으로)
    const urlSearchObj1 = new URLSearchParams(a);
    // console.log(urlSearchObj1);
    const param = urlSearchObj1.get('id');
    // console.log(param);

    fetch('/api/club/comment?post_id='+param, {
        method: 'GET'
        // 데이터를 가져오는 경우 GET
        // 데이터를 생성해야 할 경우 POST
        // 데이터를 업데이트해야 하는 경우 PUT, PETCH
        // 데이터를 삭제해야 하는 경우 DELETE
    })
        .then(response => response.json())
        .then(response => {
            console.log(response.data.commentDataList);

            for(const commentData of response.data.commentDataList) {
                // console.log(commentData.userDto);

                const commentWrapper = document.querySelector(".comment-wrapper");
                console.log(commentWrapper);

                const newCommnetWrapper = commentWrapper.cloneNode(true);
                const commentNickname = newCommnetWrapper.querySelector(".commentNickname"); 
                commentNickname.innerText = commentData.userDto.nickname;

                const commentCreatedAt = newCommnetWrapper.querySelector(".commentCreatedAt");
                commentCreatedAt.innerText = commentData.clubPostCommentDto.created_at;

                const commentContent = newCommnetWrapper.querySelector(".commentContent");
                commentContent.innerText = commentData.clubPostCommentDto.content;




                const commentWrapperBox = document.getElementById("comment-wrapper-box");
                commentWrapperBox.appendChild(newCommnetWrapper); 
            }
        });
}




document.addEventListener('DOMContentLoaded', () => {
    getCommentList();
    
})