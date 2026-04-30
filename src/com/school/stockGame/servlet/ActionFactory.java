package com.school.stockGame.servlet;

public class ActionFactory {
	private ActionFactory(){
	}
	
	public static Action getAction(String cmd){
		Action a = null;
		System.out.println(cmd);
		if(cmd == null)
			cmd = "";
		switch (cmd){
		case "MyAssertUI":
			a = new MyAssertUI();
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
		}
		return a;
	}
	

}
