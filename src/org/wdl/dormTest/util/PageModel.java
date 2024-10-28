package org.wdl.dormTest.util;

/**
 * 分页实体：用来封装分页相关的信息   比如：当前页码   每页显示多少条数据   总记录数
 */
public class PageModel {

	 private int pageIndex = 1;//当前页码
	 private int pageSize = 3;//每页显示的记录数  
	 private int totalNum;//总记录数
	  
	  
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	  
	
	//计算查询的开始行号   0   8  16
	public int getStartNum(){
		           
		return (this.getPageIndex()-1)*this.getPageSize();
	}
	  
	  
}
