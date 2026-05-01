<%@page import="com.google.gson.JsonObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    JsonObject jsonObject=new JsonObject();
    jsonObject.addProperty("result",(Boolean)request.getAttribute("result"));
    //System.out.println("id"+jsonObject);
%>
<%=jsonObject.toString() %>