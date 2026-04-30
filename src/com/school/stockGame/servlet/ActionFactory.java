package com.school.stockGame.servlet;

public class ActionFactory {
	private ActionFactory(){}
	
	public static Action getAction(String cmd){
		Action a = null;
		if(cmd == null) cmd = "";
		System.out.println(cmd);
		switch (cmd){
		case "LoginUI":
			a = new LoginUIAction();
			break;
		case "LoginAction":
			a=new LoginAction();	
			break;
		case "AddMemberUI":
			a=new AddMemberUIAction();
			break;
		case "AddMemberAction":
			a=new AddMemberAction();
			break;
		case "LogoutAction":
			a=new LogoutAction();
		default:
			a=new LoginUIAction();

		}
		return a;
	}
	

}
