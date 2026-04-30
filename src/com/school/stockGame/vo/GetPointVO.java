package com.school.stockGame.vo;

public class GetPointVO {

	private int pointNo;
	private String content;
	private int point;
	private String date;
	private String studentId;
	
	public GetPointVO(){
		setPointNo(0);
		setContent("없음");
		setPoint(0);
		setDate(date);
		setStudentId(studentId);
	}

	public GetPointVO(int pointNo, String content, int point, String date, String studentId) {
		setPointNo(pointNo);
		setContent(content);
		setPoint(point);
		setDate(date);
		setStudentId(studentId);
	}

	public int getPointNo() {
		return pointNo;
	}

	public void setPointNo(int pointNo) {
		this.pointNo = pointNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "[pointNo=" + pointNo + ", content=" + content + ", point=" + point + ", date=" + date
				+ ", studentId=" + studentId + "]";
	}

}
