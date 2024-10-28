package org.wdl.dormTest.bean;

import java.util.Date;

public class Record {
	
	private int id;
	private Integer studentId;
	private Date date;
	private String remark;
	private Integer disabled;
	
	private String beginDate;
	private String endDate;
	private User user;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", studentId=" + studentId + ", date=" + date + ", remark=" + remark
				+ ", beginDate=" + beginDate + ", endDate=" + endDate + ", user=" + user + ", disabled=" + disabled
				+ "]";
	}

	

	
}
