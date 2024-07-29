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
    const currentUrl = window.location.search;
    // console.log(a);

    // 위에서 가져온 ?id=9에서 9만 가져온다 (id -> 쿼리스트링 키값으로)
    const urlQueryString = new URLSearchParams(currentUrl);
    // console.log(urlSearchObj1);
    const param = urlQueryString.get('id');
    // console.log(param);

    fetch('/api/club/comment?post_id='+param, {
        method: 'GET'
        // 데이터를 가져오는 경우 GET
        // 데이터를 생성해야 할 경우 POST
        // 데이터를 업데이트해야 하는 경우 PUT, PATCH
        // 데이터를 삭제해야 하는 경우 DELETE
    })
        .then(response => response.json())
        .then(response => {
            // console.log(response.data.commentDataList);

            const commentWrapperBox = document.getElementById("comment-wrapper-box");
            commentWrapperBox.innerHTML = '';

            for(const commentData of response.data.commentDataList) {
                // console.log(commentData.userDto);

                const commentWrapper = document.querySelector("#comment-template .comment-wrapper");
                // console.log(commentWrapper);

                const newCommentWrapper = commentWrapper.cloneNode(true);
                const commentNickname = commentWrapper.querySelector(".commentNickname"); 
                commentNickname.innerText = commentData.userDto.nickname;               

                const commentCreatedAt = commentWrapper.querySelector(".commentCreatedAt");
                commentCreatedAt.innerText = formatDate(commentData.clubPostCommentDto.created_at);

                const commentContent = newCommentWrapper.querySelector(".commentContent");
                commentContent.innerText = commentData.clubPostCommentDto.content;
                

                const commentHeart = newCommentWrapper.querySelector(".commentHeart");

                const writeNestedComment = newCommentWrapper.querySelector(".writeNestedComment");



               // Find or create a container for nested comments
            let nestedCommentsContainer = newCommentWrapper.querySelector(".nestedCommentsContainer");
            if (!nestedCommentsContainer) {
                nestedCommentsContainer = document.createElement('div');
                nestedCommentsContainer.classList.add('nestedCommentsContainer');
                newCommentWrapper.appendChild(nestedCommentsContainer);
            }

            const nestedCommentWrapperTemplate = document.querySelector("#nested-comment-template .nested-comment-wrapper");
            for (const nestedCommentData of commentData.nestedCommentDetailList) {
                const newNestedCommentWrapper = nestedCommentWrapperTemplate.cloneNode(true);

                const nestedCommentNickname = newNestedCommentWrapper.querySelector(".nestedCommentNickname");
                nestedCommentNickname.innerText = nestedCommentData.userDtoForNestedComment.nickname;

                const nestedCommentCreatedAt = newNestedCommentWrapper.querySelector(".nestedCommentCreatedAt");
                nestedCommentCreatedAt.innerText = formatDate(nestedCommentData.nestedCommentDto.created_at);

                const nestedCommentContent = newNestedCommentWrapper.querySelector(".nestedCommentContent");
                nestedCommentContent.innerText = nestedCommentData.nestedCommentDto.content;

                nestedCommentsContainer.appendChild(newNestedCommentWrapper);
            }

                // writeNestedComment.onclick = function a() {}; 아래와 같은 코드임
                // writeNestedComment.onclick = function b () {};
                // writeNestedComment.setAttribute('onclick', function b() {});

                // writeNestedComment.onclick = (event) => {
                //     console.log(event.target);
                // };

                // writeNestedComment.addEventListener('click', function a() {});
                
                writeNestedComment.onclick = () => {
                    const comment = document.querySelector(".comment");
                    comment.focus();
                    comment.placeholder = `@${commentData.userDto.nickname}`;
                   
                   const registerComment = document.querySelector(".registerComment");
                //     // console.log(registerComment);
                    // console.log(registerComment);
                    registerComment.onclick = () => {
                        const commentId = commentData.clubPostCommentDto.id
                        const content = comment.value;
                //         console.log(commentId);
                //         console.log(content);
                        const data = {
                            comment_id: commentId,
                            content: content
                        }
                        fetch('/api/club/nestedComment', {
                            method: "POST",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(data)
                        })
                        .then(response => response.json())
                        .then(response => {
                            console.log('Response:', response);
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                    };
                };

                
                const commentId = commentData.clubPostCommentDto.id;
                if(commentData.isLiked) {
                    commentHeart.classList.add("fill-on");
                    commentHeart.onclick = () => {
                        removeHeart(commentId);
                        
                    }
                } else {
                    commentHeart.classList.remove("fill-on");
                    commentHeart.onclick = () => {
                        addHeart(commentId);
                    }
                }

                commentWrapperBox.appendChild(newCommentWrapper); 
            }
        });
}



function removeHeart(commentId) {
    fetch('/api/club/comment/like?comment_id=' + commentId, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(response => {
        getCommentList();
    })
}

function addHeart(commentId){
    fetch('/api/club/comment/like?comment_id=' + commentId, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(response => {
        getCommentList();
    })
}

function formatDate(dateString) {
    const date = new Date(dateString);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}`;
}





document.addEventListener('DOMContentLoaded', () => {
    getCommentList();

})