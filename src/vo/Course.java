package vo;

import java.util.Date;

public class Course {

	private int no;
	private String name;
	private int quota;
	private int regCnt;
	private String status;
	private Date createDate;
	private String teacherIdOrName;
	
	public Course() {
	}
	public Course(int no, String name, int quota, String teacherIdOrName) {
		super();
		this.no = no;
		this.name = name;
		this.quota = quota;
		this.teacherIdOrName = teacherIdOrName;
	}
	public Course(int no, String name, int quota, int regCnt, String status) {
		super();
		this.no = no;
		this.name = name;
		this.quota = quota;
		this.regCnt = regCnt;
		this.status = status;
	}
	public Course(String name, int quota, int regCnt, String status, Date createDate, String teacherIdOrName) {
		super();
		this.name = name;
		this.quota = quota;
		this.regCnt = regCnt;
		this.status = status;
		this.createDate = createDate;
		this.teacherIdOrName = teacherIdOrName;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuota() {
		return quota;
	}
	public void setQuota(int quota) {
		this.quota = quota;
	}
	public int getRegCnt() {
		return regCnt;
	}
	public void setRegCnt(int regCnt) {
		this.regCnt = regCnt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTeacherIdOrName() {
		return teacherIdOrName;
	}
	public void setTeacherIdOrName(String teacherIdOrName) {
		this.teacherIdOrName = teacherIdOrName;
	}
}
