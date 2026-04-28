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
			a = new MyAssertUIAction();
			break;
			
		case "MyAssertAction":
			a = new MyAssertUIAction();
			break;
		}
		return a;
	}
	

}
