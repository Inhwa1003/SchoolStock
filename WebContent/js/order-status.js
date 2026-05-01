let xhr = new XMLHttpRequest();

// 1. 서버에 데이터를 요청하는 함수
let getOrdersEvent = function() {
    // 현재 select 태그에서 선택된 값 (sell 또는 buy)을 가져옵니다.
    let selectedType = document.querySelector("#orderTypeSelect").value;
    
    // controller에 cmd와 함께 선택된 type을 파라미터로 보냅니다.
    xhr.open("get", "controller?cmd=StockOrderStatusAction&type=" + selectedType + "&no="+ stockNo, true);
    
   
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let list = JSON.parse(xhr.responseText);
            let tbody = document.querySelector("#orderListBody");
            tbody.innerHTML = ""; 

            list.forEach(function(order) {
                let tr = document.createElement("tr");
                // 서버 vo 필드명과 일치해야 함  기반
                tr.innerHTML = "<td>" + order.content + "</td>" +
                               "<td>" + order.price + "</td>" +
                               "<td>" + order.amount + "</td>";
                tbody.appendChild(tr);
            });
        }
    };
    xhr.send(null);
};

// 2. 페이지 로드 시 이벤트 연결
window.onload = function() {
    let selectEl = document.querySelector("#orderTypeSelect");
    
    if(selectEl) {
        // select 값이 바뀔 때마다 getOrdersEvent 함수 실행
        selectEl.onchange = getOrdersEvent;
        
        // 처음에 '매도' 리스트를 바로 보여주고 싶다면 초기 실행 호출
        getOrdersEvent(); 
    }
};

