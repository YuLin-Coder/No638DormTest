package org.wdl.dormTest.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PageTag extends TagSupport {
	/*<!--totalNum:查询出的总数据量     pageSize：每一页展示的行数    pageIndex:表示当前页面  
			submitUrl：表示点击上一页下一页首页 尾页是发送的请求-->*/
	private Integer totalNum;
	private Integer pageSize;
	private Integer pageIndex;
	private String submitUrl;
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}

	//重新doStartTag（遇到开始标签时执行）和doEndTag（遇到结束标签后执行）方法
	@Override
	public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		System.out.println(totalNum+"   "+pageSize+"  "+pageIndex+"  "+submitUrl);
		
		try {
			StringBuffer page = new StringBuffer();
			//总页面数
			Integer totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize+1;
			System.out.println("totalPage:"+totalPage);
			
			if(totalNum > pageSize) {
				//只有当查询处理的数据量>每一页展示的数据量时才进行分页
				if(pageIndex == 1) {
					//当前页码是首页
					page.append("<a href='#'>首页</a>&nbsp;");
					page.append("<a href='#'>上一页</a>&nbsp;");
					page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex+1)+"'>下一页</a>&nbsp;");
					page.append("<a href='"+submitUrl+"&pageIndex="+totalPage+"'>尾页</a>&nbsp;");
					
				}else if(pageIndex == totalPage) {
					//当前页码是尾页
					page.append("<a href='"+submitUrl+"&pageIndex="+1+"'>首页</a>&nbsp;");
					page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex-1)+"'>上一页</a>&nbsp;");
					page.append("<a href='#'>下一页</a>&nbsp;");
					page.append("<a href='#'>尾页</a>&nbsp;");
				}else {
					//当前页码在中间
					page.append("<a href='"+submitUrl+"&pageIndex="+1+"'>首页</a>&nbsp;");
					page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex-1)+"'>上一页</a>&nbsp;");
					page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex+1)+"'>下一页</a>&nbsp;");
					page.append("<a href='"+submitUrl+"&pageIndex="+totalPage+"'>尾页</a>&nbsp;");
					
				}
				page.append("<br> 当是第"+pageIndex+" 页&nbsp;/共"+totalPage+"页   &nbsp;/共"+totalNum+"条数据");
			}
			
			writer.print(page.toString());
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getSubmitUrl() {
		return submitUrl;
	}

	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}

	
	
}
