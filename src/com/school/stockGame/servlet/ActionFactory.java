package com.school.stockGame.servlet;

public class ActionFactory {
	private ActionFactory() {
	}

	public static Action getAction(String cmd) {
		Action a = null;
		System.out.println(cmd);

		if (cmd == null)
			cmd = "";
		switch (cmd) {
		case "MyAssetsUI":
			a = new MyAssetsUIAction();
			break;
		case "MyAssetsAction":
			a = new MyAssetsAction();
			break;
		case "NewsUI":
			a = new NewsUIAction();
			break;
		case "GetStockPrice":
			a = new GetStockPriceAction();
			break;
		case "idCheck":
			a = new IdCheckAction();
			break;
		case "LoginUI":
			a = new LoginUIAction();
			break;
		case "LoginAction":
			a = new LoginAction();
			break;
		case "AddMemberUI":
			a = new AddMemberUIAction();
			break;
		case "AddMemberAction":
			a = new AddMemberAction();
			break;
		case "LogoutAction":
			a = new LogoutAction();
			break;
		case "CouponPersonalUI":
			a = new CouponPersonalUIAction();
			break;
		case "CouponMarketUI":
			a = new CouponMarketUIAction();
			break;
		case "CouponBuyAction":
			a = new CouponBuyAction();
			break;
		case "MyPointHistoryUI":
			a = new MyPointHistoryUI();
			break;
		case "StockListUI":
			a = new StockListUI();
			break;
		case "StockPriceAjax":
			a = new StockPriceAjax();
			break;
		case "StockDetailUI":
			a = new StockDetailUIAction();
			break;
		case "StockOrderStatus":
			a = new StockOrderStatusAcion();
			break;
		case "MyStockOrder":
			a = new MyStockOrderAction();
			break;
		case "StockBuy":
			a = new StockBuyAction();
			break;
		default:
			a = new LoginUIAction();
		}
		return a;
	}

}
