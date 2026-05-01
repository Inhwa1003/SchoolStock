function updateStock(stockNo) {
    if (!stockNo) return;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            
            // 1. 현재가 갱신
            var price = document.getElementById("price-" + response.stockNo);
            if (price) price.innerHTML = response.stockPrice.toLocaleString() + "P";
            
            // 2. 수익금 갱신
            var profit = document.getElementById("profit-" + response.stockNo);
            if (profit) {
                var pVal = response.stockProfit;
                profit.innerHTML = (pVal > 0 ? "+" : "") + pVal.toLocaleString() + "P";
                profit.className = pVal >= 0 ? "plus" : "minus";
            }
            
            // 3. 상단 카드 갱신
            document.getElementById("total-assets").innerHTML = response.totalAssets.toLocaleString() + "P";
            var totalProfitElem = document.getElementById("total-profit");
            totalProfitElem.innerHTML = (data.totalProfit > 0 ? "+" : "") + data.totalProfit.toLocaleString() + "P";
            totalProfitElem.className = data.totalProfit >= 0 ? "value plus" : "value minus";
        }
    };
    xhr.open("GET", "controller?cmd=GetStockPrice&stockNo=" + stockNo, true);
    xhr.send(null);
}

function updateAllStocks() {
    var rows = document.getElementsByClassName("stock-row-item");
    
    if (rows.length === 0) {
        updateStock(1);
        return;
    }


    for (var i = 0; i < rows.length; i++) {
        var sNo = rows[i].id.split("-")[1]; 
        updateStock(sNo);
    }
}