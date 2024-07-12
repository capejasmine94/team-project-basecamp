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