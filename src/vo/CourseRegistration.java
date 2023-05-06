package vo;

import java.util.Date;

public class CourseRegistration {

	private int regNo;
	private String studentIdOrName;
	private int courseNo;
	private String canceled;
	private Date createDate;
	private String courseName;
	
	public CourseRegistration() {
	}
	public CourseRegistration(int regNo, int courseNo, String canceled, Date createDate, String courseName) {
		super();
		this.regNo = regNo;
		this.courseNo = courseNo;
		this.canceled = canceled;
		this.createDate = createDate;
		this.courseName = courseName;
	}
	public CourseRegistration(int regNo, String studentIdOrName, String canceled, Date createDate) {
		super();
		this.regNo = regNo;
		this.studentIdOrName = studentIdOrName;
		this.canceled = canceled;
		this.createDate = createDate;
	}
	
	public int getRegNo() {
		return regNo;
	}
	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}
	public String getStudentIdOrName() {
		return studentIdOrName;
	}
	public void setStudentIdOrName(String studentIdOrName) {
		this.studentIdOrName = studentIdOrName;
	}
	public int getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
	}
	public String getCanceled() {
		return canceled;
	}
	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
