var renderAssets = function() {
    // SQL 더미데이터 기준 데이터 설정 (학생 'abc' 기준 예시)
    var summaryData = {
        totalAssets: 8200, 
        points: 3900,
        profit: 300,
        coupons: 3
    };

    var stockList = [
        { 
            name: "마라탕", 
            qty: 5, 
            current: 900, 
            avg: 860, 
            totalCost: 4300, 
            profit: 200 
        },
        { 
            name: "PC방", 
            qty: 0, 
            current: 2500, 
            avg: 2000, 
            totalCost: 0, 
            profit: 500 
        },
        { 
            name: "SM", 
            qty: 0, 
            current: 4000, 
            avg: 0, 
            totalCost: 0, 
            profit: 0 
        }
    ];

    // 상단 요약 영역 반영
    document.getElementById("total-assets").innerHTML = summaryData.totalAssets.toLocaleString() + "P";
    document.getElementById("total-points").innerHTML = summaryData.points.toLocaleString() + "P";
    document.getElementById("total-profit").innerHTML = summaryData.profit.toLocaleString() + "P";
    document.getElementById("coupon-count").innerHTML = summaryData.coupons + "개";

    // 테이블 렌더링 (순서: 주식명, 보유수량, 현재가격, 평균단가, 총 구매 비용, 수익금)
    var tbody = document.getElementById("stock-list");
    var html = "";

    for (var i = 0; i < stockList.length; i++) {
        var stock = stockList[i];
        
        if(stock.qty >= 0) { 
            var colorClass = stock.profit >= 0 ? "plus" : "minus";
            var prefix = stock.profit > 0 ? "+" : "";

            html += "<tr>";
            html += "    <td>" + stock.name + "</td>";
            html += "    <td>" + stock.qty + "개</td>";
            html += "    <td>" + stock.current.toLocaleString() + "P</td>";
            html += "    <td>" + stock.avg.toLocaleString() + "P</td>";
            html += "    <td>" + stock.totalCost.toLocaleString() + "P</td>";
            html += "    <td class='" + colorClass + "'>" + prefix + stock.profit.toLocaleString() + "P</td>";
            html += "</tr>";
        }
    }

    tbody.innerHTML = html;
};

window.onload = function() {
    renderAssets();
};