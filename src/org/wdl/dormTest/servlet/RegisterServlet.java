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
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.DormBuildService;
import org.wdl.dormTest.service.DormBuildServiceImpl;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DormBuildService buildService = new DormBuildServiceImpl();
		UserService userService = new UserServiceImpl();
		
		request.setCharacterEncoding("utf-8");
		System.out.println("=========注册==============");
		String action = request.getParameter("action");
		
		List<DormBuild>  builds = buildService.findAll();
		request.setAttribute("builds", builds);
		 if(action != null && action.equals("save")) {
			//保存学生
				String stuCode = request.getParameter("stuCode");
				String name = request.getParameter("name");
				String sex = request.getParameter("sex");
				String tel = request.getParameter("tel");
				String passWord = request.getParameter("passWord");
				String  dormBuildId= request.getParameter("dormBuildId");
				String dormCode = request.getParameter("dormCode");
				System.out.println("stuCode:"+stuCode+"  name:"+name+"   sex:"+sex+
						"  tel:"+tel+" passWord:"+passWord+" dormBuildId:"+dormBuildId+
						"  dormCode:"+dormCode);
				
				User student = userService.findByStuCode(stuCode);
				//更新之前，查询数据库是否已经存在当前学号的学生，如已存在，则跳转到添加页面
				if(student != null  ) {
					System.out.println("根据学生学号查询student:"+student);
					//当前学号的学生已存在
					request.setAttribute("error", "当前学号的学生已存在，请重新输入！");
					
					request.getRequestDispatcher("/register.jsp").forward(request, response);
				}else {
					User user2 = new User();
					user2.setStuCode(stuCode);
					user2.setSex(sex);
					user2.setTel(tel);
					user2.setName(name);
					user2.setPassWord(passWord);
					user2.setDormBuildId(Integer.parseInt(dormBuildId));
					user2.setDormCode(dormCode);
					user2.setRoleId(2);
					user2.setCreateUserId(0);
					userService.saveStudent(user2);
					request.setAttribute("error", "注册成功，请登录！");
					
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
		 }else {
			 //跳转到注册页面
			request.getRequestDispatcher("/register.jsp").forward(request, response); 
		 }
		
	}

}
