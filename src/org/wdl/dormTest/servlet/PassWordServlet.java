package org.wdl.dormTest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;

/**
 * Servlet implementation class PassWordServlet
 */
@WebServlet("/password.action")
public class PassWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassWordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("---------------------password.action---------");
		
		String action = request.getParameter("action");
		//获取当前登录的用户
		User userCur = (User) request.getSession().getAttribute("session_user");
		System.out.println("action:"+action+" userCur:"+userCur);
		
		UserService userService = new UserServiceImpl();
		if(action != null && action.equals("preChange")) {
			//跳转到密码修改页面
			request.setAttribute("mainRight", "/WEB-INF/jsp/passwordChange.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		}else if(action != null && action.equals("ajaxOldPassWord")) {
			//校验用户输入的元密码是否正确
			
			//当前登录的用户在输入框输入的密码
			String oldPassWord = request.getParameter("oldPassWord");
			//当前登录的用户原始密码
			String passWord = userCur.getPassWord();
			
			if(!passWord.equals(oldPassWord)) {
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.print("您输入的原始密码错误，请重新输入！");
			}
			
		}else if(action != null && action.equals("change")) {
			//修改密码
			String newPassword = request.getParameter("newPassword");
			userCur.setPassWord(newPassword);
			userService.updatePassWord(userCur);
			
			System.out.println("修改密码userCur:"+userCur);
			
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
