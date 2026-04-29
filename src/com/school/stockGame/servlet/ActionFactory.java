package com.school.stockGame.servlet;

public class ActionFactory {
	private ActionFactory(){}
	
	public static Action getAction(String cmd){
		Action a = null;
		System.out.println(cmd);
		
		if(cmd == null)
			cmd = "";
		
		switch (cmd) {
		case "stockDetailUI":
			a = new StockDetailUIAction();
			break;
		case "loginUI":
			a = new LoginUIAction();
		case "loginAction":
			a = new LoginAction();

		default:
			break;
		}
		return a;
		
	}

}
