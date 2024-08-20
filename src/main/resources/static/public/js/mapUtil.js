// 사용하는 html에 스크립트 추가 :          일단 제 Api key를 사용했습니다.나중에 ↓ 이 부분 안에 들어가는 키를 바꾸셔야 됩니다
// <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d17362e8bc72e774ada9174ac4347761&libraries=services"></script>

// 지도가 들어가는 곳에 <div id="map"></div> 추가
// 스크립트로 showMap(주소)를 쓰면 지도에 주소위치가 땋 뜹니다

// 지도를 생성합니다    
window.setTimeout(function(){
    const mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  
    map = new kakao.maps.Map(mapContainer, mapOption); 
},100);

let map

function showMap(address){
// 주소-좌표 변환 객체를 생성합니다
const geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
geocoder.addressSearch(address, function(result, status) {

    // 정상적으로 검색이 완료됐으면 
    if (status === kakao.maps.services.Status.OK) {

        const coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        const marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
});    
map.relayout();
}
