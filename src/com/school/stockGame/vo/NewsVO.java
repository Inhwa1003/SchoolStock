package com.school.stockGame.vo;

public class NewsVO {
	private int newsNO;
	private String content;
	
	
	public NewsVO() {
		setNewsNO(0);
		setContent("없음");
	}


	public NewsVO(int newsNO, String content) {
		setNewsNO(newsNO);
		setContent(content);
	}


	public int getNewsNO() {
		return newsNO;
	}


	public void setNewsNO(int newsNO) {
		this.newsNO = newsNO;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "[newsNO=" + newsNO + ", content=" + content + "]";
	}
	
	
}