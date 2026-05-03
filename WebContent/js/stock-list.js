function updateStockPrices() {
  fetch(contextPath + "/controller?cmd=StockPriceAjax")
    .then(function(response) {
      return response.json();
    })
    .then(function(stockList) {
      stockList.forEach(function(stock) {
        const row = document.querySelector(
          'tr[data-stock-name="' + stock.stockName + '"]'
        )

        if (row == null) {
          return;
        }
        row.style.cursor = "pointer";
        row.onclick = function(){
        	location.href = contextPath + "/controller?cmd=StockDetailUI&no=" + stock.stockNo;
        };
        const currentPriceTd = row.querySelector(".current-price");
        const prevPriceTd = row.querySelector(".prev-price");
        const priceChangeTd = row.querySelector(".price-change");
        const changeRateTd = row.querySelector(".change-rate");

        currentPriceTd.textContent = stock.currentPrice + "P";
        prevPriceTd.textContent = stock.prevPrice + "P";

        if (stock.priceChange >= 0) {
          priceChangeTd.textContent = "+" + stock.priceChange + "P";
          priceChangeTd.className = "price-change up";
        } else {
          priceChangeTd.textContent = stock.priceChange + "P";
          priceChangeTd.className = "price-change down";
        }

        if (stock.changeRate >= 0) {
          changeRateTd.textContent = "+" + stock.changeRate + "%";
          changeRateTd.className = "change-rate up";
        } else {
          changeRateTd.textContent = stock.changeRate + "%";
          changeRateTd.className = "change-rate down";
        }
      });
    })
}

window.onload = function() {
  updateStockPrices();

   setInterval(function() {
    updateStockPrices();
  }, 10000);
};
