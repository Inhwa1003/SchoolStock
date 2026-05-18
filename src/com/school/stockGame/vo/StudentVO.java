package com.school.stockGame.vo;

public class StudentVO {
	private String studentId;
	private String password;
	private String name;
	private int grade;
	private String className;
	private int classNumber;
	private int registerYear;
	private int totalCoupon;
	private int totalPoint;
	

	public StudentVO() {}
	
	public StudentVO(String studentId) {
		setStudentId(studentId);
	}
	
	public StudentVO(String studentId, String password){
		setStudentId(studentId);
		setPassword(password);
	}
	
	public StudentVO(String studentId, String password, String name, int grade, String className, int classNumber){
		setStudentId(studentId);
		setPassword(password);
		setName(className);
		setGrade(grade);
		setClassName(className);
		setClassNumber(classNumber);
	}
	public StudentVO(String studentId, String password, String name, int grade, String className, int classNumber, int registerYear, int totalCoupon, int totalPoint) {
		setStudentId(studentId);
		setPassword(password);
		setName(name);
		setGrade(grade);
		setClassName(className);
		setClassNumber(classNumber);
		setRegisterYear(registerYear);
		setTotalCoupon(totalCoupon);
		setTotalPoint(totalPoint);
	}
	

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}
	public int getRegisterYear() {
		return registerYear;
	}
	public void setRegisterYear(int registerYear) {
		this.registerYear = registerYear;
	}
	public int getTotalCoupon() {
		return totalCoupon;
	}
	public void setTotalCoupon(int totalCoupon) {
		this.totalCoupon = totalCoupon;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
}