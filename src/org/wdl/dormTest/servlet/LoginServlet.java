package org.wdl.dormTest.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;
import org.wdl.dormTest.util.CookieUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("========登录=============");
		//Tomcat8.0 处理post请求乱码问题
		request.setCharacterEncoding("utf-8");
		//根据输入框标签的name属性值去获登录名和密码
		String stuCode = request.getParameter("stuCode");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		String roleId = request.getParameter("roleId");
		System.out.println("stuCode:"+stuCode+"  password:"+password+"   remember:"+remember+"  roleId："+roleId);
		
		UserService userService = new UserServiceImpl();
		User  user = null;
		if(roleId != null && roleId.equals("0")) {
			//管理员
			  user = userService.findAdmin(stuCode,password);
			
		}else if(roleId != null && roleId.equals("1")) {
			//宿舍管理员
			user = userService.findDormManage(stuCode,password);
		}if(roleId != null && roleId.equals("2")) {
			//学生
			//去查询用户输入的登录名和密码是否正确
			  user = userService.findByStuCodeAndPass(stuCode,password,"2");
		}
		System.out.println("user:"+user);
		
		if(user == null) {
			//用户输入的学号或密码错误，跳转到登录页面，并给予提示信息
			request.setAttribute("error", "您输入的用户名或密码错误！");
			//请求链未断开的跳转，可以在下一个servlet或jsp中，获取保存在request中的数据
			request.getRequestDispatcher("index.jsp").forward(request, response);			
		}else {
			//用户输入学号和密码正确，登录成功，跳转到主页面
			//保存在session中的数据，默认是30分钟内有效。保存在session中的数据，在整个项目中都可以获取得到
			request.getSession().setAttribute("session_user", user);
			
			if(remember != null && remember.equals("remember-me")) {
				//记住密码一周  时间单位是秒
				CookieUtil.addCookie("cookie_name_pass",7*24*60*60,request,response,URLEncoder.encode(user.getStuCode(), "utf-8"),URLEncoder.encode(password, "utf-8"),roleId);
			}
			System.out.println("========跳转到主页面=====");
			//WEB-INF下面的内容是受保护的，不能在通过地址栏直接访问，也不能通过response.sendRedirect重定向的形势访问
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			System.out.println("getServletContext().getContextPath():"+getServletContext().getContextPath());
			//response.sendRedirect(getServletContext().getContextPath()+"/WEB-INF/jsp/main.jsp");
		}
	}

}
