function getSearchList(){
   const searchWord = document.getElementById('search-word').value;
   console.log(searchWord);

   const searchOption = document.getElementById('search-option').value;
   console.log(searchOption);
   
   fetch(`/api/club/posts?option=${searchOption}&searchWord=${searchWord}`, {
        method: "GET"
   })
   .then(response => response.json())
   .then(response => {
        console.log(response);

        const box = document.getElementById('board-list-box');
        box.innerHTML='';
        for(const post of response.data.searchPosts) {

            const boardListTemplate = document.getElementById('board-list-template');
            const boardListWrapper = boardListTemplate.querySelector('.board-list-wrapper');
            const newBoardListWrapper = boardListWrapper.cloneNode(true);
            const categoryName = newBoardListWrapper.querySelector('.category-name');
            categoryName.innerText = `[${post.category_name}]`;

            const title = newBoardListWrapper.querySelector('.title');
            title.innerText = post.title;

            const nickname = newBoardListWrapper.querySelector('.nickname');
            nickname.innerText = post.nickname;

            const createdAt = newBoardListWrapper.querySelector('.created-at');
            createdAt.innerText = formatDateDetail(post.created_at);

            const readCount = newBoardListWrapper.querySelector('.read-count');
            readCount.innerText = `조회 ${post.read_count}`;

            const totalCommentCount = newBoardListWrapper.querySelector('.total-comment-count');
            totalCommentCount.innerText = post.totalComment;

            const postUrl = newBoardListWrapper.querySelector('.post-url');
            postUrl.href = `/club/readPost?id=${post.id}`

            box.appendChild(newBoardListWrapper);


        }
   })


}