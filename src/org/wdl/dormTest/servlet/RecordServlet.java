package org.wdl.dormTest.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.Record;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.DormBuildService;
import org.wdl.dormTest.service.DormBuildServiceImpl;
import org.wdl.dormTest.service.RecordService;
import org.wdl.dormTest.service.RecordServiceImpl;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;
import org.wdl.dormTest.util.PageModel;

/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/record.action")
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("====record.action========");
		
		//在tomcat8.0中，如果是post请求，传递过来的中文可能会出现乱码问题 
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		System.out.println("action:"+action+" recordId:"+id);
		
		//获取当前登录的用户
		User userCurr = (User) request.getSession().getAttribute("session_user");
				
		UserService userService = new UserServiceImpl();
		RecordService recordService = new RecordServiceImpl();
		DormBuildService buildService = new DormBuildServiceImpl();
		
		if(action != null & action.equals("list")) {
			//查询缺勤记录并展示
			Integer roleId = userCurr.getRoleId();
			List<DormBuild>  builds = new ArrayList<DormBuild>();
			if(roleId.equals(0)) {
				//如当前用户是超级管理员，他能将学生添加到所有的宿舍楼,查询所有宿舍楼
				builds = buildService.findAll();
			}else if(roleId.equals(1)) {
				//如当前用户 为宿舍管理员，他只能添加学生到其管理的宿舍楼
				builds =  buildService.findByUserId(userCurr.getId());
			}
			System.out.println("builds:"+builds);
			request.setAttribute("builds", builds);
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String dormBuildId = request.getParameter("dormBuildId");
			String searchType = request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			String pageIndex = request.getParameter("pageIndex");
			System.out.println("startDate:"+startDate+" endDate:"+endDate+" dormBuildId:"+dormBuildId+
					" searchType:"+searchType+" keyword:"+keyword+"  pageIndex:"+pageIndex);
			
			//默认查询第一页，需两个参数，当前页码pageIndex，每页展示的条数  
			PageModel pageModel = new PageModel();
			if(pageIndex != null && !pageIndex.equals("")) {
				pageModel.setPageIndex(Integer.parseInt(pageIndex));
			}
			
			//分页查询考勤记录
			List<Record> records = recordService.findRecords(startDate,endDate,dormBuildId,
					searchType,keyword,userCurr,pageModel);
			System.out.println("records:"+records);
			
			//获取查询结果总数
			Integer totalNum = recordService.getToTalNum(startDate,endDate,dormBuildId,
					searchType,keyword,userCurr);
			System.out.println("totalNum:"+totalNum);
			
			request.setAttribute("totalNum", totalNum);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("dormBuildId", dormBuildId);
			request.setAttribute("searchType", searchType);
			request.setAttribute("keyword", keyword);
			request.setAttribute("pageIndex", pageModel.getPageIndex());
			request.setAttribute("records", records);
			request.setAttribute("mainRight", "/WEB-INF/jsp/recordList.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null && action.equals("preAdd")) {
			//跳转到添加页面
			request.setAttribute("mainRight", "/WEB-INF/jsp/recordAddOrUpdate.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null && action.equals("save")) {
			//保存
			String stuCode = request.getParameter("stuCode");
			String date = request.getParameter("date");
			String remark = request.getParameter("remark");
			System.out.println("remark:"+remark+"  date:"+date+"  stuCode:"+stuCode);
			
			//做保存前，判断用户是否有添加该学号学生缺勤记录的权限
			User user = userService.findStuCodeAndManager(stuCode,userCurr);
			System.out.println("查询添加缺勤权限user:"+user);
			/*//通过当前用户id查询其管理的宿舍楼
			List<DormBuild> dormBuilds= dormBuildService.findByUserId(userCurr.getId());
			Boolean flag = false;
			for (DormBuild dormBuild : dormBuilds) {
				if (dormBuild.getId().equals(userCurr.getDormBuildId())) {
					flag = true;
				}
			}*/
			
			if(id != null && !id.equals("")) {
				//更新
				if(user != null) {
					//有修改权限
					Record record = recordService.findById(Integer.parseInt(id));
					try {
						record.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
						record.setRemark(remark);
						record.setStudentId(user.getId());
						
						recordService.update(record);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
				}else {
					//无修改权限
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
				}
				
			}else {
				//保存
				if(user != null) {
					//说明当前登录的用户有添加该学号学生缺勤记录的权限
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date2 = null;
					try {
						date2 = dateFormat.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Record record = new Record();
					record.setStudentId(user.getId());
					record.setDate(date2);
					record.setDisabled(0);
					record.setRemark(remark);
					//保存数据到数据库
					recordService.save(record);
					
					response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
				}else {
					//没有添加的权限,跳转到添加页面
					request.setAttribute("error", "您没有添加该学号学生缺勤记录的权限！");
					request.setAttribute("mainRight", "/WEB-INF/jsp/recordAddOrUpdate.jsp");
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
				}
			}
			
			
			
		}else if(action != null && action.equals("preUpdate")) {
			//跳转到修改页面
			Record record = recordService.findById(Integer.parseInt(id));
			System.out.println("record:"+record);
			
			//查看用户是否有修改考勤记录的权限
			User user = userService.findStuCodeAndManager(record.getUser().getStuCode(),userCurr);
			System.out.println("修改权限user ："+user);
			if(user == null) {
				//去修改权限
				response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
			}else {
				request.setAttribute("record", record);
				request.setAttribute("mainRight", "/WEB-INF/jsp/recordAddOrUpdate.jsp");
				request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			}
			
		}else if(action != null && action.equals("deleteOrAcive")) {
			System.out.println("======删除或者激活=========");
			//删除或者激活
			String disabled = request.getParameter("disabled");
			
			Record record = recordService.findById(Integer.parseInt(id));
			record.setDisabled(Integer.parseInt(disabled));
			
			//查看用户是否有修改考勤记录的权限
			User user = userService.findStuCodeAndManager(record.getUser().getStuCode(),userCurr);
			System.out.println("修改权限user ："+user);
			if(user == null) {
				//无删除激活权限
				response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
			}else {
				//有删除激活权限
				recordService.update(record);
				//跳转到缺勤记录列表页
				response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
			}
		
		}
	}
		

}
