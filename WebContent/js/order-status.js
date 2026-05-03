let xhr = new XMLHttpRequest();
let xhr2 = new XMLHttpRequest();

// 1. 서버에 데이터를 요청하는 함수
let getOrdersEvent = function() {
	// 현재 select 태그에서 선택된 값 (sell 또는 buy)을 가져옵니다.
	let selectedType = document.querySelector("#orderTypeSelect").value;


	// controller에 cmd와 함께 선택된 type을 파라미터로 보냅니다.
	xhr.open("get", "controller?cmd=StockOrderStatus&type=" + selectedType
			+ "&no=" + stockNo, true);

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			let list = JSON.parse(xhr.responseText);
			let tbody = document.querySelector("#orderListBody");
			tbody.innerHTML = "";

			list.forEach(function(order) {
				let tr = document.createElement("tr");
				// 서버 vo 필드명과 일치해야 함  기반
				tr.innerHTML = "<td>" + order.content + "</td>"
							+ "<td>" + order.price + "</td>"
							+ "<td>" + order.amount + "</td>";
				tbody.appendChild(tr);
			});
		}
	};
	xhr.send(null);
};

//1. 서버에 데이터를 요청하는 함수
let getMyOrdersEvent = function() {

	// controller에 cmd와 함께 선택된 type을 파라미터로 보냅니다.
	xhr2.open("get", "controller?cmd=MyStockOrder&no=" + stockNo, true);

	xhr2.onreadystatechange = function() {
		if (xhr2.readyState == 4 && xhr2.status == 200) {
			let list = JSON.parse(xhr2.responseText);
			let tbody = document.querySelector("#myOrderListBody");
			tbody.innerHTML = "";

			list.forEach(function(order) {
				let tr = document.createElement("tr");
				// 서버 vo 필드명과 일치해야 함  기반
				tr.innerHTML = "<td>" + order.content + "</td>"
							+ "<td>" + order.price + "</td>"
							+ "<td>" + order.amount + "</td>"
							+ "<td>" + order.orderDate + "</td>"
							+ "<td><input type='button' class='cancelBtn' " +
									"data-order-no='"+ order.orderNo +"'value='취소'/></td>";
				tbody.appendChild(tr);
				
				document.querySelectorAll(".cancelBtn").forEach(function(btn) {
				    btn.onclick = function() {
				    	let orderNo = btn.dataset.orderNo;
				    	
				    	if(confirm("취소되었습니다.")){123
				    		 location.href = "controller?cmd=MyStockOrderCancel"+ "&orderNo=" + orderNo + "&stockNo=" + stockNo;
				    	}
				        btn.closest("tr").remove();
				    };
				});
			});
		}
	};
	xhr2.send(null);
};

//===== 가격 자동 업데이트 =====
let priceUpdateTimer = null;
let priceXhr = new XMLHttpRequest();

// 등락률 계산 함수
function calculateChange() {
    const stockPriceEl = document.querySelector('.stock-price');
    if (!stockPriceEl) return;
    
    const prevPrice = parseInt(stockPriceEl.dataset.prevPrice);
    const nowPriceText = document.querySelector('.price-now').textContent;
    const nowPrice = parseInt(nowPriceText.replace('P', ''));
    
    const change = nowPrice - prevPrice;
    const percent = ((change / prevPrice) * 100).toFixed(2);
    
    const priceChangeEl = document.querySelector('.price-change');
    const sign = change >= 0 ? '+' : '';
    priceChangeEl.textContent = sign + change + 'P(' + sign + percent + '%)';
    
    priceChangeEl.classList.remove('up', 'down');
    if (change > 0) {
        priceChangeEl.classList.add('up');
    } else if (change < 0) {
        priceChangeEl.classList.add('down');
    }
}

// 현재가 업데이트 함수 (XMLHttpRequest 패턴)
function updateNowPrice() {
    const stockPriceEl = document.querySelector('.stock-price');
    if (!stockPriceEl) return;
    
    const sNo = stockPriceEl.dataset.stockNo;
    
    priceXhr.open('GET', 'controller?cmd=GetNowPrice&stockNo=' + sNo, true);
    priceXhr.onreadystatechange = function() {
        if (priceXhr.readyState === 4 && priceXhr.status === 200) {
            try {
                const data = JSON.parse(priceXhr.responseText);
                document.querySelector('.price-now').textContent = data.nowPrice + 'P';
                calculateChange();
            } catch (e) {
                console.error('가격 데이터 파싱 실패:', e);
            }
        }
    };
    priceXhr.send(null);
}

// 타이머 정리 함수
function stopPriceUpdate() {
    if (priceUpdateTimer !== null) {
        clearInterval(priceUpdateTimer);
        priceUpdateTimer = null;
    }
}


// 2. 페이지 로드 시 이벤트 연결
window.onload = function() {
	
	document.querySelector("#backBtn").onclick = function(){history.back();};
	
	let buyBtn = document.querySelector("#buyBtn");
	let sellBtn = document.querySelector("#sellBtn");
	let selectEl = document.querySelector("#orderTypeSelect");
	let refreshBtn = document.querySelector("#refreshBtn");
	
	if(buyBtn){
		buyBtn.onclick = function(){
			 const buyPrice = parseInt(document.getElementById("buyPrice").value);
			 const buyAmount = parseInt(document.getElementById("buyAmount").value);
			 
			// 확인
			    const totalPrice = buyPrice * buyAmount;
			    if (!confirm(buyAmount + " 주를 " + buyPrice + " P에 매수합니다.\n총 " + totalPrice + " P가 차감됩니다. 진행하시겠습니까?")) {
			        return;
			    }
			    
			    // 컨트롤러로 이동
			    location.href = "controller?cmd=StockBuy&buyPrice=" + buyPrice+ "&buyAmount=" + buyAmount + "&stockNo=" + stockNo;
		}
	}
	
	if(sellBtn){
		sellBtn.onclick = function(){
			 const sellPrice = parseInt(document.getElementById("sellPrice").value);
			 const sellAmount = parseInt(document.getElementById("sellAmount").value);
			 
			// 확인
			    const totalPrice = sellPrice * sellAmount;
			    if (!confirm(sellAmount + " 주를 " + sellPrice + " P에 매도합니다.\n총 " + totalPrice + " P 입니다. 진행하시겠습니까?")) {
			        return;
			    }
			    
			    // 컨트롤러로 이동
			    location.href = "controller?cmd=StockSell&sellPrice=" + sellPrice+ "&sellAmount=" + sellAmount + "&stockNo=" + stockNo;
		}
	}
	
	if(refreshBtn){
		refreshBtn.onclick = getMyOrdersEvent;
		
		getMyOrdersEvent();
	}

	if (selectEl) {
		// select 값이 바뀔 때마다 getOrdersEvent 함수 실행
		selectEl.onchange = getOrdersEvent;

		// 처음에 '매도' 리스트를 바로 보여주고 싶다면 초기 실행 호출
		getOrdersEvent();
	}
	
	if (document.querySelector('.stock-price')) {
        calculateChange();  // 처음 한 번 계산
        
        stopPriceUpdate();  // 혹시 모를 기존 타이머 정리
        priceUpdateTimer = setInterval(updateNowPrice, 5000);
    }
};

window.addEventListener('beforeunload', stopPriceUpdate);
window.addEventListener('pagehide', stopPriceUpdate);