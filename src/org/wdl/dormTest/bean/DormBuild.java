package org.wdl.dormTest.bean;

public class DormBuild {
	private Integer id;
	private String name;
	private String remark;
	private Integer disabled;
	
	public DormBuild() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DormBuild(String name, String remark, Integer userId) {
		super();
		this.name = name;
		this.remark = remark;
	}
	
	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DormBuild [id=" + id + ", name=" + name + ", remark=" + remark + ", disabled=" + disabled + "]";
	}
	
}
