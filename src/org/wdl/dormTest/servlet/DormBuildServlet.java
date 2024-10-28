package org.wdl.dormTest.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.service.DormBuildService;
import org.wdl.dormTest.service.DormBuildServiceImpl;

/**
 * Servlet implementation class DormBuildServlet
 */
@WebServlet("/dormBuild.action")
public class DormBuildServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DormBuildServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("=========dormBuild.action=========");
		
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		//通过request.getParameter("id")方式获取的值都是String类型
		String id = request.getParameter("id");
		System.out.println("action:"+action);
		
		DormBuildService dormBuildService = new DormBuildServiceImpl();
		
		if(action != null & action.equals("list")) {
			List<DormBuild>  builds = new ArrayList<DormBuild>();
			if(id == null || id.equals("")) {
				//点击左侧宿舍楼管理，查询所有宿舍楼信息，跳转到宿舍楼列表页
				builds = dormBuildService.find();
			}else if(id != null && !id.equals("")) {
				//点击搜索按钮搜索宿舍楼，根据宿舍楼id查询宿舍楼信息
				DormBuild  build = dormBuildService.findById(Integer.parseInt(id));
				builds.add(build);
			}
			
			//查询所有的宿舍楼，在select中遍历
			List<DormBuild>  buildSelects = dormBuildService.find();
			request.setAttribute("buildSelects", buildSelects);
			request.setAttribute("id", id);
			System.out.println("builds:"+builds);
			request.setAttribute("builds", builds);
			request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildList.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null & action.equals("preAdd")) {
			//表示跳转到宿舍楼添加页面
			request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null & action.equals("save")) {
			//保存数据
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			System.out.println("name:"+name+"  remark:"+remark);
			
			
			//宿舍楼名字不能重复，从数据库查询，当前用户输入的宿舍楼名字是否已经存在
			DormBuild  dormBuild = dormBuildService.findByName(name);
			System.out.println("dormBuild:"+dormBuild);
			if(id != null && !id.equals("")) {
				//更新
				if(dormBuild != null && !dormBuild.getId().equals(Integer.parseInt(id))) {
					//表示跳转到宿舍楼添加页面
					request.setAttribute("error", "当前宿舍楼名已存在，请重新输入！");
					
					//根据宿舍楼id，查询宿舍楼
					DormBuild build = dormBuildService.findById(Integer.parseInt(id));
					//保存宿舍楼信息，到前端页面展示
					request.setAttribute("build", build);
					request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildAddOrUpdate.jsp");
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
				}else {
					dormBuild = dormBuildService.findById(Integer.parseInt(id));
					dormBuild.setName(name);
					dormBuild.setRemark(remark);
					
					//执行更新
					dormBuildService.update(dormBuild);
					//更新完成，跑到宿舍楼管理列表页，查询所有宿舍楼
					List<DormBuild>  builds  = dormBuildService.find();
					request.setAttribute("builds", builds);
					request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildList.jsp");
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
					
				}
			}else {
				//保存
				if(dormBuild != null) {
					//当前用户输入的宿舍楼名已存在
					//表示跳转到宿舍楼添加页面
					request.setAttribute("error", "当前宿舍楼名已存在，请重新输入！");
					request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildAddOrUpdate.jsp");
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
				}else {
					//当前用户输入的宿舍楼名不存在，则保存用户输入的信息到数据库
					DormBuild build = new DormBuild();
					build.setName(name);
					build.setRemark(remark);
					build.setDisabled(0);
					dormBuildService.save(build);
					//保存完成，跑到宿舍楼管理列表页，查询所有宿舍楼
					List<DormBuild>  builds  = dormBuildService.find();
					request.setAttribute("builds", builds);
					request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildList.jsp");
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
				}
			}
			
		}else if(action != null & action.equals("preUpdate")) {
			//根据宿舍楼id，查询宿舍楼
			DormBuild build = dormBuildService.findById(Integer.parseInt(id));
			//保存宿舍楼信息，到前端页面展示
			request.setAttribute("build", build);
			//跳转到宿舍楼修改页面
			request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildAddOrUpdate.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}else if(action != null & action.equals("deleteOrAcive")) {
			//删除或激活
			String disabled = request.getParameter("disabled");
			
			DormBuild  dormBuild = dormBuildService.findById(Integer.parseInt(id));
			System.out.println("更新前dormBuild："+dormBuild);
			dormBuild.setDisabled(Integer.parseInt(disabled));
			System.out.println("更新后dormBuild："+dormBuild);
			
			//执行更新
			dormBuildService.update(dormBuild);
			//更新完成，跑到宿舍楼管理列表页，查询所有宿舍楼
			List<DormBuild>  builds  = dormBuildService.find();
			request.setAttribute("builds", builds);
			request.setAttribute("mainRight", "/WEB-INF/jsp/dormBuildList.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}
		
		
	}

}
